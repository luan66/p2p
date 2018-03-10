package cn.wolfcode.p2p.base.domain;

import java.util.Date;

/**
 * 邮箱发送记录
 */
public class MailVerify extends BaseDomain{

    private String uuid;
    //邮箱
    private String email;
    //内容
    private String text;
    //事件
    private Date sendTime;
    //用户编号
    private Long userId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
