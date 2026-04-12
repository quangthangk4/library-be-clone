package com.library.catalog.presentation.controller;

import com.library.catalog.dto.response.author.AuthorOverviewResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.util.RequiresRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    // not finish
    // limit 10
    @RequiresRole(RoleConstants.LIBRARIAN)
    @GetMapping
    public ApiResponseApp<List<AuthorOverviewResponse>> searchAuthor(@RequestParam("keyword") String keyword) {
        log.info("Search author with keyword: {}", keyword);
        if ("thangvip123".contains(keyword.toLowerCase()))
            return ApiResponseApp.success(List.of(
                    AuthorOverviewResponse.builder().id(55L).name("Thang").build(),
                    AuthorOverviewResponse.builder().id(2L).name("ThangVip123").build(),
                    AuthorOverviewResponse.builder().id(3L).name("test1").build(),
                    AuthorOverviewResponse.builder().id(4L).name("test2").build()
            ));
        return ApiResponseApp.success(null);
    }


    // not finish
    @PostMapping
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<Void> createAuthor(@RequestBody String name) {
        log.info("Create author: {}", name);
        return ApiResponseApp.success(null);
    }
}
