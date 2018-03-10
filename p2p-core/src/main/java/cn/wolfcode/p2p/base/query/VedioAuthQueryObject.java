package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 视频认证
 */
@Setter
@Getter
public class VedioAuthQueryObject extends QueryObject{
    private int state = -1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //处理结束时间:
    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
