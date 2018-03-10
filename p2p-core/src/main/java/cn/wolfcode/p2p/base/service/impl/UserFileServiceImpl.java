package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.mapper.UserFileMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.UserFileQueryObject;
import cn.wolfcode.p2p.base.service.IUserFileService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.BitStatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 材料认证
 */
@Service
public class UserFileServiceImpl implements IUserFileService {
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;
    @Autowired
    private IUserInfoService userInfoService;

    //查询所有
    public List<UserFile> selectAll() {
        return userFileMapper.selectAll();
    }

    //初始化一个文件认证
    public void apply(String upload, LoginInfo loginInfo) {
        UserFile userFile = new UserFile();
        userFile.setImage(upload);
        userFile.setApplierId(loginInfo);
        userFile.setApplyTime(new Date());
        //保存:
        userFileMapper.insert(userFile);
    }

    @Override
    public List<UserFile> queryHashType(Long userId,boolean isType) {
        return userFileMapper.queryHashType(userId,isType);
    }

    //修该分类的状态
    public void userFile_selectType(Long[] id, long[] fileType) {
        for(int i = 0;i < id.length; i++){
            Long fileId = id[i];
            long fileTypeId = fileType[i];
            UserFile userFile = userFileMapper.selectByPrimaryKey(fileId);
            SystemDictionaryItem item = new SystemDictionaryItem();
            item.setId(fileTypeId);
            userFile.setFileTypeId(item);
            update(userFile);
        }
    }

    //高级查询
    public PageResult query(UserFileQueryObject qo) {
        int totalCount = userFileMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<UserFile> list = userFileMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //后天审核资料
    public void audit(UserFile userFile, LoginInfo loginInfo) {
        //判断资料审核状态
        UserFile userFile1 = userFileMapper.selectByPrimaryKey(userFile.getId());
        if(userFile1.getState() != UserFile.REALAUTH_NOT){
            throw new DisplayableException("实名认证状态不处于申请中");
        }
        //1:判断是否已经审核过:
        Integer state = userFile.getState();
        if(state == null || (state != UserFile.REALAUTH_SUCCESS && state != UserFile.REALAUTH_ERROR)){
            throw new DisplayableException("该状态异常!");
        }
        //2:修改审核对象的相关信息:
        //申请人实名认证对象
        userFile1.setState(userFile.getState());
        userFile1.setRemark(userFile.getRemark());
        userFile1.setScore(userFile.getScore());
        userFile1.setAuditorId(loginInfo);
        userFile1.setAuditTime(new Date());
        userFileMapper.updateByPrimaryKey(userFile1);

        //获取申请人对象
        LoginInfo applierId = userFile1.getApplierId();
        //申请人基本信息对象
        UserInfo userInfo = userInfoService.getUserInfoById(applierId.getId());
        //3:审核成功:修改userinfo-->realName ,idNumber,bitState
        if(state == UserFile.REALAUTH_SUCCESS){
            userInfo.setBitState(BitStatesUtils.addState(userInfo.getBitState(),BitStatesUtils.OP_BASIC_INFO));
            int score = userInfo.getScore() + userFile.getScore();
            userInfo.setScore(score);
            userInfoService.update(userInfo);
        }
        //4:审核失败:清除userinfo对象中的realAuthId的值;
        /*if(state == RealAuth.REALAUTH_ERROR){
            userInfo.setRealAuthId(null);
        }*/
    }

    //查询
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.queryForList(qo);
    }

    public void update(UserFile userFile){
        int update = userFileMapper.updateByPrimaryKey(userFile);
        if(update==0){
            throw new DisplayableException("乐观锁异常!");
        }
    }
}
