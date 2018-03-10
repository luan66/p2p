package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 还款计划:
 */
@Setter
@Getter
public class PaymentSchedule extends BaseDomain{

    //本期还款日期:
    private Date deadLine;

    //本期真实还款日期:
    private Date payDate;

    //本期还款总额:
    private BigDecimal totalAmount;

    //本期还款本金:
    private BigDecimal principal;

    //本期还款利息:
    private BigDecimal interest;

    //还款期数
    private int monthIndex;

    //还款状态: 按时,超时
    private int state;

    //借款类型:
    private int bidRequestType;

    //还款类型
    private int returnType;

    //借款标对象:
    private Long bidRequestId;

    //借款人:
    private Long createUserId;

    //借款标题:
    private String bidRequestTitle;

    //收款计划集合:
    private List<PaymentScheduleDetail> details = new ArrayList<PaymentScheduleDetail>();


    public String getStateDisplay(){
        switch (this.state){
            case  Constants.PAYMENT_STATE_NORMAL : return "还款中";
            case  Constants.PAYMENT_STATE_DONE : return "还款成功";
            case  Constants.PAYMENT_STATE_OVERDUE : return "还款失败";
        }
        return "未知";
    }
}
