package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.event.SmsSendEvemt;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;
import cn.wolfcode.p2p.util.DateUtil;
import cn.wolfcode.p2p.util.PropertiesUtil;
import cn.wolfcode.p2p.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 发送验证码
 */
@Service
public class SendVerifyCodeServiceImpl implements ISendVerifyCodeService,ApplicationEventPublisherAware {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private PropertiesUtil propertiesUtil;
    private ApplicationEventPublisher applicationEventPublisher;

    //发送验证码:
    public VerifyCodeVo sendVerifyCode(String phoneNumber, Long userId, VerifyCodeVo verifyCode) {
        //判断:
        //1:校验手机号:
        if (!StringUtils.hasLength(phoneNumber) || phoneNumber.trim().length() != 11) {
            throw new DisplayableException("手机号格式不正确");
        }
        //2:判断是否已经绑定手机号;
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo.hasBindPhoneNumber()){
            throw new DisplayableException("已经绑定手机号");
        }
        //3:判断两次发送验证码的间隔时间(是否太频繁) :获取上一次的发送记录
        if(verifyCode != null) {
            Date sendTime = verifyCode.getSendTime();
            long secondsBetween = DateUtil.getSecondsBetween(new Date(), sendTime);
            //间隔时间
            int minVerifyCodeTime = "".indexOf(propertiesUtil.getMinVerifyCodeTime());
            if (secondsBetween <= minVerifyCodeTime) {
                throw new DisplayableException("您发送太频繁,稍后再试!");
            }
        }
        //执行发送验证码:
        //1:生成验证码:
        int randomNum = (int) ((Math.random() * 9 + 1) * 100000);

        //2:发送验证码:原始的方式----------------------------------:
        //发送短信的内容:
        //String context = propertiesUtil.getContext() + randomNum;
        //doSend(context,phoneNumber);
        //-------------------------------------------------------:
        //3:保存记录:往session中保存发送验证码的记录 : 发送人;手机号;验证码;发送时间;ip
        VerifyCodeVo vo = new VerifyCodeVo();
        vo.setPhoneNumber(phoneNumber);
        vo.setVerifyCode(randomNum);
        vo.setSendTime(new Date());

        //02:发送验证码:使用抛事件的方式----------------------------------:
        //参数一:自定的事件:
        //参数二:发送短信要用的信息对象:
        applicationEventPublisher.publishEvent(new SmsSendEvemt(vo));

        //------------------------------------------------------------
        return vo;
    }


    //设置事件发送器
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    //发送短原始方式:该方法写在serviec中;
    /*private void doSend(String context,String phoneNumber){
        Map<String,String> parmas = new HashMap<String,String>();
        //中国网通的账号名
        parmas.put("Uid" ,propertiesUtil.getUid());
        //中国网通发送短信的密钥
        parmas.put("Key" , propertiesUtil.getKey());
        //要发送的手机号
        parmas.put("smsMob" , phoneNumber);
        //要发送的内容
        parmas.put("smsText" ,context );
        try {
            //调用该方法,发送短信:
            //返回是否成功的状态码:
            String state = HttpUtil.sendHttpRequest("http://utf8.api.smschinese.cn", parmas);
            //手机号格式不正确抛异常:
            if(state.equals("-4")){
                throw new DisplayableException("手机号格式不正确!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
