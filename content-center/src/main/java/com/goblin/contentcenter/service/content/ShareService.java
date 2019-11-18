package com.goblin.contentcenter.service.content;

import com.goblin.contentcenter.dao.content.ShareMapper;
import com.goblin.contentcenter.domain.dto.content.ShareDTO;
import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.domain.entity.content.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
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
public class ShareService {

    @Resource
    private ShareMapper shareMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;


    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        // 发布人id
        Integer userId = share.getUserId();

        UserDTO userDTO = restTemplate.getForObject("http://user-center/users/{userId}" ,UserDTO.class,userId);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

}
