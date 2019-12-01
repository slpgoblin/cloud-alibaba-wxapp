package com.goblin.contentcenter.feignclient;

import com.goblin.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {

    @GetMapping("/test/user")
    UserDTO getUser(@SpringQueryMap UserDTO userDTO);

    @PostMapping("/test/user")
    Map postUser(@RequestBody UserDTO userDTO);

}
