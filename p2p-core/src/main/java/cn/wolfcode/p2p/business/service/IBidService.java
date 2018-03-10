package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.business.domain.Bid;
import cn.wolfcode.p2p.business.domain.BidRequest;

import java.math.BigDecimal;

/**
 * 投资人.
 */
public interface IBidService {
    /**
     * 添加数据
     * @param bid
     */
    void insert(Bid bid);

    /**
     * 添加对应的投标记录
     */
    public void insertBid(BidRequest bidRequest, LoginInfo loginInfo, BigDecimal amount);

    /**
     * 修改投标状态
     * @param bidRequestId
     */
    public void updateStateByBidRequestId(Long bidRequestId ,int state);
}
