package com.tuckersoft.branchengine.service;

import com.tuckersoft.branchengine.dto.UserDto;
import com.tuckersoft.branchengine.exception.BadRequestException;
import com.tuckersoft.branchengine.exception.ResourceNotFoundException;
import com.tuckersoft.branchengine.model.User;
import com.tuckersoft.branchengine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getMe(String email) {
        return toDto(findByEmail(email));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public UserDto changeRole(Long id, String role, String requestingEmail) {
        if (!ROLE_USER.equals(role) && !ROLE_ADMIN.equals(role)) {
            throw new BadRequestException("El rol debe ser " + ROLE_USER + " o " + ROLE_ADMIN);
        }

        User target = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        User requester = findByEmail(requestingEmail);
        if (target.getId().equals(requester.getId())) {
            throw new BadRequestException("No puedes cambiar tu propio rol");
        }

        target.setRole(role);
        return toDto(userRepository.save(target));
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName(), user.getRole(), user.getCreatedAt());
    }
}