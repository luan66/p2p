package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.Account;

import java.math.BigDecimal;

/**
 * 充值流水
 */
public interface IAccountFlowService {
    /**
     * 账户充值:
     */
    public void creatAccountFlow(Account account, BigDecimal amount);

    /**
     * 投标流水:
     */
    public void creatAccountFlowByBid(Account account, BigDecimal amount);

    /**
     * 账户可用余额添加
     * @param account
     * @param availableAmount
     */
    public void createBidErrorFlow(Account account,BigDecimal availableAmount);

    /**
     * 满标审核失败:冻结金额减少流水单
     * @param account
     * @param availableAmount
     */
    void creatFreezedAmountFlow(Account account, BigDecimal availableAmount);

    /**
     * 借款成功,账户可用余额添加
     * @param borrowAccount
     * @param bidRequestState
     */
    void creatUsableAmountAccountFlow(Account borrowAccount, BigDecimal bidRequestState);

    /**
     * 支付平台管理费
     * @param borrowAccount
     * @param managementCharge
     */
    void createAccountManagementChargeFlow(Account borrowAccount, BigDecimal managementCharge);

    /**
     * 生成还款计划流水
     * @param bidAccount
     * @param totalAmount
     */
    void createrReturnAccountFlow(Account bidAccount, BigDecimal totalAmount);

    /**
     * 还款时,收款人账户可用余额添加流水:
     * @param toAccount
     * @param bidAmount
     */
    void creatBidAccountFlow(Account toAccount, BigDecimal bidAmount);

    /**
     * 支付利息管理费:
     * @param toAccount
     * @param bigDecimal
     */
    void creatInterestManagerChargeAccountFlow(Account toAccount, BigDecimal bigDecimal);
}
