package com.goblin.contentcenter.controller.content;

import com.goblin.contentcenter.domain.dto.content.ShareDTO;
import com.goblin.contentcenter.service.content.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: content-center
 * @description:
 * @author: Guojs
 * @create: 2019-10-26 10:49
 **/
@RestController
@RequestMapping("/shares")
@Slf4j
public class ShareConroller {

    @Resource
    private ShareService shareService;


    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return shareService.findById(id);
    }

}
