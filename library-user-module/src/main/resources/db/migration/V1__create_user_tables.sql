-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    last_login_at TIMESTAMP,
    profile_picture_url VARCHAR(255),
    ai_personalization_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create Roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create Permissions table
CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT PRIMARY KEY,
    permission_name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create User-Role junction table (Many-to-Many)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create Role-Permission junction table (Many-to-Many)
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_role_name ON roles(role_name);
CREATE INDEX IF NOT EXISTS idx_permission_name ON permissions(permission_name);

-- Insert default roles
INSERT INTO roles (id, role_name, description, created_at) VALUES
    (1, 'ADMIN', 'Administrator with full system access', CURRENT_TIMESTAMP),
    (2, 'LIBRARIAN', 'Library staff with operational access', CURRENT_TIMESTAMP),
    (3, 'READER', 'Regular library member', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- Insert default permissions
INSERT INTO permissions (id, permission_name, description, created_at) VALUES
    -- User management permissions
    (1, 'MANAGE_USERS', 'Create, update, delete users', CURRENT_TIMESTAMP),
    (2, 'VIEW_USERS', 'View user information', CURRENT_TIMESTAMP),

    -- Role and permission management
    (3, 'MANAGE_ROLES', 'Create, update, delete roles', CURRENT_TIMESTAMP),
    (4, 'MANAGE_PERMISSIONS', 'Assign permissions to roles', CURRENT_TIMESTAMP),

    -- Publication management permissions
    (5, 'MANAGE_PUBLICATIONS', 'Add, update, delete publications', CURRENT_TIMESTAMP),
    (6, 'VIEW_PUBLICATIONS', 'View publication catalog', CURRENT_TIMESTAMP),

    -- Borrowing permissions
    (7, 'BORROW_BOOK', 'Borrow books from library', CURRENT_TIMESTAMP),
    (8, 'RETURN_BOOK', 'Return borrowed books', CURRENT_TIMESTAMP),
    (9, 'RENEW_BOOK', 'Renew borrowed books', CURRENT_TIMESTAMP),
    (10, 'RESERVE_BOOK', 'Reserve books', CURRENT_TIMESTAMP),

    -- Transaction management
    (11, 'MANAGE_TRANSACTIONS', 'Process borrowing/return transactions', CURRENT_TIMESTAMP),
    (12, 'MANAGE_FINES', 'Process fines and payments', CURRENT_TIMESTAMP),

    -- Review and rating
    (13, 'WRITE_REVIEW', 'Write book reviews', CURRENT_TIMESTAMP),
    (14, 'RATE_BOOK', 'Rate books', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- Assign permissions to ADMIN role (full access)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
    (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14)
ON CONFLICT DO NOTHING;

-- Assign permissions to LIBRARIAN role
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (2, 2), (2, 5), (2, 6), (2, 11), (2, 12)
ON CONFLICT DO NOTHING;

-- Assign permissions to READER role
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 13), (3, 14)
ON CONFLICT DO NOTHING;
