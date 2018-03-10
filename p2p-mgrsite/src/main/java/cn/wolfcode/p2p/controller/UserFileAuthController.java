package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.UserFile;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.UserFileQueryObject;
import cn.wolfcode.p2p.base.service.IUserFileService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台认证资料
 */
@Controller
public class UserFileAuthController {
    @Autowired
    private IUserFileService userFileService;

    @RequestMapping("userFileAuth")
    public String userFileAuth(@ModelAttribute("qo")UserFileQueryObject qo,Model model){
        //查询所有的认证资料:
        PageResult pageResult = userFileService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "userFileAuth/list";
    }


    @ResponseBody
    @RequestMapping("userFile_audit")
    public JsonResult audit(UserFile userFile){
        JsonResult jsonResult = new JsonResult();
        try{
            userFileService.audit(userFile, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
