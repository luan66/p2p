package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 客户账户信息
 */
@Setter
@Getter
public class Account extends BaseDomain{
    private int version;                    //对象版本信息（用作乐观锁）
    private String tradePassword;           //交易密码
    private BigDecimal usableAmount = Constants.ZERO;        //账户可用余额
    private BigDecimal freezedAmount = Constants.ZERO;       //账户冻结金额
    private BigDecimal unReceiveInterest = Constants.ZERO;   //账户待收利息
    private BigDecimal unReceivePrincipal = Constants.ZERO;  //账户待收本金
    private BigDecimal unReturnAmount = Constants.ZERO;      //账户待还金额
    private BigDecimal remainBorrowLimit = Constants.BORROW_LIMIT;   //账户剩余授信额度
    private BigDecimal borrowLimitAmount = Constants.BORROW_LIMIT;         //账户授信额度

    //修改可用余额:
    public void addUsableAmount(BigDecimal amount){
        setUsableAmount(getUsableAmount().add(amount));
    }
    //修改冻结余额:
    public void addFreezedAmount(BigDecimal amount){
        setFreezedAmount(getFreezedAmount().add(amount));
    }

    //账户总额: 可用余额+冻结金额+代收本金
    public BigDecimal getTotalAmount(){
        return usableAmount.add(freezedAmount).add(unReceiveInterest);
    }
}
