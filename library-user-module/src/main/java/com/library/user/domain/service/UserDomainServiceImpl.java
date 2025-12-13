package com.library.user.domain.service;

import com.library.user.domain.model.Permission;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.repository.PermissionRepository;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.valueobject.Email;

/**
 * Domain Service for User-related business logic that spans multiple aggregates
 * Domain services contain business logic that doesn't naturally fit within a single entity/aggregate
 */
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepositoryInterface userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public UserDomainServiceImpl(UserRepositoryInterface userRepository,
                                RoleRepository roleRepository,
                                PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void validateUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }

    @Override
    public void validateUniqueEmail(Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email.getValue());
        }
    }

    @Override
    public boolean canUserPerformAction(UserAggregate user, String permissionName) {
        if (!user.isActive()) {
            return false;
        }
        return user.hasPermission(permissionName);
    }

    @Override
    public void assignDefaultRole(UserAggregate user) {
        RoleAggregate readerRole = roleRepository.findByName("READER")
            .orElseThrow(() -> new IllegalStateException("Default READER role not found"));

        user.assignRole(readerRole);
    }

    @Override
    public void validateRoleChange(UserAggregate user, RoleAggregate newRole) {
        // Business rule: Cannot change role if user has active borrowing transactions
        // This check would require calling circulation module, so for now we just validate the role exists
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        // Ensure the role exists in the system
        roleRepository.findById(newRole.getId())
            .orElseThrow(() -> new IllegalArgumentException("Role does not exist"));
    }

    @Override
    public void validatePermissionAssignment(RoleAggregate role, Permission permission) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        // Ensure the permission exists in the system
        permissionRepository.findById(permission.getId())
            .orElseThrow(() -> new IllegalArgumentException("Permission does not exist"));
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailAvailable(Email email) {
        return !userRepository.existsByEmail(email);
    }
}
