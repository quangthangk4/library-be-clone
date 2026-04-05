-- V5: Make hashed_password column nullable for users table
-- This supports social login users who do not have a password
ALTER TABLE users ALTER COLUMN hashed_password DROP NOT NULL;
