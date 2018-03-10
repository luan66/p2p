package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.mapper.SystemAccountMapper;
import cn.wolfcode.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统平台账户
 */
@Service
public class SystemAccountServiceImpl implements ISystemAccountService {
    @Autowired
    private SystemAccountMapper systemAccountMapper;

    @Override
    public SystemAccount selectBySystemAccount() {
        return systemAccountMapper.selectBySystemAccount();
    }

    @Override
    public void update(SystemAccount systemAccount) {
        int i = systemAccountMapper.updateByPrimaryKey(systemAccount);
        if(i == 0){
            throw new DisplayableException("修改账户失败,乐观锁异常!");
        }
    }
}
