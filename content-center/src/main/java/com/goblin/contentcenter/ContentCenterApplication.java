package com.goblin.contentcenter;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.goblin.contentcenter.rockermq.GoblinSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.goblin.contentcenter.dao")
@SpringBootApplication
@EnableFeignClients// (defaultConfiguration = UserCenterFeignConfig.class)
@EnableBinding({Source.class, GoblinSource.class})
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
