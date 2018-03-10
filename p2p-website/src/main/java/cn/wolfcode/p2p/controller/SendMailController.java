package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISendMailService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 激活邮箱
 */
@Controller
public class SendMailController {
    @Autowired
    private ISendMailService sendMailService;


    @ResponseBody
    @RequestMapping("sendEmail")
    public JsonResult sendEmail(String email, Model model){
        JsonResult jsonResult = new JsonResult();
        try{
            //发送验证码:
            sendMailService.sendMail(email, UserContext.getLoginInfo().getId());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
