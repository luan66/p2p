package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.anno.NeedLoginAnnotation;
import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.SytemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典有关
 */
@Controller
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;


    /**
     * 显示数据字典视图:
     */
    @NeedLoginAnnotation
    @RequestMapping("systemDictionary_list")
    public String view(@ModelAttribute("qo") SytemDictionaryQueryObject qo, Model model){
        //高级查询:
        PageResult pageResult = systemDictionaryService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "systemdic/systemDictionary_listpage";
    }

    /**
     * 显示数据字典明细:
     */
    @NeedLoginAnnotation
    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem_list(@ModelAttribute("qo") SytemDictionaryQueryObject qo, Model model){
        //明细的高级查询:
        PageResult pageResultItem = systemDictionaryService.queryItem(qo);
        List<SystemDictionary> systemDictionaryGroups = systemDictionaryService.selectAll();
        model.addAttribute("pageResultItem",pageResultItem);
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups);
        return "systemdic/systemDictionaryItem_listpage";
    }


    /**
     * 数据字典添加或修改:
     */
    @ResponseBody
    @NeedLoginAnnotation
    @RequestMapping("systemDictionary_saveOrUpdate")
    public JsonResult systemDictionary_saveOrUpdate(SystemDictionary systemDictionary){
        JsonResult jsonResult = new JsonResult();
        try{
            systemDictionaryService.saveOrUpdate(systemDictionary);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }

    /**
     * 数据字典明细添加或修改:
     */
    @ResponseBody
    @NeedLoginAnnotation
    @RequestMapping("systemDictionaryItem_saveOrUpdate")
    public JsonResult systemDictionaryItem_saveOrUpdate(SystemDictionaryItem systemDictionaryItem){
        JsonResult jsonResult = new JsonResult();
        try{
            systemDictionaryService.saveOrUpdateItem(systemDictionaryItem);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }
}
