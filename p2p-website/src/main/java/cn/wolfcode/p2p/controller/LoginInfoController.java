package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class LoginInfoController {
    @Autowired
    private ILoginInfoService loginInfoService;

    /**
     * 注册时,效验用户名是否还存在;
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("checkUsername")
    public boolean checkUsername(String username) throws SQLException {
        return !loginInfoService.checkUsername(username);
    }

    /**
     * 注册用户:
     */
    @ResponseBody
    @RequestMapping("registerUser")
    public JsonResult registerUser(String username, String password, String confirmPwd){
        try {
            loginInfoService.registerUser(username,password,confirmPwd,LoginInfo.USERTYPE_WEBSITE);
        }catch (RuntimeException e){
            return new JsonResult(e.getMessage());
        }catch (Exception e){
            return new JsonResult("程序除了一点错误,我们正在敲打程序员!");
        }
        return new JsonResult();
    }
}
