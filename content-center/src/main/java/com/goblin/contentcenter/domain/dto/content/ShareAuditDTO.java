package com.goblin.contentcenter.domain.dto.content;

import com.goblin.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/8 12:19
 * @Description:
 * @Version:
 */
@Data
public class ShareAuditDTO {

    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 原因
     */
    private String reason;

}
