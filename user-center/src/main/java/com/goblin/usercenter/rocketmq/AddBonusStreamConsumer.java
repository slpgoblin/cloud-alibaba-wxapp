package com.goblin.usercenter.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.goblin.usercenter.domain.dto.message.UserAddBonusMsgDTO;
import com.goblin.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/26 9:33
 * @Description:
 * @Version:
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AddBonusStreamConsumer {

    private final UserService userService;

    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO messageBody){
        log.info("执行消费mq  message：{}", JSONObject.toJSONString(messageBody));
        userService.addBonus(messageBody);
    }

}
