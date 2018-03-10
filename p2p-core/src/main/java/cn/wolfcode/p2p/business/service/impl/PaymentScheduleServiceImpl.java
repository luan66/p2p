package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.business.domain.Bid;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleMapper;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;
import cn.wolfcode.p2p.util.CalculatetUtil;
import cn.wolfcode.p2p.util.Constants;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 还款计划
 */
@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService{
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;

    /**
     * 生成还款计划:
     */
    public void createrPaymentSchedule(BidRequest br) {
        //还款期数:
        int monthes2Return = br.getMonthes2Return();
        //非最后一期的利息总额
        BigDecimal totalInterestAmount = Constants.ZERO;
        BigDecimal totalPrincipalAmount = Constants.ZERO;

        for (int i=0;i<monthes2Return;i++){
            PaymentSchedule ps = new PaymentSchedule();
            ps.setBidRequestId(br.getId());
            ps.setBidRequestTitle(br.getTitle());
            ps.setBidRequestType(Constants.BIDREQUEST_TYPE_NORMAL);
            ps.setCreateUserId(br.getCreateUserId().getId());
            //截止时间:用标的发布时间
            ps.setDeadLine(DateUtils.addMonths(br.getPublishTime(),i+1));
            ps.setMonthIndex(i+1);
            ps.setState(Constants.PAYMENT_STATE_NORMAL);
            ps.setReturnType(br.getReturnType());
            //判断是否是最后一期:
            if(i+1 >= monthes2Return){
                ps.setInterest(br.getTotalRewardAmount().subtract(totalInterestAmount));
                ps.setPrincipal(br.getBidRequestAmount().subtract(totalPrincipalAmount));
                ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));
            }else{
                //本期还款总金
                BigDecimal returnTotalAmount = CalculatetUtil.calMonthToReturnMoney(br.getReturnType(), br.getBidRequestAmount(), br.getCurrentRate(),
                        i + 1, monthes2Return).setScale(Constants.SCALE_SAVE , RoundingMode.HALF_UP);
                ps.setTotalAmount(returnTotalAmount);
                //本期还款利息
                BigDecimal returnInterest = CalculatetUtil.calMonthlyInterest(br.getReturnType(), br.getBidRequestAmount(), br.getCurrentRate(),
                        i + 1, monthes2Return).setScale(Constants.SCALE_SAVE , RoundingMode.HALF_UP);
                ps.setInterest(returnInterest);
                //本期还款本金
                BigDecimal returnPrincipal = returnTotalAmount.subtract(returnInterest).setScale(Constants.SCALE_SAVE , RoundingMode.HALF_UP);
                ps.setPrincipal(returnPrincipal);

                //设置非最后一期的利息总额
                totalInterestAmount = totalInterestAmount.add(returnInterest);
                totalPrincipalAmount = totalPrincipalAmount.add(returnPrincipal);
            }

            //保存:
            paymentScheduleMapper.insert(ps);

            //创建每期的收款计划:
            createPaymentScheduleDetail(ps,br);
        }
    }

    public PageResult query(QueryObject qo) {
        int totalCount = paymentScheduleMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<PaymentSchedule> list = paymentScheduleMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    @Override
    public PaymentSchedule selectByPrimaryKey(Long id) {
        return paymentScheduleMapper.selectByPrimaryKey(id);
    }

    //还款:修改还款计划:
    public void updateByPaymentSchedule(PaymentSchedule ps) {
        ps.setState(Constants.PAYMENT_STATE_DONE);
        ps.setPayDate(new Date());
    }

    @Override
    public void update(PaymentSchedule ps) {
        int num = paymentScheduleMapper.updateByPrimaryKey(ps);
        if(num == 0){
            throw new DisplayableException("修改还款对象异常,[乐观锁异常]");
        }
    }

    /**
     * 创建每期的收款计划:
     * @param ps
     * @param br
     */
    private void createPaymentScheduleDetail(PaymentSchedule ps, BidRequest br) {
        List<Bid> bids = br.getBids();

        //非最后一期的利息总额
        BigDecimal totalInterestAmount = Constants.ZERO;
        BigDecimal totalPrincipalAmount = Constants.ZERO;

        for (int i = 0; i<bids.size(); i++) {
            //每一个投标对象
            Bid bid = bids.get(i);

            PaymentScheduleDetail psd = new PaymentScheduleDetail();
            psd.setBidAmount(bid.getAvailableAmount());
            psd.setBidId(bid.getId());
            psd.setBidRequestId(br.getId());
            psd.setDeadline(ps.getDeadLine());
            psd.setFromLoginInfoId(br.getCreateUserId().getId());
            psd.setMonthIndex(i+1);
            psd.setPaymentScheduleId(ps.getId());
            psd.setReturnType(ps.getReturnType());
            psd.setToLoginInfoId(bid.getBidUserId().getId());

            //该投标占该借款总额的:
            BigDecimal scale = bid.getAvailableAmount().divide(br.getBidRequestAmount() , Constants.SCALE_CAL , RoundingMode.HALF_UP);
            //该期的最后一标:所有标加起来等于总还款:
            if(i+1 == bids.size()){
                psd.setPrincipal(ps.getPrincipal().subtract(totalPrincipalAmount));
                psd.setInterest(ps.getInterest().subtract(totalInterestAmount));
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
            }else{
                //本期本标收利息:本标金额/总借款金额*当前期利息
                BigDecimal toInterest = scale.multiply(ps.getInterest()).setScale(Constants.SCALE_SAVE , RoundingMode.HALF_UP);
                psd.setInterest(toInterest);
                //本期本标收本金:本标金额/总借款金额*当前期本金
                BigDecimal toPrincipal = ps.getPrincipal().multiply(scale).setScale(Constants.SCALE_SAVE , RoundingMode.HALF_UP);
                psd.setPrincipal(toPrincipal);
                //本期本标收总金额:本标金额/总借款金额*当前期总金;
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));

                totalPrincipalAmount = totalPrincipalAmount.add(toPrincipal);
                totalInterestAmount = totalInterestAmount.add(toInterest);
            }

            //保存:
            paymentScheduleDetailService.insert(psd);

        }

    }
}
