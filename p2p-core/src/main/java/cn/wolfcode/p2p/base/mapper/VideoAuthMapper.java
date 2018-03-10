package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.List;

public interface VideoAuthMapper {
    int insert(VideoAuth record);

    /**
     * 总条数
     * @param qo
     * @return
     */
    int queryForCount(QueryObject qo);

    /**
     * 查询的数据集合
     * @param qo
     * @return
     */
    List<VideoAuth> queryForList(QueryObject qo);


    // VideoAuth selectByPrimaryKey(Long id);
}