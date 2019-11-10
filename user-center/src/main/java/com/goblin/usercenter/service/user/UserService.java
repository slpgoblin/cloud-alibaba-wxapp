package com.goblin.usercenter.service.user;

import com.goblin.usercenter.dao.user.UserMapper;
import com.goblin.usercenter.domain.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }


}
