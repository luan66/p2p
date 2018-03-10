package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户流水
 */
@Setter
@Getter
public class AccountFlow extends BaseDomain{
    //交易的类型(进还是出):
    private int actionType;
    //金额:
    private BigDecimal amount;
    //审核配注:
    private String note;
    //账户可用金额:
    private BigDecimal usableAmount;
    //账户冻结金额:
    private BigDecimal freezedAmount;
    //交易时间:
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actionTime;
    //对应的账户:
    private Long accountId;

}
