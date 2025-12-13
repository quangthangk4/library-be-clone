package com.library.user.domain.specification;

import com.library.user.domain.model.UserAggregate;

/**
 * Specification to check if a user can borrow books
 * Business rules:
 * - User must be ACTIVE
 * - User must have BORROW_BOOK permission
 * - User must not be suspended or deactivated
 */
public class CanBorrowBooksSpecification implements UserSpecification {

    @Override
    public boolean isSatisfiedBy(UserAggregate user) {
        if (user == null) {
            return false;
        }

        // User must be active
        if (!user.isActive()) {
            return false;
        }

        // User must have permission to borrow books
        return user.hasPermission("BORROW_BOOK");
    }
}
