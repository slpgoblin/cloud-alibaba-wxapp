package com.goblin.usercenter.controller.user;

import cn.hutool.core.util.ObjectUtil;
import com.goblin.usercenter.auth.CheckLogin;
import com.goblin.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.goblin.usercenter.domain.dto.user.LoginRespDTO;
import com.goblin.usercenter.domain.dto.user.UserLoginDTO;
import com.goblin.usercenter.domain.dto.user.UserRespDTO;
import com.goblin.usercenter.domain.entity.user.User;
import com.goblin.usercenter.service.user.UserService;
import com.goblin.usercenter.util.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JwtOperator jwtOperator;

    @GetMapping("/{id}")
    @CheckLogin
    public User findById(@PathVariable Integer id) {
        log.info("我被请求了...");
        return this.userService.findById(id);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) {

        // 看用户是否注册，如果没有注册就（插入）
        // 如果已经注册
        User user = this.userService.login(loginDTO);
        if (ObjectUtil.isNull(user)){
            return LoginRespDTO.builder().build();
        }

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info(
                "用户{}登录成功，生成的token = {}, 有效期到:{}",
                loginDTO.getWxNickname(),
                token,
                jwtOperator.getExpirationTime()
        );

        // 构建响应
        return LoginRespDTO.builder()
                .user(
                        UserRespDTO.builder()
                                .id(user.getId())
                                .avatarUrl(user.getAvatarUrl())
                                .bonus(user.getBonus())
                                .wxNickname(user.getWxNickname())
                                .build()
                )
                .token(
                        JwtTokenRespDTO.builder()
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .token(token)
                                .build()
                )
                .build();
    }

}
