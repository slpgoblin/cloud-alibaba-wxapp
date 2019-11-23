package com.goblin.contentcenter.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

// 这里不能写configuration注解，父子上下文问题
public class UserCenterFeignConfig {

    @Bean
    public Logger.Level level(){
        // 打印http请求所有信息
        return Logger.Level.FULL;
    }

}
