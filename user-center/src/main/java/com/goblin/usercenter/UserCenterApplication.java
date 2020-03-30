package com.goblin.usercenter;

import com.goblin.usercenter.rocketmq.GoblinSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.goblin.usercenter.dao")
@SpringBootApplication
@EnableBinding({Sink.class, GoblinSink.class})
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
