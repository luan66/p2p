package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资料认证
 */
@Setter
@Getter
public class UserFileQueryObject extends QueryObject{
    private int state = -1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //材料用户的id:
    private Long userId;

    //处理一天的最后一秒
    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
