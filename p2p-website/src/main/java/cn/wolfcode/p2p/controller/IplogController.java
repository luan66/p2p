package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.IplogQueryObject;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录记录：
 */
@Controller
public class IplogController {
    @Autowired
    private IIplogService iplogService;

    @RequestMapping("ipLog")
    public String ipLog(@ModelAttribute("qo") IplogQueryObject qo, Model model){
        qo.setUsername(UserContext.getLoginInfo().getUsername());
        PageResult result = iplogService.query(qo);
        model.addAttribute("result",result);
        return "iplog_list";
    }
}
