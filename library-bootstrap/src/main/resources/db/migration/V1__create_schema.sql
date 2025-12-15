-- V1: Create database schema for Library Management System
-- Generated from DBeaver schema export

-- Authors table
CREATE TABLE authors (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    author_name VARCHAR(100) NOT NULL,
    biography TEXT NULL,
    date_of_birth DATE NULL,
    date_of_death DATE NULL,
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_author_name ON authors USING btree (author_name);

-- Categories table
CREATE TABLE categories (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    category_name VARCHAR(100) NOT NULL,
    parent_category_id BIGINT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (id),
    CONSTRAINT idx_category_name UNIQUE (category_name)
);

CREATE INDEX idx_parent_category ON categories USING btree (parent_category_id);

-- Publishers table
CREATE TABLE publishers (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    address VARCHAR(255) NULL,
    publisher_name VARCHAR(100) NOT NULL,
    CONSTRAINT publishers_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_publisher_name ON publishers USING btree (publisher_name);

-- Tags table
CREATE TABLE tags (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    tag_name VARCHAR(50) NOT NULL,
    CONSTRAINT idx_tag_name UNIQUE (tag_name),
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);

-- Publications table
CREATE TABLE publications (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    cover_image_url VARCHAR(255) NULL,
    description TEXT NULL,
    edition VARCHAR(100) NULL,
    isbn VARCHAR(13) NULL,
    language VARCHAR(50) NOT NULL,
    number_of_pages INT NULL,
    publication_year INT NULL,
    publisher_id BIGINT NULL,
    size VARCHAR(50) NULL,
    subtitle VARCHAR(255) NULL,
    title VARCHAR(255) NOT NULL,
    weight FLOAT8 NULL,
    CONSTRAINT idx_publication_isbn UNIQUE (isbn),
    CONSTRAINT publications_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_publication_publisher ON publications USING btree (publisher_id);
CREATE INDEX idx_publication_title ON publications USING btree (title);

-- Items table
CREATE TABLE items (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    acquired_date DATE NOT NULL,
    barcode VARCHAR(50) NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    location VARCHAR(100) NULL,
    publication_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT idx_item_barcode UNIQUE (barcode),
    CONSTRAINT items_item_type_check CHECK (item_type IN ('HARDCOVER', 'PAPERBACK', 'JOURNAL')),
    CONSTRAINT items_pkey PRIMARY KEY (id),
    CONSTRAINT items_status_check CHECK (status IN ('AVAILABLE', 'BORROWED', 'RESERVED', 'IN_MAINTENANCE', 'LOST'))
);

CREATE INDEX idx_item_publication_id ON items USING btree (publication_id);
CREATE INDEX idx_item_status ON items USING btree (status);

-- Publication-Author junction table
CREATE TABLE publication_authors (
    author_id BIGINT NOT NULL,
    publication_id BIGINT NOT NULL,
    CONSTRAINT publication_authors_pkey PRIMARY KEY (author_id, publication_id)
);

-- Publication-Category junction table
CREATE TABLE publication_categories (
    category_id BIGINT NOT NULL,
    publication_id BIGINT NOT NULL,
    CONSTRAINT publication_categories_pkey PRIMARY KEY (category_id, publication_id)
);

-- Publication-Tag junction table
CREATE TABLE publication_tags (
    publication_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT publication_tags_pkey PRIMARY KEY (publication_id, tag_id)
);

-- Roles table
CREATE TABLE roles (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    description VARCHAR(255) NULL,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT idx_role_name UNIQUE (role_name),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

-- Users table
CREATE TABLE users (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    account_status VARCHAR(255) NOT NULL,
    address VARCHAR(255) NULL,
    ai_personalization_enabled BOOLEAN NOT NULL,
    date_of_birth DATE NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    last_login_at TIMESTAMP(6) NULL,
    phone_number VARCHAR(20) NULL,
    profile_picture_url VARCHAR(255) NULL,
    CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT users_account_status_check CHECK (account_status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'DEACTIVATED')),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_email ON users USING btree (email);

-- User-Role junction table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Borrowing Transactions table
CREATE TABLE borrowing_transactions (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    borrowed_date TIMESTAMP(6) NOT NULL,
    due_date DATE NOT NULL,
    item_id BIGINT NOT NULL,
    librarian_id_issue BIGINT NULL,
    librarian_id_return BIGINT NULL,
    renewal_count INT NOT NULL,
    returned_date TIMESTAMP(6) NULL,
    status VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT borrowing_transactions_pkey PRIMARY KEY (id),
    CONSTRAINT borrowing_transactions_status_check CHECK (status IN ('ACTIVE', 'RETURNED', 'OVERDUE'))
);

CREATE INDEX idx_borrow_due_date ON borrowing_transactions USING btree (due_date);
CREATE INDEX idx_borrow_item_id ON borrowing_transactions USING btree (item_id);
CREATE INDEX idx_borrow_status ON borrowing_transactions USING btree (status);
CREATE INDEX idx_borrow_user_id ON borrowing_transactions USING btree (user_id);

-- Reservations table
CREATE TABLE reservations (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    notification_sent_date TIMESTAMP(6) NULL,
    publication_id BIGINT NOT NULL,
    reservation_date TIMESTAMP(6) NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT reservations_pkey PRIMARY KEY (id),
    CONSTRAINT reservations_status_check CHECK (status IN ('PENDING', 'FULFILLED', 'CANCELLED', 'EXPIRED'))
);

CREATE INDEX idx_reservation_date ON reservations USING btree (reservation_date);
CREATE INDEX idx_reservation_publication_id ON reservations USING btree (publication_id);
CREATE INDEX idx_reservation_status ON reservations USING btree (status);
CREATE INDEX idx_reservation_user_id ON reservations USING btree (user_id);

-- Fines table
CREATE TABLE fines (
    id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NULL,
    updated_at TIMESTAMP(6) NULL,
    fine_amount NUMERIC(10, 2) NOT NULL,
    fine_date DATE NOT NULL,
    paid_date TIMESTAMP(6) NULL,
    payment_status VARCHAR(20) NOT NULL,
    transaction_id BIGINT NOT NULL,
    CONSTRAINT fines_payment_status_check CHECK (payment_status IN ('UNPAID', 'PAID')),
    CONSTRAINT fines_pkey PRIMARY KEY (id),
    CONSTRAINT uk7t1elqs9tdjr1yfed9yobxnpf UNIQUE (transaction_id)
);

CREATE INDEX idx_fine_date ON fines USING btree (fine_date);
CREATE INDEX idx_fine_payment_status ON fines USING btree (payment_status);
CREATE INDEX idx_fine_transaction_id ON fines USING btree (transaction_id);
