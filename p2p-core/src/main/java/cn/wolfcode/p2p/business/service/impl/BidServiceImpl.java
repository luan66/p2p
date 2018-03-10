package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.business.domain.Bid;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.mapper.BidMapper;
import cn.wolfcode.p2p.business.service.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BidServiceImpl implements IBidService {
    @Autowired
    private BidMapper bidMapper;

    @Override
    public void insert(Bid bid) {
        bidMapper.insert(bid);
    }

    @Override
    public void insertBid(BidRequest bidRequest, LoginInfo loginInfo, BigDecimal amount) {
        Bid bid = new Bid();
        bid.setActualRate(bidRequest.getCurrentRate());
        bid.setBidRequestId(bidRequest.getId());
        bid.setAvailableAmount(amount);
        bid.setBidRequestState(bidRequest.getBidRequestState());
        bid.setBidRequestTitle(bidRequest.getTitle());
        bid.setBidTime(new Date());
        bid.setBidUserId(loginInfo);
        bidMapper.insert(bid);
    }

    public void updateStateByBidRequestId(Long bidRequestId ,int state) {
        bidMapper.updateStateByBidRequestId(bidRequestId,state);
    }
}
