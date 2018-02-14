package com.ideamoment.emars;

import com.ideamoment.emars.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class EmarsConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).excludePathPatterns(
                "/index",
                "/login",
                "/login/**",
                "/error",
                "/logout",
                "/isLogined"
        );
    }
}
