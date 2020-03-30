package com.goblin.contentcenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/12 13:36
 * @Description:
 * @Version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDTO {

    private Integer userId;

    //积分
    private Integer bonus;

}
