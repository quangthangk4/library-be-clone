package com.library.circulation.dto.response;

import com.library.circulation.domain.enums.ActivityType;

import java.time.Instant;

public record ActivityResponse (
        String userFullName,
        ActivityType type,
        String targetName,
        Instant timestamp
){
}
