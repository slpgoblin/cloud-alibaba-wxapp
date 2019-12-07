package com.goblin.contentcenter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.feignclient.TestBlogFeignClient;
import com.goblin.contentcenter.feignclient.TestUserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
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

    @GetMapping("test-hot")
    @SentinelResource("hot")
    public String testHot(
            @RequestParam(required = false) String a,
            @RequestParam(required = false) String b){
        return a + " " + b;
    }

    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(
            @RequestParam(required = false) String a) {

        String resourceName = "test-sentinel-api";
        ContextUtil.enter(resourceName, "test-wfw");

        // 定义一个sentinel保护的资源，名称是test-sentinel-api
        Entry entry = null;
        try {

            entry = SphU.entry(resourceName);
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
        }
        // 如果被保护的资源被限流或者降级了，就会抛BlockException
        catch (BlockException e) {
            log.warn("限流，或者降级了", e);
            return "限流，或者降级了";
        } catch (IllegalArgumentException e2) {
            // 统计IllegalArgumentException【发生的次数、发生占比...】
            Tracer.trace(e2);
            return "参数非法！";
        } finally {
            if (entry != null) {
                // 退出entry
                entry.exit();
            }
            ContextUtil.exit();
        }
    }
}
