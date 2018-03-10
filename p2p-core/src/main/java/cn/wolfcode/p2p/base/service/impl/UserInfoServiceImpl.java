package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.MailVerify;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.MailVerifyMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;
import cn.wolfcode.p2p.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private MailVerifyMapper mailVerifyMapper;
    @Autowired
    private PropertiesUtil propertiesUtil;

    //初始化基本信息:
    public void iniUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    //通过id查询对象:
    public UserInfo getUserInfoById(Long userId) {
        return userInfoMapper.getUserInfoById(userId);
    }

    //绑定手机号:
    public void bindPhoneNumber(String phoneNumber, String verifyCode, VerifyCodeVo verifyCodeVo,Long userId) {
        //1,判断绑定的手机号和发送验证码时候的手机号必须一致
        if(!StringUtils.hasLength(phoneNumber)){
            throw new DisplayableException("手机号格式不正确!");
        }
        String phoneNumberSession = verifyCodeVo.getPhoneNumber();
        if(!phoneNumber.equals(phoneNumberSession)){
            throw new DisplayableException("手机号有变动!");
        }
        //2:校验验证码:
        String verifyCodeSession = String.valueOf(verifyCodeVo.getVerifyCode());
        if(!verifyCodeSession.equals(verifyCode)){
            throw new DisplayableException("验证码不正确!");
        }
        //3:验证码是否已过期:
        Date sendTimeSession = verifyCodeVo.getSendTime();
        //有效时间:
        System.out.println(propertiesUtil.getMaxVerifyCodeTime());
        long maxVerifyCodeTime = Long.parseLong(propertiesUtil.getMaxVerifyCodeTime());
        if(DateUtil.getSecondsBetween(new Date(),sendTimeSession) > maxVerifyCodeTime){
            throw new DisplayableException("验证码已过期!");
        };
        //4,判断用户是否已经绑定手机号码
        UserInfo userInfo = getUserInfoById(userId);
        if(userInfo.hasBindPhoneNumber()){
            throw new DisplayableException("用户已经绑定手机号!");
        };
        //5,修改userInfo :bitState  , phoneNumber
        userInfo.addState(BitStatesUtils.OP_BIND_PHONE);
        userInfo.setPhoneNumber(phoneNumber);
        update(userInfo);
    }

    //绑定邮箱
    public void bindEmail(String uuid) {
        //1,拿到uuid ,EmailVerifyVo  拿到用户信息
        if(!StringUtils.hasLength(uuid)){
             throw new DisplayableException("无效的邮箱!");
        }
        MailVerify mailVerify = mailVerifyMapper.getMailVerifyByUuid(uuid);
        if(mailVerify == null){
             throw new DisplayableException("链接无效,请重新发送!");
        }
        //2,判断是否已经完成邮箱绑定
        UserInfo userInfo = userInfoMapper.getUserInfoById(mailVerify.getUserId());
        if(userInfo.hasBindEmail()){
             throw new DisplayableException("邮箱已经注册!");
        }
        //3,判断邮箱过期
        if(DateUtil.getSecondsBetween(new Date(),mailVerify.getSendTime()) >= Constants.MAX_MEILCODETIME){
             throw new DisplayableException("邮箱已经过期了!");
        }
        //4,绑定邮箱修改userInfo
        userInfo.addState(BitStatesUtils.OP_BIND_EMAIL);
        userInfo.setEmail(mailVerify.getEmail());
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    //保存基本资料
    public void save(UserInfo userInfo) {
        UserInfo oldUserInfo = userInfoMapper.getUserInfoById(userInfo.getId());
        oldUserInfo.getEducationBackground();
        oldUserInfo.getHouseCondition();
        oldUserInfo.getIncomeGrade();
        oldUserInfo.getKidCount();
        oldUserInfo.getMarriage();

        oldUserInfo.setEducationBackground(userInfo.getEducationBackground());
        oldUserInfo.setIncomeGrade(userInfo.getIncomeGrade());
        oldUserInfo.setMarriage(userInfo.getMarriage());
        oldUserInfo.setHouseCondition(userInfo.getHouseCondition());
        oldUserInfo.setKidCount(userInfo.getKidCount());

        //设置位状态:
        if(!oldUserInfo.hasBasicInfo()){
            oldUserInfo.addState(BitStatesUtils.OP_BASIC_INFO);
        }

        //修改:
        update(oldUserInfo);

    }

    public void update(UserInfo userInfo){
        int count = userInfoMapper.updateByPrimaryKey(userInfo);
        if(count == 0){
            throw new DisplayableException("用户信息修改失败,请稍后重试.[userInfo乐观锁异常]");
        }
    }
}
