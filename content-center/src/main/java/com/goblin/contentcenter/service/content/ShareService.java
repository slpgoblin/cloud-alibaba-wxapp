package com.goblin.contentcenter.service.content;

import com.alibaba.fastjson.JSONObject;
import com.goblin.contentcenter.dao.content.ShareMapper;
import com.goblin.contentcenter.dao.message.RocketmqTransactionLogMapper;
import com.goblin.contentcenter.domain.dto.content.ShareAuditDTO;
import com.goblin.contentcenter.domain.dto.content.ShareDTO;
import com.goblin.contentcenter.domain.dto.message.UserAddBonusMsgDTO;
import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.domain.entity.content.Share;
import com.goblin.contentcenter.domain.entity.message.RocketmqTransactionLog;
import com.goblin.contentcenter.domain.enums.AuditStatusEnum;
import com.goblin.contentcenter.feignclient.UserCenterFeignClient;
import com.google.common.base.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @program: content-center
 * @description:
 * @author: Guojs
 * @create: 2019-10-26 10:53
 **/
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    private final RocketMQTemplate rocketMQTemplate;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    private final Source source;


    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        // 发布人id
        Integer userId = share.getUserId();

        UserDTO userDTO = userCenterFeignClient.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

    public Share auditById(Integer id, ShareAuditDTO shareAuditDTO) {
        // 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = shareMapper.selectByPrimaryKey(id);
        if (share == null){
            throw new IllegalArgumentException("非法参数！该分享不存在");
        }
        if (!Objects.equal("NOT_YET",share.getAuditStatus())){
            throw new IllegalArgumentException("非法参数！该分享已审核完成");
        }
        //审核资源，将状态设为PASS/REJECT
//        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
//        shareMapper.updateByPrimaryKey(share);
//        //如果是PASS，那么为发布人添加积分,异步执行
////        userCenterFeignClient.addBonus(id,500);
//        //改造为mq，发消息给ma，用户中心去消费
//        rocketMQTemplate.convertAndSend("add-bonus",
//                UserAddBonusMsgDTO.builder()
//                        .userId(share.getUserId())
//                        .bonus(50)
//                        .build()
//        );
        //将share添加到redis等业务操作

        // 二次改造兼容分布式事务
        if (AuditStatusEnum.PASS.equals(shareAuditDTO.getAuditStatusEnum())) {
//            rocketMQTemplate.sendMessageInTransaction(
//                    //事务消息生产者组
//                    "tx-add-bonus-group",
//                    //主题
//                    "add-bonus",
//                    //消息体
//                    MessageBuilder
//                            .withPayload(
//                                    UserAddBonusMsgDTO.builder()
//                                            .userId(share.getUserId())
//                                            .bonus(50)
//                                            .build()
//                            )
//                            //header也有大用处
//                            .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
//                            .setHeader("share_id",id)
//                            .build(),
//                    //arg有大用处
//                    shareAuditDTO);

            //三次改造   使用Stream
            source.output().send(
                MessageBuilder
                    .withPayload(
                        UserAddBonusMsgDTO.builder()
                                .userId(share.getUserId())
                                .bonus(50)
                                .build()
                    )
                    //header也有大用处
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
                    .setHeader("share_id",id)
                    .setHeader("dto", JSONObject.toJSONString(shareAuditDTO))
                    .build()
            );
        }else {
            auditByIdInDB(id, shareAuditDTO);
        }
        return share;
    }

    /**
     * 单体方法
     * @param id
     * @param auditDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id,ShareAuditDTO auditDTO){
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        shareMapper.updateByPrimaryKeySelective(share);
        // 把share写到缓存
    }

    /**
     * 事务消息方法
     * @param id
     * @param auditDTO
     * @param transactionId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        auditByIdInDB(id, auditDTO);

        rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
    }
}
