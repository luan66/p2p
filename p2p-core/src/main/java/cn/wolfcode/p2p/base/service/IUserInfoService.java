package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;

public interface IUserInfoService {
    /**
     * 初始化基本信息:
     * @param userInfo
     */
    void iniUserInfo(UserInfo userInfo);

    /**
     * 通过id查询对象:
     * @param userId
     */
    UserInfo getUserInfoById(Long userId);

    /**
     * 绑定手机号:
     * @param phoneNumber
     * @param verifyCode
     */
    public void bindPhoneNumber(String phoneNumber, String verifyCode, VerifyCodeVo verifyCodeVo, Long userId);

    /**
     * 绑定邮箱
     * @param uuid
     */
    void bindEmail(String uuid);

    /**
     * 保存基本资料
     * @param userInfo
     */
    void save(UserInfo userInfo);

    /**
     * 修改基本信息:
     * @param userInfo
     */
    void update(UserInfo userInfo);
}
