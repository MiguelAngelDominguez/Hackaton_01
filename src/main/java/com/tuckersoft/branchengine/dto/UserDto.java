package com.tuckersoft.branchengine.dto;

import java.time.Instant;

public record UserDto(
        Long id,
        String email,
        String displayName,
        String role,
        Instant createdAt
) {
}