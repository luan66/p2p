package cn.wolfcode.p2p.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置属性
 */
@Component
@Setter
@Getter
public class PropertiesUtil {

    //验证码相关
    //1:验证码发送间隔时间(多少秒后重新发送/秒);
    @Value("${verifyCode.minVerifyCodeTime}")
    public String minVerifyCodeTime;
    //2:验证码失效时间(多少秒后验证码失效/秒);
    @Value("${verifyCode.maxVerifyCodeTime}")
    public String maxVerifyCodeTime;
    //3:中国网建的账号:
    @Value("${verifyCode.Uid}")
    public String Uid;
    //4:中国网通发送短信的密钥
    @Value("${verifyCode.Key}")
    public String Key;
    //5:短信内容
    @Value("${verifyCode.context}")
    public String context;

    //发送邮箱:
    //1:发送邮件的账号
    @Value("${spring.mail.username}")
    public String mailUsername;

    //文件上传:
    @Value("${uploadImage}")
    public String uploadImage;

}
