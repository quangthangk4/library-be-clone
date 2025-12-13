package com.library.user.application.usecase;

import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.application.dto.request.CreateUserRequest;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.mapper.UserMapper;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.service.UserDomainService;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CreateUserUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryInterface userRepository;
    private final RoleRepository roleRepository;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        log.info("Creating new user with username: {}", request.getUsername());

        // Validate uniqueness
        Email email = Email.of(request.getEmail());
        userDomainService.validateUniqueUsername(request.getUsername());
        userDomainService.validateUniqueEmail(email);

        // Get role (default to READER if not specified)
        String roleName = request.getRoleName() != null ? request.getRoleName() : "READER";
        RoleAggregate role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Create user profile
        UserProfile profile = userMapper.mapToUserProfile(request);

        // Hash password
//        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create user aggregate
        UserAggregate user = UserAggregate.create(
            request.getUsername(),
            email,
//            hashedPassword,
                request.getPassword(),
            profile,
            role
        );

        // Save user
        UserAggregate savedUser = userRepository.save(user);

        log.info("Successfully created user with ID: {}", savedUser.getId().getValue());

        return userMapper.toResponse(savedUser);
    }
}
