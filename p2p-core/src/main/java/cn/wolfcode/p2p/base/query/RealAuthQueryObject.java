package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实名认证
 */
@Getter
@Setter
public class RealAuthQueryObject extends QueryObject{
    private int state = -1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //处理endDate:
    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
