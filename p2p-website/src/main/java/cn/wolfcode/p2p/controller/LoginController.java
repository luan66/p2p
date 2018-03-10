package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
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
 * 登录:
 */
@Controller
public class LoginController {
    @Autowired
    ILoginInfoService loginInfoService;
    @Autowired
    IIplogService iplogService;

    //用户登录:
    @ResponseBody
    @RequestMapping("userLogin")
    public JsonResult userLogin(String username, String password, HttpServletRequest request){
        //返回的json对象;
        JsonResult jsonResult = new JsonResult();
        int state = Iplog.LOGIN_ERROR;
        try {
            LoginInfo loginInfo = loginInfoService.login(username,password);
            //把对象放到session中:
            UserContext.setLoginInfo(loginInfo);
            state = Iplog.LOGIN_SUCCESS;
        }catch (DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch (Exception e){
            jsonResult = new JsonResult("亲,系统出现了异常,我们正在敲打程序员!");
        }

        /**
         * 因为失败的登录失败的状态在service层不好判断位置,所以把添加登录记录加载这里:
         */
        iplogService.insert(username,request.getRemoteAddr(),new Date(),state,Iplog.USERTYPE_WEBSITE);
        return jsonResult;
    }
}
