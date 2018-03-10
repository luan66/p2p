package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.MailVerify;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.MailVerifyMapper;
import cn.wolfcode.p2p.base.service.ISendMailService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class SendMailServiceImpl implements ISendMailService {

    @Autowired
    private MailVerifyMapper mailVerifyMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PropertiesUtil propertiesUtil;

    @Override
    public void sendMail(String email,Long userId) {

        //1,判断邮箱
        if(!StringUtils.hasLength(email)){
            throw new DisplayableException("无效的邮箱");
        }
        //2,判断是否已经完成邮箱绑定
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo.hasBindEmail()){
            throw new DisplayableException("已经绑定邮箱了");
        }
        //3,执行发送 <a href="http://localhost:80/bindEmail?">
        String uuid = UUID.randomUUID().toString();
        String text = "<a href=http://localhost/bindEmail?uuid="+uuid+">点击这里</a>";
        doSend(text,email);
        //4,保存发送记录到数据库(EmailVerifyVo (userId,uuid,email,sendTime ))
        MailVerify mv = new MailVerify();
        mv.setEmail(email);
        mv.setSendTime(new Date());
        mv.setText(text);
        mv.setUserId(userId);
        mv.setUuid(uuid);
        mailVerifyMapper.insert(mv);
        System.out.println("发送邮件:"+text);
    }

   public void doSend(String text,String email){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //发送邮件的那个邮箱:
            helper.setFrom(propertiesUtil.getMailUsername());
            //接收邮件的那个邮箱:
            helper.setTo(email);
            //主题:
            helper.setSubject("主题：邮箱激活");
            //邮件内容
            helper.setText(text,true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
