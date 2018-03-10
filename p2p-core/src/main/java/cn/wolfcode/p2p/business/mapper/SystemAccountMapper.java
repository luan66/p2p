package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {
    int insert(SystemAccount record);

    int updateByPrimaryKey(SystemAccount record);

    SystemAccount selectBySystemAccount();
}