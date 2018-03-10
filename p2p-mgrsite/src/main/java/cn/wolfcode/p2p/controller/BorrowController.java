package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.query.InvestQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 借款申请
 */
@Controller
public class BorrowController {
    @Autowired
    private IBidRequestService bidRequestService;

    //发标前审核
    @RequestMapping("bidrequest_publishaudit_list")
    public String bidRequestPublishauditList(InvestQueryObject qo, Model model){
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidrequest/publish_audit";
    }

    //满标一审
    @RequestMapping("bidrequest_audit1_list")
    public String audit1(@ModelAttribute("qo") BidRequestQueryObject qo, Model model){
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidRequest/audit1";
    }

    //满标二审
    @RequestMapping("bidrequest_audit2_list")
    public String audit2(@ModelAttribute("qo") BidRequestQueryObject qo, Model model){
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidRequest/audit2";
    }

    //审核借款
    @ResponseBody
    @RequestMapping("bidrequest_publishaudit")
    public JsonResult bidRequestPublishaudit(Long id,int state,String remark){
        JsonResult jsonResult = new JsonResult();
        try{
            bidRequestService.audit(id,state,remark, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    //满标一审
    @ResponseBody
    @RequestMapping("bidrequest_audit1")
    public JsonResult bidrequestAudit1(Long id,int state,String remark){
        JsonResult jsonResult = new JsonResult();
        try{
            bidRequestService.audit1(id,state,remark, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    //满标二审
    @ResponseBody
    @RequestMapping("bidrequest_audit2")
    public JsonResult bidrequestAudit2(Long id,int state,String remark){
        JsonResult jsonResult = new JsonResult();
        try{
            bidRequestService.audit2(id,state,remark, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

}
