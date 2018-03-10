package cn.wolfcode.p2p.base.event;

import org.springframework.context.ApplicationEvent;

/**
 * 发送短信事件
 */
public class SmsSendEvemt extends ApplicationEvent {
    public SmsSendEvemt(Object source) {
        super(source);
    }
}
