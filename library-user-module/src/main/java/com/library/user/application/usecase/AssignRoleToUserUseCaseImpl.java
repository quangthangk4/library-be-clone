package com.library.user.application.usecase;

import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.mapper.UserMapper;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.service.UserDomainService;
import com.library.user.domain.valueobject.RoleId;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AssignRoleToUserUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AssignRoleToUserUseCaseImpl implements AssignRoleToUserUseCase {

    private final UserRepositoryInterface userRepository;
    private final RoleRepository roleRepository;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse execute(Long userId, Long roleId) {
        log.info("Assigning role ID: {} to user ID: {}", roleId, userId);

        // Find user
        UserId id = UserId.of(userId);
        UserAggregate user = userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Find role
        RoleId rId = RoleId.of(roleId);
        RoleAggregate role = roleRepository.findById(rId)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Validate role change
        userDomainService.validateRoleChange(user, role);

        // Assign role
        user.assignRole(role);

        // Save user
        UserAggregate updatedUser = userRepository.save(user);

        log.info("Successfully assigned role to user ID: {}", userId);

        return userMapper.toResponse(updatedUser);
    }
}
