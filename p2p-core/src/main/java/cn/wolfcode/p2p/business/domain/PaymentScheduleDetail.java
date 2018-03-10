package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收款计划
 */
@Setter
@Getter
public class PaymentScheduleDetail extends BaseDomain{

    //投标金额:
    private BigDecimal bidAmount;

    //投标对象
    private Long bidId;

    //投标总金额:
    private BigDecimal totalAmount;

    //代收本金:
    private BigDecimal principal;

    //代收利息:
    private BigDecimal interest;

    //本期期数:
    private int monthIndex;

    //本期收款时间
    private Date deadline;

    //真实收款时间
    private Date payDate;

    //借款对象:
    private Long bidRequestId;

    //收款类型:
    private int returnType;

    //收款计划:
    private Long paymentScheduleId;

    //借款人:
    private Long fromLoginInfoId;

    //收款人:
    private Long toLoginInfoId;



}
