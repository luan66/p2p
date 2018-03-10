package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.List;

/**
 * 登录记录:
 */
public interface IplogMapper {
    /**
     * 添加登录记录:
     * @param record
     * @return
     */
    int insert(Iplog record);

    /**
     * 高级查询总条数
     * @param qo
     * @return
     */
    int queryForCount(QueryObject qo);

    /**
     * 高级查询总数据
     * @param qo
     * @return
     */
    List<Iplog> queryForList(QueryObject qo);
}