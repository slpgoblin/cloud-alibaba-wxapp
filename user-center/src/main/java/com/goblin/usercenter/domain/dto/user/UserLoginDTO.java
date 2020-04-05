package com.goblin.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserLoginDTO {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信昵称
     */
    private String wxNickname;

    /**
     * 头像地址
     */
    private String avatarUrl;

}