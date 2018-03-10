package cn.wolfcode.p2p;

import cn.wolfcode.p2p.interceptor.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-website.properties")
@Import(ApplicationCoreConfig.class)
//@ImportResource("classpath:applicationContext.xml")
public class ApplicationWebSiteConfig
{
    public static void main(String[] args )
    {
        SpringApplication.run(ApplicationWebSiteConfig.class,args);
    }

    //配置拦截器:
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}
