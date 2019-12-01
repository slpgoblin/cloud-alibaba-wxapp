package com.goblin.contentcenter;

import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.feignclient.TestBlogFeignClient;
import com.goblin.contentcenter.feignclient.TestUserCenterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TestUserCenterFeignClient testUserCenterFeignClient;
    @Autowired
    private TestBlogFeignClient testBlogFeignClient;

    @GetMapping("/getInstances")
    public List<ServiceInstance> getInstances() {
        // 获取用户信息实例
        return discoveryClient.getInstances("user-center");
    }

    @GetMapping("test-get")
    public UserDTO testGet(UserDTO userDTO){
        return testUserCenterFeignClient.getUser(userDTO);
    }

    @PostMapping("test-post")
    public Map testPost(@RequestBody UserDTO userDTO){
        return testUserCenterFeignClient.postUser(userDTO);
    }

    @GetMapping("test-blog")
    public String testBlog(){
        return testBlogFeignClient.index();
    }
}
