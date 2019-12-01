package com.goblin.usercenter.test;

import cn.hutool.core.map.MapUtil;
import com.goblin.usercenter.domain.entity.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: goblin
 * @DATE: Created in 2019/11/24 12:08
 * @Description: 测试controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    public User getUser(User user) {
        return user;
    }

    @PostMapping("/user")
    public Map<Object, Object> postUser(@RequestBody User user){
        Map<Object, Object> map = MapUtil.of(new Object[][]{
                {"success", true},
                {"data", user}
        });
        return map;
    }

}
