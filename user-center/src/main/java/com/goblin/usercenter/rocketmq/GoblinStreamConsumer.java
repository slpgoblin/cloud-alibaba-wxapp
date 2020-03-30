package com.goblin.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/16 13:29
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class GoblinStreamConsumer {

    @StreamListener(GoblinSink.GOBLIN_INPUT)
    public void receive(String messageBody){
        log.info("通过goblin-stream收到消息：{}",messageBody);
    }

}
