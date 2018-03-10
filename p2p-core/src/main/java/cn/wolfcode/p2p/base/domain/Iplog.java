package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 登录记录:
 */
@Setter
@Getter
public class Iplog extends BaseDomain{
    //登陆成功状态:
    public static final int LOGIN_SUCCESS = 1;
    //登录失败状态:
    public static final int LOGIN_ERROR = 0;

    //前台用户;
    public static final int USERTYPE_WEBSITE = 0;
    //公司用户;
    public static final int USERTYPE_MGRSITE = 1;

    private String username;            //登录的用户;
    private String ip;                  //登录的ip;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginTime;             //登录的时间;
    private int state = LOGIN_SUCCESS;  //登录的状态;
    private int userType = USERTYPE_WEBSITE;//登录人员类型;

    //格式化登录状态:页面使用;
    public String getLoginState(){
        return state == LOGIN_ERROR ? "失败":"成功";
    }
}
