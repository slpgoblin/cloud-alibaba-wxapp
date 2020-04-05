package com.goblin.usercenter.service.user;

import cn.hutool.core.lang.caller.SecurityManagerCaller;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.goblin.usercenter.dao.bonus.BonusEventLogMapper;
import com.goblin.usercenter.dao.user.UserMapper;
import com.goblin.usercenter.dao.user.UserPasswordMapper;
import com.goblin.usercenter.domain.dto.message.UserAddBonusMsgDTO;
import com.goblin.usercenter.domain.dto.user.UserLoginDTO;
import com.goblin.usercenter.domain.entity.bonus.BonusEventLog;
import com.goblin.usercenter.domain.entity.user.User;
import com.goblin.usercenter.domain.entity.user.UserPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;
    @Resource
    private UserPasswordMapper userPasswordMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        //为用户加积分
        Integer userId = userAddBonusMsgDTO.getUserId();
        Integer bonus = userAddBonusMsgDTO.getBonus();
        User user = userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKeySelective(user);
        //记录日志到log表
        bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event("CONTERIBUTE")
                        .createTime(new Date())
                        .description("投稿加积分")
                        .build()
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public User login(UserLoginDTO loginDTO) {
        Example example = new Example(UserPassword.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", loginDTO.getUserId());
        criteria.andEqualTo("encrptPassword", SecureUtil.md5(loginDTO.getPassword()));
        User user = userMapper.selectOne(
                User.builder()
                        .wxId(loginDTO.getUserId())
                        .build()
        );
        if (user == null) {
            User userToSave = User.builder()
                    .wxId(loginDTO.getUserId())
                    .bonus(300)
                    .wxNickname(loginDTO.getWxNickname())
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .roles("user")
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            UserPassword password = UserPassword.builder()
                    .userId(Integer.valueOf(loginDTO.getUserId()))
                    .encrptPassword(SecureUtil.md5(loginDTO.getPassword()))
                    .build();
            userPasswordMapper.insertSelective(password);
            userMapper.insertSelective(userToSave);
            return userToSave;
        }else {
            UserPassword userPassword = userPasswordMapper.selectOneByExample(example);
            if (ObjectUtil.isNull(userPassword)){
                return null;
            }
            return user;
        }
    }
}
