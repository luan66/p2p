package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台实名认证
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("realAuth")
    public String realAyth(@ModelAttribute("qo") RealAuthQueryObject qo, Model model){
        PageResult pageResult = realAuthService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "realAuth/list";
    }


    /**
     * 审核实名认证
     * @return
     */
    @ResponseBody
    @RequestMapping("realAuth_audit")
    public JsonResult realAuth_audit(RealAuth realAuth){
        JsonResult jsonResult = new JsonResult();
        try{
            realAuthService.audit(realAuth, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
