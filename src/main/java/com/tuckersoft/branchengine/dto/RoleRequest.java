package com.tuckersoft.branchengine.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank(message = "El rol es obligatorio")
        String role
) {
}