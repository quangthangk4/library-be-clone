-- Add RETURN_CONFIRMED to notifications type constraint
ALTER TABLE notifications
    DROP CONSTRAINT notifications_type_check;

ALTER TABLE notifications
    ADD CONSTRAINT notifications_type_check CHECK (type IN (
        'BOOK_RESERVED', 'BOOK_AVAILABLE', 'BORROW_SUCCESS',
        'BORROW_CANCELLED_EXPIRED', 'OVERDUE_WARNING',
        'FINE_ISSUED', 'SYSTEM_MAINTENANCE', 'RETURN_REMINDER',
        'PICKUP_CONFIRMED', 'RETURN_CONFIRMED'
    ));
