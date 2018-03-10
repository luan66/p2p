package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 后台登录:
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IIplogService iplogService;

    @ResponseBody
    @RequestMapping("userLogin")
    public JsonResult loginInfoService(String username, String password, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        //登录状态:默认是失败状态;
        int state = Iplog.LOGIN_ERROR;
        try {
            LoginInfo login = loginInfoService.login(username, password);
            //把对象放到session中:
            UserContext.setLoginInfo(login);
            //修改登录状态
            state = Iplog.LOGIN_SUCCESS;
        }catch (RuntimeException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch (Exception e){
            jsonResult = new JsonResult("程序除了一点错误,我们正在敲打程序员!");
        }

        //设置登陆日志:
        iplogService.insert(username,request.getRemoteAddr(),new Date(),state, LoginInfo.USERTYPE_MGRSITE);
        return jsonResult;
    }
}
