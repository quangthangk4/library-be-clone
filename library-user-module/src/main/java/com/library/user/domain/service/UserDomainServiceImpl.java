package com.library.user.domain.service;

import com.library.user.domain.entities.Role;
import com.library.user.domain.entities.User;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.Email;

/**
 * Domain Service for User-related business logic that spans multiple aggregates
 * Domain services contain business logic that doesn't naturally fit within a single entity/aggregate
 */
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDomainServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void validateUniqueEmail(Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email.getValue());
        }
    }

    @Override
    public void assignDefaultRole(User user) {
        Role studentRole = roleRepository.findByName("STUDENT")
            .orElseThrow(() -> new IllegalStateException("Default STUDENT role not found"));

        user.assignRole(studentRole);
    }

    @Override
    public void validateRoleChange(User user, Role newRole) {
        // Business rule: Cannot change a role if a user has active borrowing transactions
        // This check would require calling circulation module, so for now we just validate the role exists
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        // Ensure the role exists in the system
        roleRepository.findById(newRole.getId())
            .orElseThrow(() -> new IllegalArgumentException("Role does not exist"));
    }

    @Override
    public boolean isEmailAvailable(Email email) {
        return !userRepository.existsByEmail(email);
    }
}
