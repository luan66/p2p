package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发送验证码
 */
@Controller
public class SendVerifyCodeController {

    @Autowired
    private ISendVerifyCodeService sendVerifyCodeServiceService;

    @ResponseBody
    @RequestMapping("sendVerifyCode")
    public JsonResult sendVerifyCode(String phoneNumber){
        JsonResult jsonResult = new JsonResult();
        try{
        //发送验证码:
        VerifyCodeVo verifyCodeVo = sendVerifyCodeServiceService.sendVerifyCode(phoneNumber, UserContext.getLoginInfo().getId(), UserContext.getVerifyCodeVo());
        //把verifyCodeVo放到session中:
        UserContext.setVerifyCodeVo(verifyCodeVo);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

}
