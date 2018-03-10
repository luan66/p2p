package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 存放session的对象:
 */
public class UserContext {
    private static final String LOGININFO_IN_SESSION = "loginInfo";

    //设置登录对象到session中
    public static void setLoginInfo(LoginInfo loginInfo){
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute(LOGININFO_IN_SESSION,loginInfo);
    }
    //从session中获取登录对象:
    public static LoginInfo getLoginInfo(){
        return (LoginInfo) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(LOGININFO_IN_SESSION);
    }

}
