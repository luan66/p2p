package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.business.domain.BidRequest;

import java.util.List;

public interface BidRequestMapper {
    /**
     * 添加一调数据
     * @param record
     * @return
     */
    int insert(BidRequest record);

    /**
     * 发标审核高级查询总条数
     * @param qo
     * @return
     */
    int queryForCount(QueryObject qo);

    /**
     * 发标审核高级查询数据集合
     * @param qo
     * @return
     */
    List<BidRequest> queryForList(QueryObject qo);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest bidRequest);
}