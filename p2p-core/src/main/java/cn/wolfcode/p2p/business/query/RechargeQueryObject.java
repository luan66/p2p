package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 审核线下充值条件
 */
@Setter
@Getter
public class RechargeQueryObject extends QueryObject{
    private int state = -1;
    //开户行:
    private int bankInfoId = -1;
    //交易号
    private String tradeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //处理最后时间
    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
