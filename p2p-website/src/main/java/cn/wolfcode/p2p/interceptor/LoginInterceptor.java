package cn.wolfcode.p2p.interceptor;

import cn.wolfcode.p2p.base.anno.NeedLoginAnnotation;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器:登录效验
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 实现效验方法:
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            //1:获取真实类型:
            HandlerMethod handler1 = (HandlerMethod) handler;
            //2:判断上面有没有NeedLoginAnnotation注解:
            NeedLoginAnnotation anno = handler1.getMethodAnnotation(NeedLoginAnnotation.class);
            if(anno==null) return true;
            //3:注解不为null,验证有没有登录:
            if(UserContext.getLoginInfo() == null){
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return true;
    }
}
