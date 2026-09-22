package com.tuckersoft.branchengine.controller;

import com.tuckersoft.branchengine.dto.RoleRequest;
import com.tuckersoft.branchengine.dto.UserDto;
import com.tuckersoft.branchengine.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        return userService.getMe(authentication.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> list() {
        return userService.findAll();
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto changeRole(@PathVariable Long id,
                              @Valid @RequestBody RoleRequest request,
                              Authentication authentication) {
        return userService.changeRole(id, request.role(), authentication.getName());
    }
}