package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 还款条件对象
 */
@Setter
@Getter
public class PaymentScheuleQueryObject extends QueryObject{

    //状态:
    private int state = -1;
    private Date beginDate;
    private Date endDate;

    //当前标id
    private Long bidRequestId;
}
