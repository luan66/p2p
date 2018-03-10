package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.mapper.BidRequestAuditHistoryMapper;
import cn.wolfcode.p2p.business.service.IBidRequestAuditHistoryService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 记录审核标的记录
 */
@Service
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    //添加
    public void insert(BidRequestAuditHistory bidRequestAudit) {
        bidRequestAuditHistoryMapper.insert(bidRequestAudit);
    }

    @Override
    public void creatAuditHistory(int state, String remark, LoginInfo loginInfo, BidRequest br) {
        BidRequestAuditHistory bidRequestAudit = new BidRequestAuditHistory();
        bidRequestAudit.setState(state);
        bidRequestAudit.setRemark(remark);
        bidRequestAudit.setAuditTime(new Date());
        bidRequestAudit.setAuditorId(loginInfo);
        bidRequestAudit.setApplyTime(br.getApplyTime());
        bidRequestAudit.setApplierId(br.getCreateUserId());
        bidRequestAudit.setBidRequestId(br.getId());
        bidRequestAudit.setAuditType(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        bidRequestAuditHistoryMapper.insert(bidRequestAudit);
    }

}
