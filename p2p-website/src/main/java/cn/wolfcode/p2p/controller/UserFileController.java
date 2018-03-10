package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.UserFile;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.service.IUserFileService;
import cn.wolfcode.p2p.base.service.impl.SystemDictionaryServiceImpl;
import cn.wolfcode.p2p.util.JsonResult;
import cn.wolfcode.p2p.util.PropertiesUtil;
import cn.wolfcode.p2p.util.UploadUtil;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 材料认证
 */
@Controller
public class UserFileController {
    @Autowired
    private IUserFileService userFileService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private PropertiesUtil propertiesUtil;

    @RequestMapping("userFile")
    public String userFile(Model model){
        Long userId = UserContext.getLoginInfo().getId();
        //查询没有分类的:
        List<UserFile> userFiles = userFileService.queryHashType(userId,false);
        if(userFiles.size() > 0){
            //共享分类:
            List<SystemDictionaryServiceImpl> userFileType = systemDictionaryService.getItemListBydirSn("userFileType");
            model.addAttribute("userFiles",userFiles);
            model.addAttribute("fileTypes",userFileType);
            return "userFiles_commit";
        }
        //查询有分类的:
        userFiles = userFileService.queryHashType(userId,true);
        model.addAttribute("userFiles",userFiles);
        return "userFilesPage";
    }

    /**
     * 文件上传
     * @return
     */
    @ResponseBody
    @RequestMapping("uploadFile")
    public JsonResult uploadFile(MultipartFile file){
        JsonResult jsonResult = new JsonResult();
        try{
            String upload = UploadUtil.upload(file, propertiesUtil.getUploadImage());
            //初始化一个资料认证:
            userFileService.apply(upload, UserContext.getLoginInfo());
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }


    /**
     * 修改文件的分类:
     * @return
     */
    @ResponseBody
    @RequestMapping("userFile_selectType")
    public JsonResult userFile_selectType(Long[] id,long[] fileType){
        JsonResult jsonResult = new JsonResult();
        try{
            //修改:
            userFileService.userFile_selectType(id,fileType);
        }catch(DisplayableException e){
            jsonResult = new JsonResult(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            jsonResult = new JsonResult("啊,服务器出错啦,我们正在殴打程序员.");
        }
        return jsonResult;
    }


}
