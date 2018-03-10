package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.PropertiesUtil;
import cn.wolfcode.p2p.util.UploadUtil;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 身份认证
 */
@Controller
public class RealAuthPageController {
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private PropertiesUtil propertiesUtil;



    //跳转视图:
    @RequestMapping("realAuth")
    public String view(Model model){
        //共享loginInfo
        //已认证:调到另一个页面:
        Long id = UserContext.getLoginInfo().getId();
        UserInfo userInfo = userInfoService.getUserInfoById(id);
        model.addAttribute("auditing",userInfo.getRealAuthId());
        if(userInfo.hasRealAuth()){
            model.addAttribute("auditing",null);
            RealAuth realAuth = realAuthService.selectByPrimaryKey(userInfo.getRealAuthId());
            model.addAttribute("realAuth",realAuth);
            return "/realAuth_result";
        }
        //申请中:跳到另一个页面:
        if(userInfo.getRealAuthId() != null){
            return "/realAuth_result";
        }
        model.addAttribute("logininfo", UserContext.getLoginInfo());
        return "realAuthPage";
    }

    //保存认证信息:
    @ResponseBody
    @RequestMapping("realAuth_save")
    public JsonResult realAuth(RealAuth realAuth){
        JsonResult jsonResult = new JsonResult();
        try{
            realAuthService.save(realAuth, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    //保存认证信息:
    @ResponseBody
    @RequestMapping("uploadImage")
    public String uploadImage(MultipartFile file){
        String uploadImage = propertiesUtil.getUploadImage();
        return UploadUtil.upload(file,uploadImage);
    }
}
