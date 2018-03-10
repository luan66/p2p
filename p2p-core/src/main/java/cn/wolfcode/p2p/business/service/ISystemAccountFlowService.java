package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * 系统账户流水
 */
public interface ISystemAccountFlowService {

    void createrSystemAccountFlow(SystemAccount systemAccount, BigDecimal managementChargeMoney);

    /**
     * 收取利息管理费:
     * @param interestManagerCharge
     */
    void createrInterestManagerChargeSystemAccountFlow(SystemAccount systemAccount,BigDecimal interestManagerCharge);

}

