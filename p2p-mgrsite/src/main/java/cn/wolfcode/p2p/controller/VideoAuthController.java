package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.VedioAuthQueryObject;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证
 */
@Controller
public class VideoAuthController {
    @Autowired
    private IVideoAuthService videoAuthService;

    /**
     * 跳转:
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("vedioAuth")
    public String vedioAuth(@ModelAttribute("qo") VedioAuthQueryObject qo, Model model){
        PageResult pageResult = videoAuthService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "vedioAuth/list";
    }

    /**
     * 自动补全
     * @return
     */
    @ResponseBody
    @RequestMapping("authComplete")
    public List<Map<String,Object>> authComplete(String username){
        return videoAuthService.getAuthListByUsername(username);
    }

    /**
     * 审核:
     * @return
     */
    @ResponseBody
    @RequestMapping("vedioAuth_audit")
    public JsonResult vedioAuthAudit(int state,Long loginInfoValue,String remark){
        JsonResult jsonResult = new JsonResult();
        try{
            videoAuthService.insert(state,loginInfoValue,remark, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
