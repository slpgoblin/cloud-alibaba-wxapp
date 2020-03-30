package com.goblin.usercenter.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GoblinSink {

    String GOBLIN_INPUT = "goblin-input";

    @Input(GOBLIN_INPUT)
    SubscribableChannel input();

}
