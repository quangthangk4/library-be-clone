package com.library.user.application.usecase.user.impl;

import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.application.dto.request.CreateUserRequest;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.mapper.UserMapper;
import com.library.user.application.usecase.user.CreateUserUseCase;
import com.library.user.domain.entities.Role;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.port.IPasswordHasher;
import com.library.user.domain.service.UserDomainService;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.PasswordHash;
import com.library.user.domain.valueobject.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CreateUserUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;
    private final IPasswordHasher passwordHasher;

    @Override
    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        // Validate uniqueness
        Email email = Email.of(request.email());
        userDomainService.validateUniqueEmail(email);

        // Get a role (default to STUDENT if not specified)
        String roleName = "STUDENT";
        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Create user profile
        UserProfile profile = userMapper.mapToUserProfile(request);

        // Hash password
        PasswordHash passwordHash = PasswordHash.createFromRaw(request.password(), passwordHasher);

        // Create user aggregate
        User user = User.create(
            email,
            passwordHash,
            profile,
            role
        );

        // Save user
        User savedUser = userRepository.save(user);

        log.info("Successfully created user with ID: {}", savedUser.getId().getValue());

        return userMapper.toResponse(savedUser);
    }
}
