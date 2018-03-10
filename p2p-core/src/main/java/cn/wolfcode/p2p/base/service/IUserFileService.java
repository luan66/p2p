package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserFile;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.UserFileQueryObject;

import java.util.List;

/**
 * 材料认证
 */
public interface IUserFileService {
    /**
     * 查询所有:
     * @return
     */
    List<UserFile> selectAll();

    /**
     * 初始化一个文件认证
     * @param upload
     * @param loginInfo
     */
    void apply(String upload, LoginInfo loginInfo);

    /**
     * 查询有分类的
     * @param userId
     * @return
     */
    List<UserFile> queryHashType(Long userId,boolean isType);


    /**
     * 修改分类的状态:
     * @param id
     * @param fileType
     */
    void userFile_selectType(Long[] id, long[] fileType);

    /**
     * 高级查询
     * @param qo
     * @return
     */
    PageResult query(UserFileQueryObject qo);

    /**
     * 后台审核资料:
     * @param userFile
     * @param loginInfo
     */
    void audit(UserFile userFile, LoginInfo loginInfo);

    /**
     * 查询单个申请人的资料集合
     * @param qo
     * @return
     */
    List<UserFile> queryForList(UserFileQueryObject qo);
}
