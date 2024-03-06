package org.yuxiang.jin.officeauto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //登录拦截器的创建
    //本注解用于创建Spring MVC的拦截器对象
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    //权限拦截器的创建
    @Bean
    public PopedomInterceptor getPopedomInterceptor() {
        return new PopedomInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //对系统进行拦截器的创建
        InterceptorRegistration login = registry.addInterceptor(getLoginInterceptor());
        //加入对应的匹配路径,同时将不拦截的路径进行放行
        login.addPathPatterns("/*/*", "/*/*/*");
        //配置排除的路径
        login.excludePathPatterns("/oa/login", "/css/**", "/docs/**", "/fonts/**", "/images/**", "/resources/**");

        //注册权限拦截器
        InterceptorRegistration popedom = registry.addInterceptor(getPopedomInterceptor());
        //配置拦截的地址规则
        popedom.addPathPatterns("/*/*/*");
        popedom.excludePathPatterns("/oa/login", "/css/**", "/docs/**", "/fonts/**", "/images/**", "/resources/**");
    }
}
