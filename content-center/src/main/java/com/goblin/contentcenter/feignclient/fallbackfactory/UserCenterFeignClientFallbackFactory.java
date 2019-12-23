package com.goblin.contentcenter.feignclient.fallbackfactory;

import com.goblin.contentcenter.domain.dto.user.UserDTO;
import com.goblin.contentcenter.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: goblin
 * @DATE: Created in 2019/12/24 0:06
 * @Description: 获取异常的
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserCenterFeignClientFallbackFactory implements FallbackFactory<UserCenterFeignClient> {

    @Override
    public UserCenterFeignClient create(Throwable cause) {
        return new UserCenterFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("远程调用被限流/降级了", cause);
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("流控/降级返回的用户");
                return userDTO;
            }
        };
    }

}
