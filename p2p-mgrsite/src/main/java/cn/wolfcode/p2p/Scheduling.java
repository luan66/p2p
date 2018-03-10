package cn.wolfcode.p2p;

import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class Scheduling {

    @Scheduled(fixedDelay=2000)
    public void method(){
        System.out.println("=================");
    }
}
