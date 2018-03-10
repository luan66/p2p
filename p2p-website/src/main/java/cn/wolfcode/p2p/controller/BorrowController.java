package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.UserFileQueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserFileService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.PaymentScheuleQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;
import cn.wolfcode.p2p.util.Constants;
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
 * 借款相关:
 */
@Controller
public class BorrowController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;

    //显示页面:
    @RequestMapping("borrow")
    public String borrow(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        //没有登录跳转:
        if(loginInfo == null){
            return "redirect:borrow_page.html";
        }
        //已登录跳转:
        Long id = UserContext.getLoginInfo().getId();
        model.addAttribute("account",accountService.selectByPrimaryKey(id));
        model.addAttribute("userinfo",userInfoService.getUserInfoById(id));
        model.addAttribute("creditBorrowScore", Constants.CORE_VERIFY);
        return "borrow_page";
    }

    //填写借款页面:
    @RequestMapping("borrowInfo")
    public String borrowInfo(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.getUserInfoById(loginInfo.getId());
        if(userInfo.hasBidRequestInProcess()){
            //正在借款,调到另一个页面:
            return "borrow_apply_result";
        }
        //最小借款金额
        model.addAttribute("minBidRequestAmount",Constants.SMALLEST_BIDREQUEST_AMOUNT);
        //当前用户的账户:
        Account account = accountService.selectByPrimaryKey(loginInfo.getId());
        model.addAttribute("account",account);
        //最小投标金额:
        model.addAttribute("minBidAmount",Constants.SMALLEST_BID_AMOUNT);
        return "borrow_apply";
    }

    //提交借款信息:
    @ResponseBody
    @RequestMapping("borrow_apply")
    public JsonResult borrowApply(BidRequest bidRequest,Model model){
        JsonResult jsonResult = new JsonResult();
        try{
            //设置借款人:
            bidRequest.setCreateUserId(UserContext.getLoginInfo());
            bidRequestService.borrowApply(bidRequest);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    //借款详情页面:
    @RequestMapping("borrow_info")
    public String borrowInfo(Model model,Long id){
        //01:查询借款明细
        BidRequest bidRequest = bidRequestService.selectByPrimaryKey(id);
        model.addAttribute("bidRequest",bidRequest);
        //02:当前借款人的基本信息:
        LoginInfo bidloginInfo = bidRequest.getCreateUserId();
        UserInfo userInfo = userInfoService.getUserInfoById(bidloginInfo.getId());
        model.addAttribute("userInfo",userInfo);
        //04:实名认证信息:
        RealAuth realAuth = realAuthService.selectByPrimaryKey(userInfo.getRealAuthId());
        model.addAttribute("realAuth",realAuth);
        //05:材料信息:
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setState(UserFile.REALAUTH_SUCCESS);
        qo.setUserId(bidloginInfo.getId());
        List<UserFile> userFiles = userFileService.queryForList(qo);
        model.addAttribute("userFiles",userFiles);

        //判断是否是借款人本人进去的界面:
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if(loginInfo != null) {
            if (loginInfo.getId().longValue() == bidRequest.getCreateUserId().getId().longValue()) {
                model.addAttribute("self", true);
            } else {
                //03:当前登录投资人的账户:
                Account account = accountService.selectByPrimaryKey(loginInfo.getId());
                model.addAttribute("account", account);
            }
        }
        return "borrow_info";
    }

    //还款页面:
    @RequestMapping("borrowBidReturn_list")
    public String borrowBidReturnList(@ModelAttribute("qo") PaymentScheuleQueryObject qo, Model model){
        Long bidUserId = UserContext.getLoginInfo().getId();
        qo.setBidRequestId(bidUserId);
        PageResult pageResult = paymentScheduleService.query(qo);
        model.addAttribute("pageResult",pageResult);
        Account account = accountService.selectByPrimaryKey(bidUserId);
        model.addAttribute("account",account);
        return "returnmoney_list";
    }

    //还款:
    @ResponseBody
    @RequestMapping("returnMoney")
    public JsonResult returnMoney(Long id){
        JsonResult jsonResult = new JsonResult();
        try{
            bidRequestService.returnMoney(id,UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

}
