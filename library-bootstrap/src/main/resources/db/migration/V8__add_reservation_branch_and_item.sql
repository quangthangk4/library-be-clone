-- Fix status constraint to match ReservationStatus enum (drop first, then migrate data, then re-add)
ALTER TABLE reservations DROP CONSTRAINT reservations_status_check;

-- Migrate FULFILLED → COMPLETED (old seed data uses FULFILLED)
UPDATE reservations SET status = 'COMPLETED' WHERE status = 'FULFILLED';

ALTER TABLE reservations ADD CONSTRAINT reservations_status_check
    CHECK (status IN ('PENDING', 'READY_FOR_PICKUP', 'CANCELLED', 'EXPIRED', 'COMPLETED'));

-- Add preferred_branch and assigned_item_id
ALTER TABLE reservations ADD COLUMN preferred_branch VARCHAR(50) NOT NULL DEFAULT 'ANY';
ALTER TABLE reservations ADD COLUMN assigned_item_id BIGINT REFERENCES items(id);

CREATE INDEX idx_reservation_assigned_item ON reservations USING btree (assigned_item_id);
