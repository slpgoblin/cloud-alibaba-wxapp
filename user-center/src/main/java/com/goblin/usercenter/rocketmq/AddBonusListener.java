//package com.goblin.usercenter.rocketmq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.goblin.usercenter.dao.bonus.BonusEventLogMapper;
//import com.goblin.usercenter.dao.user.UserMapper;
//import com.goblin.usercenter.domain.dto.message.UserAddBonusMsgDTO;
//import com.goblin.usercenter.domain.entity.bonus.BonusEventLog;
//import com.goblin.usercenter.domain.entity.user.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @Author: goblin
// * @DATE: Created in 2020/3/13 8:41
// * @Description:
// * @Version:
// */
//@Service
//@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bonus")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Slf4j
//public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {
//
//    private final UserMapper userMapper;
//    private final BonusEventLogMapper bonusEventLogMapper;
//
//    /**
//     * 收到消息的时候执行的业务方法
//     * @param message
//     */
//    @Override
//    public void onMessage(UserAddBonusMsgDTO message) {
//        log.info("执行消费mq  message：{}", JSONObject.toJSONString(message));
//        //为用户加积分
//        Integer userId = message.getUserId();
//        Integer bonus = message.getBonus();
//        User user = userMapper.selectByPrimaryKey(userId);
//        user.setBonus(user.getBonus() + bonus);
//        userMapper.updateByPrimaryKeySelective(user);
//        //记录日志到log表
//        bonusEventLogMapper.insert(
//                BonusEventLog.builder()
//                        .userId(userId)
//                        .value(bonus)
//                        .event("CONTERIBUTE")
//                        .createTime(new Date())
//                        .description("投稿加积分")
//                         .build()
//        );
//    }
//
//}
