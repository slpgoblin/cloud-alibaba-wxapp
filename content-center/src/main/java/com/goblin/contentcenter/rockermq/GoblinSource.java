package com.goblin.contentcenter.rockermq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GoblinSource {

    String GOBLIN_OUTPUT = "goblin-output";

    @Output(GOBLIN_OUTPUT)
    MessageChannel outPut();

}
