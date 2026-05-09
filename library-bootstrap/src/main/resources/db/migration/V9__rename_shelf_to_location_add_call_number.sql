ALTER TABLE items RENAME COLUMN shelf TO location;
ALTER TABLE publications ADD COLUMN call_number VARCHAR(100);
