package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.RealAuthMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.BitStatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 实名认证
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    private RealAuthMapper realAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    //保存认证信息:
    public void save(RealAuth realAuth, LoginInfo loginInfo) {
        //判断用户是否已经有实名申请
        UserInfo userInfo = userInfoService.getUserInfoById(loginInfo.getId());
        if(userInfo.getRealAuthId() != null){
            throw new DisplayableException("实名认证已提交,请耐心等待!");
        }
        //判断是否已经通过实名认证
        if(userInfo.hasRealAuth()){
            throw new DisplayableException("实名已认证!");
        }
        //创建保存一个实名认证对象
        RealAuth realAuth1 = new RealAuth();
        realAuth1.setState(RealAuth.REALAUTH_NOT);
        realAuth1.setRealName(realAuth.getRealName());
        realAuth1.setSex(realAuth.getSex());
        realAuth1.setIdNumber(realAuth.getIdNumber());
        realAuth1.setBornDate(realAuth.getBornDate());
        realAuth1.setAddress(realAuth.getAddress());
        realAuth1.setImage1(realAuth.getImage1());
        realAuth1.setImage2(realAuth.getImage2());
        realAuth1.setApplierId(loginInfo);
        realAuth1.setApplyTime(new Date());
        realAuthMapper.insert(realAuth1);
        //设置userInfo 中实名申请对象的Id
        userInfo.setRealAuthId(realAuth1.getId());
        userInfoService.update(userInfo);

    }

    //查询一条数据
    public RealAuth selectByPrimaryKey(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    //后台高级查询
    public PageResult query(QueryObject qo) {
        int totalCount = realAuthMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<RealAuth> list = realAuthMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //后台审核实名认证
    public void audit(RealAuth realAuth,LoginInfo loginInfo) {
        //判断,实名状态
        RealAuth realAuth1 = realAuthMapper.selectByPrimaryKey(realAuth.getId());
        if(realAuth1.getState() != RealAuth.REALAUTH_NOT){
            throw new DisplayableException("实名认证状态不处于申请中");
        }
        //1:判断是否已经审核过:
        Integer state = realAuth.getState();
        if(state == null || (state != RealAuth.MAN_SEX && state != RealAuth.REALAUTH_ERROR)){
            throw new DisplayableException("该状态异常!");
        }
        //2:修改审核对象的相关信息:
        //申请人实名认证对象
        realAuth1.setState(realAuth.getState());
        realAuth1.setRemark(realAuth.getRemark());
        realAuth1.setAuditorId(loginInfo);
        realAuth1.setAuditTime(new Date());
        realAuthMapper.updateByPrimaryKey(realAuth1);

        //获取申请人对象
        LoginInfo applierId = realAuth1.getApplierId();
        //申请人基本信息对象
        UserInfo userInfo = userInfoService.getUserInfoById(applierId.getId());
        //3:审核成功:修改userinfo-->realName ,idNumber,bitState
        if(state == RealAuth.REALAUTH_SUCCESS){
            userInfo.setBitState(BitStatesUtils.addState(userInfo.getBitState(),BitStatesUtils.OP_REAL_AUTH));
            userInfo.setRealName(realAuth1.getRealName());
            userInfo.setIdNumber(realAuth1.getIdNumber());
        }
        //4:审核失败:清除userinfo对象中的realAuthId的值;
        if(state == RealAuth.REALAUTH_ERROR){
            userInfo.setRealAuthId(null);
        }
        update(userInfo);

    }

    public void update(UserInfo userInfo){
        int count = userInfoMapper.updateByPrimaryKey(userInfo);
        if(count == 0){
            throw new DisplayableException("用户信息修改失败,请稍后重试.[userInfo乐观锁异常]");
        }
    }
}
