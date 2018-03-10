package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统账户流水
 */
@Setter
@Getter
public class SystemAccountFlow extends BaseDomain{
    //创建日期:
    private Date createdDate;

    //交易类型
    private int actionType;

    //金额:
    private BigDecimal amount;

    //交易备注:
    private String note;

    //可用余额:
    private BigDecimal usableAmount;

    //冻结金额:
    private BigDecimal freezedAmount;
}
