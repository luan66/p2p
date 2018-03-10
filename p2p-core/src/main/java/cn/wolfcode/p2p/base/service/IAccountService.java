package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.Account;

public interface IAccountService {
    /**
     * 初始化账户信息
     * @param account
     */
    void iniAccount(Account account);

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    Account selectByPrimaryKey(Long id);

    /**
     * 修改账号的信息
     * @param account
     */
    void update(Account account);
}
