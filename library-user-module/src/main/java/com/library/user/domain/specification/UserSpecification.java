package com.library.user.domain.specification;

import com.library.user.domain.model.UserAggregate;

/**
 * Specification pattern for User business rules
 * Specifications encapsulate business rules that can be combined and reused
 */
public interface UserSpecification {

    /**
     * Check if specification is satisfied by the user
     */
    boolean isSatisfiedBy(UserAggregate user);

    /**
     * Combine specifications with AND logic
     */
    default UserSpecification and(UserSpecification other) {
        return user -> this.isSatisfiedBy(user) && other.isSatisfiedBy(user);
    }

    /**
     * Combine specifications with OR logic
     */
    default UserSpecification or(UserSpecification other) {
        return user -> this.isSatisfiedBy(user) || other.isSatisfiedBy(user);
    }

    /**
     * Negate the specification
     */
    default UserSpecification not() {
        return user -> !this.isSatisfiedBy(user);
    }
}
