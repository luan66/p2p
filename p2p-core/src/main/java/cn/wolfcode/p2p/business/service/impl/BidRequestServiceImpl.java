package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BaseAuditorDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.*;
import cn.wolfcode.p2p.business.mapper.BidRequestMapper;
import cn.wolfcode.p2p.business.service.*;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.CalculatetUtil;
import cn.wolfcode.p2p.util.Constants;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款申请
 */
@Service
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IBidService bidService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private IBidRequestAuditHistoryService bidRequestAuditHistoryMapperService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;
    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;

    //申请借款
    public void borrowApply(BidRequest bidRequest) {
        //效率原因,把不需要查询数据库的放在前面
        //3:判断年利率
        BigDecimal currentRate = bidRequest.getCurrentRate();
        if((currentRate.compareTo(Constants.SMALLEST_CURRENT_RATE) < 0) || (currentRate.compareTo(Constants.MAX_CURRENT_RATE) > 0)){
            throw new DisplayableException("你填写的年利率["+currentRate+"]不在范围内["+Constants.SMALLEST_CURRENT_RATE+"~"+Constants.MAX_CURRENT_RATE+"]");
        }
        //4:判断招标天数:
        int disableDays = bidRequest.getDisableDays();
        if(disableDays <= 0 ){
            throw new DisplayableException("你填写的招标天数["+disableDays+"]有误");
        }
        //1:判断是否已有借款正在审核:
        LoginInfo loginInfo = bidRequest.getCreateUserId();
        UserInfo userInfo = userInfoService.getUserInfoById(loginInfo.getId());
        if(userInfo.hasBidRequestInProcess()){
            throw new DisplayableException("你有借款正在申请中!");
        }
        //2:判断当前用户的可借金额:
        Account account = accountService.selectByPrimaryKey(loginInfo.getId());
        if(account.getRemainBorrowLimit().compareTo(bidRequest.getBidRequestAmount()) < 0){
            throw new DisplayableException("你的可借款额度不足!");
        }
        //5:添加借款申请信息:
        BidRequest bidRequest1 = new BidRequest();
        bidRequest1.setBidRequestType(bidRequest.getBidRequestType());
        bidRequest1.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        bidRequest1.setBidRequestAmount(bidRequest.getBidRequestAmount());
        //计算总利息:
        bidRequest1.setTotalRewardAmount(CalculatetUtil.calTotalInterest(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), bidRequest.getMonthes2Return()));
        bidRequest1.setCurrentRate(bidRequest.getCurrentRate());
        bidRequest1.setMonthes2Return(bidRequest.getMonthes2Return());
        bidRequest1.setTitle(bidRequest.getTitle());
        bidRequest1.setDescription(bidRequest.getDescription());
        bidRequest1.setDisableDays(bidRequest.getDisableDays());
        bidRequest1.setCreateUserId(loginInfo);
        bidRequest1.setApplyTime(new Date());
        bidRequest1.setMinBidAmount(bidRequest.getMinBidAmount());
        //添加:
        bidRequestMapper.insert(bidRequest1);

        //6:修改申请人的位状态为:借款申请中;
        long bitState = BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfo.setBitState(bitState);
        userInfoService.update(userInfo);
    }

    //发标审核高级查询
    public PageResult query(QueryObject qo) {
        int totalCount = bidRequestMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<BidRequest> list = bidRequestMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //审核借款发标
    public void audit(Long id, int state, String remark,LoginInfo loginInfo) {
        //效率原因,把不需要查询数据库的放在前面
        //1:查询是否有借款正在申请中:
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        if(bidRequest.getBidRequestState() != Constants.BIDREQUEST_STATE_PUBLISH_PENDING){
            throw new DisplayableException("该用户没有借款申请!");
        }
        Date nowDate = new Date();     //当前时间
        //2:修改标的审核信息:审核人,审核时间,投标人,状态(成功还是失败),审核类型;
        BidRequestAuditHistory bidRequestAudit = new BidRequestAuditHistory();
        bidRequestAudit.setAuditType(Constants.BIDREQUEST_STATE_BIDDING);
        bidRequestAudit.setBidRequestId(id);
        bidRequestAudit.setApplierId(bidRequest.getCreateUserId());
        bidRequestAudit.setApplyTime(bidRequest.getApplyTime());
        bidRequestAudit.setAuditorId(loginInfo);
        bidRequestAudit.setAuditTime(nowDate);
        bidRequestAudit.setRemark(remark);
        bidRequestAudit.setState(state);
        //添加审核记录:
        bidRequestAuditHistoryMapperService.insert(bidRequestAudit);

        if(state == BidRequestAuditHistory.REALAUTH_SUCCESS){
            //3:成功:修改bidRequest信息: 状态,招标到期时间,发布时间,评审
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
            bidRequest.setPublishTime(nowDate);
            bidRequest.setDisableDate(DateUtils.addDays(nowDate,bidRequest.getDisableDays()));
            bidRequest.setNote(remark);
        }else{
            //4:失败:修改userinfo的位状态,bidRequest状态修改
            UserInfo userInfo = userInfoService.getUserInfoById(loginInfo.getId());
            userInfo.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfoService.update(userInfo);
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_UNDO);
        }
        //修改bidRequest
        update(bidRequest);
    }

    //投标页面高级查询
    public List<BidRequest> queryForList(QueryObject qo) {
        return bidRequestMapper.queryForList(qo);
    }

    //查询一条借款
    public BidRequest selectByPrimaryKey(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    /**
     * 投标:
     * @param bidRequestId
     * @param amount
     * @param loginInfo
     */
    public void bid(Long bidRequestId, BigDecimal amount,LoginInfo loginInfo) {
        //当前投资人的id:
        Long loginInfoId = loginInfo.getId();
        //01:判断:
        //标的状态
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        if(bidRequest.getBidRequestState() != Constants.BIDREQUEST_STATE_BIDDING){
            throw new DisplayableException("标的状态不在招标中!");
        };
        //投标人不能是借款人
        if(bidRequest.getCreateUserId().getId().equals(loginInfoId)){
            throw new DisplayableException("不能投自己的标!");
        }
        //投资金额不能大于可用余额
        Account account = accountService.selectByPrimaryKey(loginInfoId);
        if(amount.compareTo(account.getUsableAmount()) > 0 ){
            throw new DisplayableException("余额不足");
        }
        //如果投标剩余金额已经小于了最小可投标金额,允许一次投完
        if(bidRequest.getRemainAmount().compareTo(bidRequest.getMinBidAmount()) < 0){
            if(amount.compareTo(bidRequest.getRemainAmount()) != 0){
                throw new DisplayableException("请投完剩余金额");
            }
        }else{
        //投资金额不能小于最下投标金额
            if(amount.compareTo(bidRequest.getMinBidAmount()) < 0){
                throw new DisplayableException("不能小于最小投标金额!");
            }
        }
        //投资人针对该标总投标金额不能大于借款金额的 50%
        BigDecimal bidAmount = Constants.ZERO;
        List<Bid> bids = bidRequest.getBids();
        for (int i=0;i<bids.size();i++){
            Bid bid = bids.get(i);
            if(bid.getBidUserId().getId().equals(loginInfoId)){
                bidAmount = bidAmount.add(bid.getAvailableAmount());
            }
        }
        bidAmount = bidAmount.add(amount);
        //当前标的一般的金额:
        BigDecimal multiply = bidRequest.getBidRequestAmount().multiply(new BigDecimal("0.5"));
        if(bidAmount.compareTo(multiply) > 0){
            throw new DisplayableException("你投的标已经超过了50%!");
        }

        //投标
        //创建/保存投标对象
        bidService.insertBid(bidRequest,loginInfo,amount);
        //投资账户余额减少,投资账户冻结金额增加
        Account bidAccount = accountService.selectByPrimaryKey(loginInfoId);
        bidAccount.addUsableAmount(amount.negate());
        bidAccount.addFreezedAmount(amount);
        accountService.update(bidAccount);
        //产生投标流水
        accountFlowService.creatAccountFlowByBid(account,amount);

        //借款对象投标次数增加
        bidRequest.setBidCount(bidRequest.getBidCount() + 1);
        //借款对象当前投标总额增加
        bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));

        //如果满标
        if((bidRequest.getRemainAmount().compareTo(Constants.ZERO) == 0)){
            //借款对象的状态修改为满标一审
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
            //当前借款的投标对象状态修改
            bidService.updateStateByBidRequestId(bidRequestId,Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        }
        //修改借款对象:
        update(bidRequest);
    }

    /**
     * 满标一审
     * @param id
     * @param state
     * @param remark
     * @param loginInfo
     */
    public void audit1(Long id, int state, String remark, LoginInfo loginInfo) {
        //01判断
        //判断借款的状态要待一审
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        if(br.getBidRequestState() != Constants.BIDREQUEST_STATE_APPROVE_PENDING_1){
            throw new DisplayableException("状态不属于满标一审!");
        }

        //02设置审核相关信息
        bidRequestAuditHistoryMapperService.creatAuditHistory(state, remark,loginInfo,br);

        //03如果审核通过:
        if(state == BidRequestAuditHistory.REALAUTH_SUCCESS){
            //修改审核状态为满标二审
            br.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
            //批量修改投标对象的状态
            bidService.updateStateByBidRequestId(id,Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        }else{
            //04:审核失败:
            auditError(id, br);
        }
        //修改借款对象:
        update(br);
    }


    /**
     * 满标二审
     * @param id
     * @param state
     * @param remark
     * @param loginInfo
     */
    public void audit2(Long id, int state, String remark, LoginInfo loginInfo) {
        //01：判断是否是满标二审状态:
        //当前借款的标:
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        //借款人账户信息:
        Account borrowAccount = accountService.selectByPrimaryKey(br.getCreateUserId().getId());
        if (br.getBidRequestState() != Constants.BIDREQUEST_STATE_APPROVE_PENDING_2){
            throw new DisplayableException("状态不处于满标二审!");
        }

        //02:修改审核信息:
        bidRequestAuditHistoryMapperService.creatAuditHistory(state,remark,loginInfo,br);

        //03:审核成功==========================================:
        if(state == BaseAuditorDomain.REALAUTH_SUCCESS){
            //修改标:
            //1，借款状态修改为还款中
            br.setBidRequestState(Constants.BIDREQUEST_STATE_PAYING_BACK);
            //2，投标对象修改为还款中
            bidService.updateStateByBidRequestId(id,Constants.BIDREQUEST_STATE_PAYING_BACK);

            //投标人:
            List<Bid> bids = br.getBids();
            Map<Long,Account> map = new HashMap<Long, Account>();
            for(int i=0;i<bids.size();i++){
                Bid bid = bids.get(i);
                //该标的投资金额:
                BigDecimal availableAmount = bid.getAvailableAmount();
                Long bidUserId = bid.getBidUserId().getId();
                Account account = map.get(bidUserId);
                if(account == null){
                    account = accountService.selectByPrimaryKey(bidUserId);
                    map.put(bidUserId,account);
                }
                //1.冻结金额减少:
                account.addFreezedAmount(availableAmount.negate());
                //2.生成解冻流水
                accountFlowService.creatFreezedAmountFlow(account,availableAmount);
                //3.代收本金,利息增加:
                account.setUnReceivePrincipal(account.getUnReceivePrincipal().add(availableAmount));
                //代收利息:投资金额/借款总金额*总利息;
                BigDecimal interest = availableAmount.divide(br.getBidRequestAmount(),Constants.SCALE_CAL ,
                        RoundingMode.HALF_UP).multiply(br.getTotalRewardAmount()).setScale(Constants.SCALE_SAVE,RoundingMode.HALF_UP);
                account.setUnReceiveInterest(account.getUnReceiveInterest().add(interest));
            }
            //修改投资人账户信息:
            for (Account account : map.values()) {
                accountService.update(account);
            }

            //借款人:
            //借款金额:
            BigDecimal bidRequestAmount = br.getBidRequestAmount();
            //1.可用余额增加
            borrowAccount.setUsableAmount(bidRequestAmount);
            //2.产生收入流水
            accountFlowService.creatUsableAmountAccountFlow(borrowAccount,bidRequestAmount);
            //3.生成待还金额
            borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount()
                    .add(br.getBidRequestAmount()).add(br.getTotalRewardAmount()));
            //4.剩余授信额度减少
            borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().subtract(bidRequestAmount));
            //5.支付平台借款管理费用,可用余额减少
            BigDecimal managementCharge = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            borrowAccount.addUsableAmount(managementCharge.negate());
            //6.产生管理费支付流水
            accountFlowService.createAccountManagementChargeFlow(borrowAccount ,managementCharge );

            //7_生成借款人的还款计划对象
            paymentScheduleService.createrPaymentSchedule(br);

            //8_平台：收取借款手续费
            //1.平台账户金额增加
            SystemAccount systemAccount = systemAccountService.selectBySystemAccount();
            BigDecimal managementChargeMoney = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(managementChargeMoney));

            //2.产生平台流水
            systemAccountFlowService.createrSystemAccountFlow(systemAccount,managementChargeMoney);
            systemAccountService.update(systemAccount);
            //修改借款人账户:
            accountService.update(borrowAccount);
        }else{
        //04:审核失败:同满标一审==========================================:
            auditError(id,br);
        }

        //借款人基本信息位状态无论成功与失败都会修改:
        UserInfo borrowUserInfo = userInfoService.getUserInfoById(br.getCreateUserId().getId());
        borrowUserInfo.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfoService.update(borrowUserInfo);
        //修改借款对象:
        update(br);
    }

    /**
     * 还款
     * @param id
     */
    public void returnMoney(Long id,LoginInfo loginInfo) {
        PaymentSchedule ps = paymentScheduleService.selectByPrimaryKey(id);
        //还款人账户:
        Account bidAccount = accountService.selectByPrimaryKey(ps.getCreateUserId());
        //01:判断:====================
        //判断还款计划状态
        if (ps.getState() != Constants.PAYMENT_STATE_NORMAL){
            throw  new DisplayableException("状态有误!");
        }
        //判断还款用户
        if(ps.getCreateUserId() != loginInfo.getId()){
            throw  new DisplayableException("你确定要帮别人还款吗?");
        }
        //判断金额
        if(bidAccount.getUsableAmount().compareTo(ps.getTotalAmount()) < 0){
            throw  new DisplayableException("账户余额不足!");
        }

        //还款账单:======================
        //返款时间
        ps.setPayDate(new Date());
        //还款状态
        ps.setState(Constants.PAYMENT_STATE_DONE);
        paymentScheduleService.update(ps);

        //02:还款人:==================
        //还款:可用余额减少
        bidAccount.addUsableAmount(ps.getTotalAmount().negate());
        //还款流水
        accountFlowService.createrReturnAccountFlow(bidAccount,ps.getTotalAmount());
        //授信额度增加
        bidAccount.setRemainBorrowLimit(bidAccount.getRemainBorrowLimit().add(ps.getPrincipal()));
        //待还总额减少
        bidAccount.setUnReturnAmount(bidAccount.getUnReturnAmount().subtract(ps.getTotalAmount()));
        //还款计划:修改还款对象
        paymentScheduleService.updateByPaymentSchedule(ps);
        accountService.update(bidAccount);

        //03:投资人:===================
        List<PaymentScheduleDetail> details = ps.getDetails();
        Map<Long,Account> map  = new HashMap<Long, Account>();
        for (int i = 0; i<details.size(); i++){
            //该期的每个收款计划
            PaymentScheduleDetail detail = details.get(i);
            //本笔金额
            BigDecimal bidAmount = detail.getTotalAmount();
            Long bidId = detail.getToLoginInfoId();
            Account toAccount = map.get(bidId.longValue());
            if(toAccount == null){
                toAccount = accountService.selectByPrimaryKey(detail.getToLoginInfoId());
                map.put(bidId.longValue(),toAccount);
            }
            //01:修改收款:
            //可用余额增加
            toAccount.addUsableAmount(bidAmount);
            //产生收款流水
            accountFlowService.creatBidAccountFlow(toAccount,bidAmount);
            //待收本金减少
            toAccount.setUnReceivePrincipal(toAccount.getUnReceivePrincipal().subtract(detail.getPrincipal()));
            //待收利息减少
            toAccount.setUnReceiveInterest(toAccount.getUnReceiveInterest().subtract(detail.getInterest()));
            //支付利息管理费用:可用余额减少
            BigDecimal bigDecimal = CalculatetUtil.calInterestManagerCharge(detail.getInterest());
            toAccount.addUsableAmount(bigDecimal.negate());
            //产生利息管理费流水
            accountFlowService.creatInterestManagerChargeAccountFlow(toAccount,bigDecimal);

            //02:修改收款计划:收款时间
            detail.setPayDate(new Date());
            paymentScheduleDetailService.update(detail);
        }

        //修改收款人账户:
        for (Account account : map.values()) {
            accountService.update(account);
        }

        //04:平台=========================
        //收取利息管理费:
        SystemAccount systemAccount = systemAccountService.selectBySystemAccount();
        //可用余额增加
        BigDecimal interestManagerCharge = CalculatetUtil.calInterestManagerCharge(ps.getInterest());
        systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestManagerCharge));
        //产生利息管理费用的收取流水
        systemAccountFlowService.createrInterestManagerChargeSystemAccountFlow(systemAccount,interestManagerCharge);
        //修改平台账户:
        systemAccountService.update(systemAccount);
        //05:已还清:======================
        if (bidAccount.getUnReturnAmount().compareTo(new BigDecimal("0.00")) == 0){
            BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(ps.getBidRequestId());
            //借款对象状态修改
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            bidRequestMapper.updateByPrimaryKey(bidRequest);
            //bid批量修改
            List<Bid> bids = bidRequest.getBids();
            for (Bid bid : bids) {
                bidService.updateStateByBidRequestId(bid.getId(),Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            }
        }
    }

    /**
     * 满标一审,二审失败:
     * @param id
     * @param br
     */
    private void auditError(Long id, BidRequest br) {
        //04如果审核失败:
        //借款状态修改为满审失败
        br.setBidRequestState(Constants.BIDREQUEST_STATE_REJECTED);
        //批量修改投标对象的状态
        bidService.updateStateByBidRequestId(id,Constants.BIDREQUEST_STATE_REJECTED);

        //退钱
        //根据借款对象的投标对象拿到投资人账户
        List<Bid> bids = br.getBids();
        Map<Long,Account> map = new HashMap<Long,Account>();
        for(int i = 0;i<bids.size(); i++){
            Bid bid = bids.get(i);
            //投资人id:
            Long bidUserId = bid.getBidUserId().getId();
            Account bidAccount = map.get(bidUserId);
            if(bidAccount != null){
                //修改投资人冻结金额,可用余额,
                bidAccount.addUsableAmount(bid.getAvailableAmount());
                bidAccount.addFreezedAmount(bid.getAvailableAmount().negate());
            }else{
                bidAccount = accountService.selectByPrimaryKey(bidUserId);
                bidAccount.addUsableAmount(bid.getAvailableAmount());
                bidAccount.addFreezedAmount(bid.getAvailableAmount().negate());
                map.put(bidUserId,bidAccount);
            }
            //产生投标失败流水
            accountFlowService.createBidErrorFlow(bidAccount,bid.getAvailableAmount());
        }

        //修改投资人账户:
        for (Account account : map.values()) {
            accountService.update(account);
        }

        //移除借款人是否有申请中的借款状态
        UserInfo bidUserInfo = userInfoService.getUserInfoById(br.getCreateUserId().getId());
        bidUserInfo.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfoService.update(bidUserInfo);
    }


    /**
     * 修改:
     * @param bidRequest
     */
    public void update(BidRequest bidRequest){
        int update = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if(update == 0){
            throw new DisplayableException("乐观锁异常!");
        }
    }
}
