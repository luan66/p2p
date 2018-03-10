package cn.wolfcode.p2p.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义springmvc配置器:
 */
@Component
public class MyMvcConfigurer extends WebMvcConfigurerAdapter {

    //添加自定义的拦截器到spring容器中;
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器,并定义拦截的路径;
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }
}
