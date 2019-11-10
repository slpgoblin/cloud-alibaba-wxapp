package com.goblin.contentcenter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: content-center
 * @description: 测试接口
 * @author: Guojs
 * @create: 2019-11-03 12:24
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/getInstances")
    public List<ServiceInstance> getInstances() {
        // 获取用户信息实例
        return discoveryClient.getInstances("user-center");
    }
}
