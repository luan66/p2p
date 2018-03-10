package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.query.InvestQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投资相关:
 */
@Controller
public class InvestController {

    @Autowired
    private IBidRequestService bidRequestService;
    /**
     * 跳转到个人投资页面
     * @return
     */
    @RequestMapping("invest")
    public String invest(){
        return "invest";
    }

    /**
     * 跳转到数据页面
     * @return
     */
    @RequestMapping("invest_list")
    public String investList(InvestQueryObject qo, Model model){
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "invest_list";
    }

    /**
     * 投标:
     * @return
     */
    @ResponseBody
    @RequestMapping("borrow_bid")
    public JsonResult borrowBid(Long bidRequestId, BigDecimal amount){
        JsonResult jsonResult = new JsonResult();
        try{
            bidRequestService.bid(bidRequestId,amount, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
