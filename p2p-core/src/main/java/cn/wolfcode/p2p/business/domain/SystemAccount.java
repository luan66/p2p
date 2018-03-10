package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 系统平台账户:
 */
@Setter
@Getter
public class SystemAccount extends BaseDomain{
    private int version;                //版本
    private BigDecimal usableAmount;    //可用金额
    private BigDecimal freezedAmount;   //冻结金额

}
