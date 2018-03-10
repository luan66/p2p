package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.Bid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BidMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    List<Bid> selectAll();

    int updateByPrimaryKey(Bid record);

    List<Bid> listByBidRequestKId(Long id);

    void updateStateByBidRequestId(@Param("bidRequestId") Long bidRequestId, @Param("state") int state);
}