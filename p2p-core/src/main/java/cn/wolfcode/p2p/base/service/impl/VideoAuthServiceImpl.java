package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.mapper.VideoAuthMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.util.BitStatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频认证
 */
@Service
public class VideoAuthServiceImpl implements IVideoAuthService {
    @Autowired
    private VideoAuthMapper videoAuthMapper;
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IUserInfoService userInfoService;

    //高级查询
    public PageResult query(QueryObject qo) {
        int totalCount = videoAuthMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<VideoAuth> list = videoAuthMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //自动补全
    public List<Map<String, Object>> getAuthListByUsername(String username) {
        return loginInfoMapper.getAuthListByUsername(username);
    }

    @Override
    public void insert(int state, Long loginInfoValue, String remark,LoginInfo auditor) {
        //1：判断是否有该用户:
        UserInfo userInfo = userInfoService.getUserInfoById(loginInfoValue);
        if(userInfo==null){
           throw new DisplayableException("用户不存在!");
        }
        //2:判断审核用户是否已经审核过了
        if(userInfo.hasVedioAuth()){
           throw new DisplayableException("已通过视频认证!");
        }
        //3:修改审核信息:
        VideoAuth videoAuth = new VideoAuth();
        //申请人:
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(loginInfoValue);
        videoAuth.setApplierId(loginInfo);
        videoAuth.setApplyTime(new Date());
        videoAuth.setAuditorId(auditor);
        videoAuth.setAuditTime(new Date());
        videoAuth.setRemark(remark);
        videoAuth.setState(state);
        //修改:
        videoAuthMapper.insert(videoAuth);

        //3:审核成功:userinfo-->状态
        if(state == VideoAuth.REALAUTH_SUCCESS){
            long videoState = BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_VEDIO_AUTH);
            userInfo.setBitState(videoState);
            userInfoService.update(userInfo);
        }
    }
}
