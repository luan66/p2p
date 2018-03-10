package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 后台的spring配置对象
 */
@SpringBootApplication
@PropertySource("classpath:application-mgrsite.properties")
@Import(ApplicationCoreConfig.class)
//@EnableScheduling
public class ApplicationMgrSiteConfig
{
    public static void main(String[] args )
    {
        SpringApplication.run(ApplicationCoreConfig.class,args);
    }
}
