package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;

public interface IBidRequestAuditHistoryService {
    void insert(BidRequestAuditHistory bidRequestAudit);

    /**
     * 查询审核信息:
     * @param state
     * @param remark
     * @param loginInfo
     * @param br
     */
    void creatAuditHistory(int state, String remark, LoginInfo loginInfo, BidRequest br);
}
