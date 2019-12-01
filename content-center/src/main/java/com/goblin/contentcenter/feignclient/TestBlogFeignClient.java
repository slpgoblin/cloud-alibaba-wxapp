package com.goblin.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: goblin
 * @DATE: Created in 2019/11/24 13:26
 * @Description: 测试脱离ribbon使用的客户端
 * @Version: 1.0
 */
@FeignClient(name = "blog",url = "https://www.goblin-blog.top/")
public interface TestBlogFeignClient {

    @GetMapping("")
    String index();
}
