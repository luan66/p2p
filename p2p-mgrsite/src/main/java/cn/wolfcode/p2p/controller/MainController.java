package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.anno.NeedLoginAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台主界面
 */
@Controller
public class MainController {

    @NeedLoginAnnotation
    @RequestMapping("main")
    public String main(){
        return "main";
    }
}
