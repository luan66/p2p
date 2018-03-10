package cn.wolfcode.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 绑定验证码记录:
 */
@Setter
@Getter
public class VerifyCodeVo{
    private String phoneNumber;     //手机号;
    private int verifyCode;         //验证码;
    private Date sendTime;          //发送时间;

}
