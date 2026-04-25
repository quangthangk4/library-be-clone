ALTER TABLE borrowing_transactions DROP CONSTRAINT IF EXISTS borrowing_transactions_status_check;
ALTER TABLE borrowing_transactions ADD CONSTRAINT borrowing_transactions_status_check
    CHECK (status IN ('WAITING_FOR_PICKUP', 'BORROWING', 'RETURNED', 'OVERDUE', 'CANCELLED'));
