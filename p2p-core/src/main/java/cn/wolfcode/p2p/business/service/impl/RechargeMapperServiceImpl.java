package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.business.domain.Recharge;
import cn.wolfcode.p2p.business.mapper.RechargeMapper;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IRechargeMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 线下充值
 */
@Service
public class RechargeMapperServiceImpl implements IRechargeMapperService {
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;

    public void insert(Recharge recharge, LoginInfo loginInfo) {
        Recharge recharge1 = new Recharge();
        recharge1.setAmount(recharge.getAmount());
        recharge1.setBankInfoId(recharge.getBankInfoId());
        recharge1.setNote(recharge1.getNote());
        recharge1.setTradeCode(recharge.getTradeCode());
        recharge1.setTradeTime(recharge.getTradeTime());
        recharge1.setApplierId(loginInfo);
        recharge1.setApplyTime(new Date());
        recharge1.setNote(recharge.getNote());
        recharge1.setState(Recharge.REALAUTH_NOT);
        //添加充值数据:
        rechargeMapper.insert(recharge1);
    }

    public PageResult query(RechargeQueryObject qo) {
        int totalCount = rechargeMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<Recharge> list = rechargeMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //审核线上充值
    public void audit(Long id, int state, String remark, LoginInfo loginInfo) {
        //01:判断:状态是否在待审核中:
        Recharge recharge = rechargeMapper.selectByPrimaryKey(id);
        if(recharge.getState() != Recharge.REALAUTH_NOT){
            throw new DisplayableException("状态不在申请中!");
        }
        //设置审核信息:
        recharge.setAuditorId(loginInfo);
        recharge.setAuditTime(new Date());
        recharge.setRemark(remark);
        recharge.setState(state);
        update(recharge);
        //成功:
        if(state ==Recharge.REALAUTH_SUCCESS){
            //充值人的账户可用金额增加,
            Account account = accountService.selectByPrimaryKey(recharge.getApplierId().getId());
            account.setUsableAmount(account.getUsableAmount().add(recharge.getAmount()));
            accountService.update(account);
            //添加流水:
            accountFlowService.creatAccountFlow(account,recharge.getAmount());
        }
        //失败:啥都不干
    }

    private void update(Recharge recharge) {
        int i = rechargeMapper.updateByPrimaryKey(recharge);
        if(i == 0){
            throw new DisplayableException("审核失败[乐观锁异常]");
        }
    }

}
