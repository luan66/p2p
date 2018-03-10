package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 登录记录的条件:
 */
@Setter
@Getter
public class IplogQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private int state = -1; //状态：
    private String username; //登录用户：

    /**
     * 处理截止日期:当天最后一秒;
     */
    public Date getEndTime(){
        return DateUtil.getEndDate(this.endTime);
    }
}
