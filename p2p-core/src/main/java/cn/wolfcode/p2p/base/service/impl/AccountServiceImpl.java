package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IUserInfoService userInfoService;

    //初始化账户信息:
    public void iniAccount(Account account) {
        accountMapper.insert(account);
    }

    //查询一条数据
    public Account selectByPrimaryKey(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    //修改账号信息
    public void update(Account account) {
        int i = accountMapper.updateByPrimaryKey(account);
        if(i == 0){
            throw new DisplayableException("账户资料修改失败[乐观锁异常!]");
        }
    }

}
