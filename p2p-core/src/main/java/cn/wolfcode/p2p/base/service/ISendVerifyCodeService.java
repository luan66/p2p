package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.vo.VerifyCodeVo;

/**
 * 发送验证码:
 */
public interface ISendVerifyCodeService {
    /**
     * 发送验证码:
     * @param phoneNumber
     */
    public VerifyCodeVo sendVerifyCode(String phoneNumber, Long userId, VerifyCodeVo verifyCode);
}
