package com.goblin.contentcenter.controller.content;

import com.goblin.contentcenter.auth.CheckAuthorization;
import com.goblin.contentcenter.domain.dto.content.ShareAuditDTO;
import com.goblin.contentcenter.domain.entity.content.Share;
import com.goblin.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/8 12:18
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {

    @Autowired
    private ShareService shareService;

    @PutMapping("/audit/{id}")
    @CheckAuthorization(value = "admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO shareAuditDTO){
        return shareService.auditById(id,shareAuditDTO);
    }

}
