package com.library.user.domain.specification;

import com.library.user.domain.model.UserAggregate;

/**
 * Specification to check if user is active
 */
public class IsActiveUserSpecification implements UserSpecification {

    @Override
    public boolean isSatisfiedBy(UserAggregate user) {
        return user != null && user.isActive();
    }
}
