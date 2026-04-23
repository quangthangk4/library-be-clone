CREATE TABLE password_reset_tokens (
    token       VARCHAR(36)  NOT NULL,
    user_id     BIGINT       NOT NULL,
    expires_at  TIMESTAMPTZ  NOT NULL,
    CONSTRAINT password_reset_tokens_pkey PRIMARY KEY (token)
);

CREATE INDEX idx_password_reset_tokens_user_id ON password_reset_tokens (user_id);
