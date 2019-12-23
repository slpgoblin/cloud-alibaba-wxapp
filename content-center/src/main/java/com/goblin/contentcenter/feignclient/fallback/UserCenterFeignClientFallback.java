package com.goblin.contentcenter.feignclient.fallback;

import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: goblin
 * @DATE: Created in 2019/12/23 23:59
 * @Description: 自定义sentinel异常
 * @Version: 1.0
 */
@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient {

    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("流控/降级返回的用户");
        return userDTO;
    }

}
