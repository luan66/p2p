package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    /**
     * 总数据
     * @param qo
     * @return
     */
    int queryForCount(QueryObject qo);

    /**
     * 总条数
     * @param qo
     * @return
     */
    List<SystemDictionary> queryForList(QueryObject qo);
}