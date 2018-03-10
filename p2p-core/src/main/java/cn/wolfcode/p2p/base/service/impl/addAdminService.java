package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 启动就创建管理员:
 */
@Service
public class addAdminService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ILoginInfoService loginInfoService;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        loginInfoService.addAdmin();
    }
}
