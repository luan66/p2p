package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlatformBankInfoController {
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("companyBank_list")
    public String companyBankList(@ModelAttribute("qo") PlatformBankInfoQueryObject qo, Model model){
        PageResult pageResult = platformBankInfoService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "platformbankinfo/list";
    }

    /**
     * 添加或者修改:
     * @return
     */
    @ResponseBody
    @RequestMapping("companyBank_update")
    public JsonResult saveOrUpdate(PlatformBankInfo platformBankInfo){
        JsonResult jsonResult = new JsonResult();
        try{
            platformBankInfoService.saveOrUpdate(platformBankInfo);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
