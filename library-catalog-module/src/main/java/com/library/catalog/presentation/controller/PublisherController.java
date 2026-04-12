package com.library.catalog.presentation.controller;

import com.library.catalog.dto.response.author.AuthorOverviewResponse;
import com.library.catalog.dto.response.publisher.PublisherOverviewResponse;
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
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    // not finish
    // limit 10
    @RequiresRole(RoleConstants.LIBRARIAN)
    @GetMapping
    public ApiResponseApp<List<PublisherOverviewResponse>> searchPublisher(@RequestParam("keyword") String keyword) {
        log.info("Search publisher with keyword: {}", keyword);
        if ("thangvip123".contains(keyword.toLowerCase()))
            return ApiResponseApp.success(List.of(
                    PublisherOverviewResponse.builder().id(55L).name("Thang").build(),
                    PublisherOverviewResponse.builder().id(2L).name("ThangVip123").build(),
                    PublisherOverviewResponse.builder().id(3L).name("test1").build(),
                    PublisherOverviewResponse.builder().id(4L).name("test2").build()
            ));
        return ApiResponseApp.success(null);
    }


    // not finish
    @PostMapping
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<Void> createPublisher(@RequestBody String name) {
        log.info("Create publisher: {}", name);
        return ApiResponseApp.success(null);
    }

}
