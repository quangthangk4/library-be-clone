-- V4: Add provider and provider_id columns to users table for Social Login support
ALTER TABLE users ADD COLUMN provider VARCHAR(50);
ALTER TABLE users ADD COLUMN provider_id VARCHAR(255);

-- Optional: Create an index for faster lookups by provider and provider_id
CREATE INDEX idx_users_provider ON users(provider, provider_id);
