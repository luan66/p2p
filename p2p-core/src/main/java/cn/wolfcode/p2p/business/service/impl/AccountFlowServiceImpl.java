package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.business.domain.AccountFlow;
import cn.wolfcode.p2p.business.mapper.AccountFlowMapper;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值流水
 */
@Service
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    //生成流水账单,
    public void createBaseFlow(Account account, BigDecimal amount,String note,int type) {
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setAccountId(account.getId());
        accountFlow.setActionTime(new Date());
        accountFlow.setActionType(type);
        accountFlow.setAmount(amount);
        accountFlow.setFreezedAmount(account.getFreezedAmount());
        accountFlow.setNote(note);
        accountFlow.setUsableAmount(account.getUsableAmount());
        accountFlowMapper.insert(accountFlow);
    }

    //账户充值:
    public void creatAccountFlow(Account account, BigDecimal amount) {
        String note = "线下充值:"+amount.setScale(Constants.SCALE_SHOW)+"元";
        createBaseFlow(account,amount,note,Constants.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
    }

    @Override
    public void creatAccountFlowByBid(Account account,BigDecimal amount) {
        String note = "本次投资了"+amount+"元";
        createBaseFlow(account,amount,note,Constants.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
    }

    @Override
    public void createBidErrorFlow(Account account,BigDecimal availableAmount) {
        String note = "满标一审失败,账户余额添加:"+availableAmount+"元";
        createBaseFlow(account,availableAmount,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    public void creatFreezedAmountFlow(Account account, BigDecimal availableAmount) {
        String note = "满标二审成功,冻结金额减少:"+availableAmount+"元";
        createBaseFlow(account,availableAmount,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    @Override
    public void creatUsableAmountAccountFlow(Account borrowAccount, BigDecimal bidRequestState) {
        String note = "满标二审成功,账户可用余额增加:"+bidRequestState+"元";
        createBaseFlow(borrowAccount,bidRequestState,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    @Override
    public void createAccountManagementChargeFlow(Account borrowAccount, BigDecimal managementCharge) {
        String note = "满标二审成功,支付平台管理费:"+managementCharge+"元";
        createBaseFlow(borrowAccount,managementCharge,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    @Override
    public void createrReturnAccountFlow(Account bidAccount, BigDecimal totalAmount) {
        String note = "还款:"+totalAmount+"元";
        createBaseFlow(bidAccount,totalAmount,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    @Override
    public void creatBidAccountFlow(Account toAccount, BigDecimal bidAmount) {
        String note = "收款时,账户可用余额添加:"+bidAmount+"元";
        createBaseFlow(toAccount,bidAmount,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }

    //支付利息管理费:
    public void creatInterestManagerChargeAccountFlow(Account toAccount, BigDecimal bigDecimal) {
        String note = "收款时,支付利息管理费:"+bigDecimal+"元";
        createBaseFlow(toAccount,bigDecimal,note,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
    }
}
