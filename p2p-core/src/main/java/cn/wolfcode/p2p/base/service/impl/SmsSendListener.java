package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.event.SmsSendEvemt;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;
import cn.wolfcode.p2p.util.HttpUtil;
import cn.wolfcode.p2p.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信监听器
 */
@Service
public class SmsSendListener implements ApplicationListener<SmsSendEvemt> {
    @Autowired
    private PropertiesUtil propertiesUtil;

    @Override
    public void onApplicationEvent(SmsSendEvemt smsSendEvemt) {
        //获取发送验证码参数:
        VerifyCodeVo vo = (VerifyCodeVo) smsSendEvemt.getSource();
        //01:发送短信的内容:
        String context = propertiesUtil.getContext() + vo.getVerifyCode();
        //02:手机号:
        String phoneNumber = vo.getPhoneNumber();
        System.out.println(" 邦定手机号=================");
        System.out.println(context);
        System.out.println(phoneNumber);
        //doSend(context, phoneNumber);
    }

    private void doSend(String context, String phoneNumber) {
        Map<String, String> parmas = new HashMap<String, String>();
        //中国网通的账号名
        parmas.put("Uid", propertiesUtil.getUid());
        //中国网通发送短信的密钥
        parmas.put("Key", propertiesUtil.getKey());
        //要发送的手机号
        parmas.put("smsMob", phoneNumber);
        //要发送的内容
        parmas.put("smsText", context);
        try {
            //调用该方法,发送短信:
            //返回是否成功的状态码:
            String state = HttpUtil.sendHttpRequest("http://utf8.api.smschinese.cn", parmas);
            //手机号格式不正确抛异常:
            if (state.equals("-4")) {
                throw new DisplayableException("手机号格式不正确!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
