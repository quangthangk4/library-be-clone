package com.library.user.application.usecase;

import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.application.dto.request.UpdateUserProfileRequest;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.mapper.UserMapper;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UpdateUserProfileUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCaseImpl implements UpdateUserProfileUseCase {

    private final UserRepositoryInterface userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse execute(Long userId, UpdateUserProfileRequest request) {
        log.info("Updating profile for user ID: {}", userId);

        // Find user
        UserId id = UserId.of(userId);
        UserAggregate user = userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Update profile
        UserProfile newProfile = userMapper.mapToUserProfile(request);
        user.updateProfile(newProfile);

        // Save user
        UserAggregate updatedUser = userRepository.save(user);

        log.info("Successfully updated profile for user ID: {}", userId);

        return userMapper.toResponse(updatedUser);
    }
}
