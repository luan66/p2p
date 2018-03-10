package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.anno.NeedLoginAnnotation;
import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆后进入个人中心:
 */
@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ISystemDictionaryService systemDictionary;

    @NeedLoginAnnotation
    @RequestMapping("personal")
    public String prosonal(Model model){
        //共享个人账户信息
        LoginInfo loginInfo = UserContext.getLoginInfo();
        Account account = accountService.selectByPrimaryKey(loginInfo.getId());
        model.addAttribute("account",account);
        UserInfo userInfo = userInfoService.getUserInfoById(loginInfo.getId());
        model.addAttribute("userInfo",userInfo);
        return "personal";
    }

    /**
     * 绑定手机号;
     */
    @ResponseBody
    @RequestMapping("bindPhoneNumber")
    public JsonResult bindPhoneNumber(String phoneNumber,String verifyCode){
        JsonResult jsonResult = new JsonResult();
        try{
            //绑定手机号:
            userInfoService.bindPhoneNumber(phoneNumber, verifyCode, UserContext.getVerifyCodeVo(),UserContext.getLoginInfo().getId());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    /**
     * 绑定邮箱
     */
    @RequestMapping("bindEmail")
    public String bindEmail(String uuid,Model model){
        try{
            userInfoService.bindEmail(uuid);
            model.addAttribute("success",true);
        }catch(DisplayableException e){
            model.addAttribute("msg",e.getMessage());
        }catch(Exception e){
            model.addAttribute("msg","系统异常了,请稍后重试!");
            e.printStackTrace();
        }
        return "checkmail_result";
    }

    /**
     * 填写基本资料
     */
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        //Long id = UserContext.getLoginInfo().getId();
        model.addAttribute("logininfo",UserContext.getLoginInfo());
        model.addAttribute("userinfo",userInfoService.getUserInfoById(UserContext.getLoginInfo().getId()));

        //个人学历:
        model.addAttribute("educationBackgrounds",systemDictionary.getItemListBydirSn("educationBackground"));
        //月收入:
        model.addAttribute("incomeGrades",systemDictionary.getItemListBydirSn("incomeGrade"));
        //婚姻情况:
        model.addAttribute("marriages",systemDictionary.getItemListBydirSn("marriage"));
        //子女情况:
        model.addAttribute("kidCounts",systemDictionary.getItemListBydirSn("kidCount"));
        //住房条件:
        model.addAttribute("houseConditions",systemDictionary.getItemListBydirSn("houseCondition"));

        return "userInfo";
    }

    /**
     * 保存基本资料
     */
    @ResponseBody
    @RequestMapping("basicInfo_save")
    public JsonResult basicInfo_save(UserInfo userInfo){
       JsonResult jsonResult = new JsonResult();
       try{
           userInfo.setId(UserContext.getLoginInfo().getId());
           userInfoService.save(userInfo);
       }catch(DisplayableException e){
           jsonResult = new JsonResult(e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
       }
       return jsonResult;
    }
}
