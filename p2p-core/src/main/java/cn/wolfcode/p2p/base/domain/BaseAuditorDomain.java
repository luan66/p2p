package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *文件上传的共同字段
 */
@Setter
@Getter
public class BaseAuditorDomain extends BaseDomain{

    //审核中:
    public static final int REALAUTH_NOT = 0;
    //审核成功:
    public static final int REALAUTH_SUCCESS= 1;
    //审核失败:
    public static final int REALAUTH_ERROR= 2;

    protected int state = REALAUTH_NOT;//申请状态;
    private String remark;              //评价
    protected LoginInfo applierId;    //申请人
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date applyTime;         //申请时间

    protected LoginInfo auditorId;   //审核人
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date auditTime;       //审核时间

    //格式化状态:
    public String getStateDisplay(){
        if(state == RealAuth.REALAUTH_NOT){
            return "审核中";
        }else if (state == RealAuth.REALAUTH_SUCCESS){
            return "审核成功";
        }else if (state == RealAuth.REALAUTH_ERROR){
            return "审核失败";
        }else {
            return "未知";
        }
    }
}
