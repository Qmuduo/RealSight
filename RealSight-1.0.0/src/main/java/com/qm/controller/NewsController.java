package com.qm.controller;

import com.qm.common.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: RealSight-1.0.0
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-10 22:16
 **/
@RestController
@RequestMapping("/news")
public class NewsController {

    @GetMapping("/getNews")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public Result getNews() {
        return Result.ok().message("新闻列表");
    }

    @PutMapping("/addNews")
    @PreAuthorize("hasAnyAuthority('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public Result addNews() {
        return Result.ok().message("新增了一条新闻");
    }
}
