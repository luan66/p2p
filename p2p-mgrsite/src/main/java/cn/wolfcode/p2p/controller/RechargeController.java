package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeMapperService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 线下充值
 */
@Controller
public class RechargeController {
    @Autowired
    private IRechargeMapperService rechargeMapperService;
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo") RechargeQueryObject qo, Model model){
        PageResult pageResult = rechargeMapperService.query(qo);
        model.addAttribute("pageResult",pageResult);
        //开户行:
        List<PlatformBankInfo> banks = platformBankInfoService.selectAll();
        model.addAttribute("banks",banks);
        return "rechargeoffline/list";
    }

    /**
     * 审核线上充值
     * @return
     */
    @ResponseBody
    @RequestMapping("rechargeOffline_audit")
    public JsonResult rechargeOfflineAudit(Long id,int state,String remark){
        JsonResult jsonResult = new JsonResult();
        try{
            rechargeMapperService.audit(id,state,remark, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
