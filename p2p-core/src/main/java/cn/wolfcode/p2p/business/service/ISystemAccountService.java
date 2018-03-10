package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.SystemAccount;

/**
 * 系统平台账户:
 */
public interface ISystemAccountService {
    /**
     * 查询系统账户
     * @return
     */
    SystemAccount selectBySystemAccount();

    void update(SystemAccount systemAccount);

}
