package com.goblin.contentcenter.feignclient;

import com.goblin.contentcenter.config.UserCenterFeignConfig;
import com.goblin.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "user-center",configuration = UserCenterFeignConfig.class)
@FeignClient(name = "user-center")
public interface UserCenterFeignClient {

    /**
     *  http://user-center/users/{id}
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable(name = "id") Integer id);

}
