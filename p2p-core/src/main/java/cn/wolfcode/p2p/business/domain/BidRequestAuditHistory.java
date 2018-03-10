package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseAuditorDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 标的审核记录
 */
@Setter
@Getter
public class BidRequestAuditHistory extends BaseAuditorDomain{
    private Long bidRequestId;      //对应标的id;
    private int auditType;          //标的审核类型;
}
