package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.domain.Recharge;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeMapperService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 账户充值:
 */
@Controller
public class RechargeController {
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;
    @Autowired
    private IRechargeMapperService rechargeMapperService;

    /**
     * 显示充值页面
     * @param model
     * @return
     */
    @RequestMapping("recharge")
    public String recharge(Model model){
        //查询公司账户集合:
        List<PlatformBankInfo> banks =  platformBankInfoService.selectAll();
        model.addAttribute("banks",banks);
        return "recharge";
    }

    /**
     * 线下充值
     * @return
     */
    @ResponseBody
    @RequestMapping("recharge_save")
    public JsonResult rechargeSave(Recharge recharge){
        JsonResult jsonResult = new JsonResult();
        try{
            rechargeMapperService.insert(recharge, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
