-- Migration: Create refresh_tokens table
-- Version: V3
-- Description: Store refresh tokens for multi-device login and session management
CREATE TABLE refresh_tokens (
uuid_token  VARCHAR(255) PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    device_id   VARCHAR(255),
    revoked     BOOLEAN NOT NULL DEFAULT FALSE,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- Đảm bảo mỗi user trên một thiết bị chỉ có 1 token active (theo UniqueConstraint bạn đã khai báo)
    CONSTRAINT uk_user_device UNIQUE (user_id, device_id),
    -- Khóa ngoại liên kết tới bảng users (giả sử bảng users đã được tạo ở V1)
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Index để tìm kiếm nhanh theo user_id
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
