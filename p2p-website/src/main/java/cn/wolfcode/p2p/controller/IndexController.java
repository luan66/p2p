package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.InvestQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.OrderBy;
import cn.wolfcode.p2p.util.OrderByType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 投资主页面
 */
@Controller
public class IndexController {
    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 进入投资主界面
     * @return
     */
    @RequestMapping("index")
    public String invest(Model model){
        //查询条件:
        InvestQueryObject qo = new InvestQueryObject();
        qo.setStates(new int[] {Constants.BIDREQUEST_STATE_BIDDING,Constants.BIDREQUEST_STATE_PAYING_BACK,Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        qo.setOrderBy(OrderBy.ORDERBY.getColumn());
        qo.setOrderByType(OrderByType.ORDER_BY_ASC.getOrderByType());
        qo.setPageSize(5);
        List<BidRequest> bidRequests = bidRequestService.queryForList(qo);
        System.out.println(bidRequests.size());
        model.addAttribute("bidRequests",bidRequests);
        return "main";
    }
}
