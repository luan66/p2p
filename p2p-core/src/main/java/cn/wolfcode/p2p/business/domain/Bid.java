package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 单项投资记录
 */
@Setter
@Getter
public class Bid extends BaseDomain{

    private BigDecimal actualRate;          //实际年利率(和标的年利率一致,冗余数据)
    private BigDecimal availableAmount;     //投资金额
    private Long bidRequestId;              //来自哪个借款标
    private String bidRequestTitle;         //标的名称(冗余数据)
    private LoginInfo bidUserId;              //投资人
    private Date bidTime;                   //投标时间
    private int bidRequestState;            //标的状态(冗余数据)

}
