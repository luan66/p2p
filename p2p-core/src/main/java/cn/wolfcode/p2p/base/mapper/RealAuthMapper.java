package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.List;

public interface RealAuthMapper {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    List<RealAuth> selectAll();

    int updateByPrimaryKey(RealAuth record);

    /**
     * 高级查询总数据
     * @param qo
     * @return
     */
    int queryForCount(QueryObject qo);

    /**
     * 高级查询总数据
     * @param qo
     * @return
     */
    List<RealAuth> queryForList(QueryObject qo);
}