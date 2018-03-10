package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Account;

public interface AccountMapper {
    /**
     * 添加一条数据
     * @param record
     * @return
     */
    int insert(Account record);

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Account record);
}