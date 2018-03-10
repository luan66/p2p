package cn.wolfcode.p2p.base.service;

public interface ISendMailService {
    /**
     * 发送邮件
     * @param email
     */
    public void sendMail(String email, Long userId);
}
