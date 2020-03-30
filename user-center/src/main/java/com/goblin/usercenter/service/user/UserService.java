package com.goblin.usercenter.service.user;

import com.alibaba.fastjson.JSONObject;
import com.goblin.usercenter.dao.bonus.BonusEventLogMapper;
import com.goblin.usercenter.dao.user.UserMapper;
import com.goblin.usercenter.domain.dto.message.UserAddBonusMsgDTO;
import com.goblin.usercenter.domain.entity.bonus.BonusEventLog;
import com.goblin.usercenter.domain.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;

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


}
