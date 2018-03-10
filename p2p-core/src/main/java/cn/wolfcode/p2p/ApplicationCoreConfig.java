package cn.wolfcode.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "cn.wolfcode.p2p.*.mapper")
@ComponentScan(basePackages = "cn")
public class ApplicationCoreConfig {

}