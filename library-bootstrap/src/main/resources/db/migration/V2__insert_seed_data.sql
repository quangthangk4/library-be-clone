-- V2: Insert seed data for Library Management System
-- Creates test data for all entities

-- ============================================================
-- ROLES (3 records)
-- ============================================================
INSERT INTO roles (id, created_at, updated_at, role_name, description) VALUES
(1, NOW(), NOW(), 'STUDENT', 'Student role - can borrow books and make reservations'),
(2, NOW(), NOW(), 'LIBRARIAN', 'Librarian role - can manage books, transactions, and users'),
(3, NOW(), NOW(), 'ADMIN', 'Administrator role - has full access to all system features');

-- ============================================================
-- ADMIN USER (1 record)
-- Password: Admin@123 (hashed with BCrypt)
-- ============================================================
INSERT INTO users (id, created_at, updated_at, email, full_name, hashed_password, account_status, ai_personalization_enabled, date_of_birth, phone_number, address, last_login_at, profile_picture_url) VALUES
(1, NOW(), NOW(), 'admin@hcmut.edu.vn', 'System Administrator', '$2a$10$N9qo8uLOickgx2ZMRZoMye1L/nMJqLJVPdam6mEZqZQfz3WJQFaXu', 'ACTIVE', true, '1990-01-01', '0000000000', 'System', NULL, NULL);

-- Link admin user to ADMIN role
INSERT INTO user_roles (user_id, role_id) VALUES (1, 3);

-- ============================================================
-- AUTHORS (20 records)
-- ============================================================
INSERT INTO authors (id, created_at, updated_at, author_name, biography, date_of_birth, date_of_death) VALUES
(1, NOW(), NOW(), 'Robert C. Martin', 'American software engineer, instructor, and best-selling author. Known as Uncle Bob.', '1952-12-05', NULL),
(2, NOW(), NOW(), 'Martin Fowler', 'British software developer, author and international public speaker on software development.', '1963-01-01', NULL),
(3, NOW(), NOW(), 'Eric Evans', 'Domain-Driven Design pioneer and software design consultant.', '1960-01-01', NULL),
(4, NOW(), NOW(), 'Kent Beck', 'American software engineer and creator of Extreme Programming and Test Driven Development.', '1961-01-01', NULL),
(5, NOW(), NOW(), 'Joshua Bloch', 'American software engineer and former Chief Java Architect at Google.', '1961-08-28', NULL),
(6, NOW(), NOW(), 'Erich Gamma', 'Swiss computer scientist and co-author of Design Patterns book.', '1961-03-13', NULL),
(7, NOW(), NOW(), 'Donald Knuth', 'American computer scientist, mathematician, and Professor Emeritus at Stanford University.', '1938-01-10', NULL),
(8, NOW(), NOW(), 'Brian Kernighan', 'Canadian computer scientist who worked at Bell Labs alongside Unix creators.', '1942-01-01', NULL),
(9, NOW(), NOW(), 'Dennis Ritchie', 'American computer scientist who created the C programming language.', '1941-09-09', '2011-10-12'),
(10, NOW(), NOW(), 'Bjarne Stroustrup', 'Danish computer scientist, creator of C++ programming language.', '1950-12-30', NULL),
(11, NOW(), NOW(), 'James Gosling', 'Canadian computer scientist, creator of Java programming language.', '1955-05-19', NULL),
(12, NOW(), NOW(), 'Guido van Rossum', 'Dutch programmer, creator of Python programming language.', '1956-01-31', NULL),
(13, NOW(), NOW(), 'Yukihiro Matsumoto', 'Japanese computer scientist, creator of Ruby programming language.', '1965-04-14', NULL),
(14, NOW(), NOW(), 'Andrew Hunt', 'American programmer and author, co-author of The Pragmatic Programmer.', '1964-01-01', NULL),
(15, NOW(), NOW(), 'David Thomas', 'American programmer and author, co-author of The Pragmatic Programmer.', '1956-01-01', NULL),
(16, NOW(), NOW(), 'Steve McConnell', 'American software engineer and author of Code Complete.', '1962-01-01', NULL),
(17, NOW(), NOW(), 'Frederick Brooks', 'American computer architect, software engineer, author of The Mythical Man-Month.', '1931-04-19', '2022-11-17'),
(18, NOW(), NOW(), 'Grady Booch', 'American software engineer, best known for developing the Unified Modeling Language.', '1955-02-27', NULL),
(19, NOW(), NOW(), 'Michael Feathers', 'American software engineer and author of Working Effectively with Legacy Code.', '1970-01-01', NULL),
(20, NOW(), NOW(), 'Vaughn Vernon', 'Software architect and Domain-Driven Design expert.', '1960-01-01', NULL);

-- ============================================================
-- PUBLISHERS (20 records)
-- ============================================================
INSERT INTO publishers (id, created_at, updated_at, publisher_name, address) VALUES
(1, NOW(), NOW(), 'Prentice Hall', 'Upper Saddle River, NJ, USA'),
(2, NOW(), NOW(), 'O''Reilly Media', 'Sebastopol, CA, USA'),
(3, NOW(), NOW(), 'Addison-Wesley', 'Boston, MA, USA'),
(4, NOW(), NOW(), 'MIT Press', 'Cambridge, MA, USA'),
(5, NOW(), NOW(), 'Springer', 'Berlin, Germany'),
(6, NOW(), NOW(), 'Wiley', 'Hoboken, NJ, USA'),
(7, NOW(), NOW(), 'Pearson', 'London, UK'),
(8, NOW(), NOW(), 'Cambridge University Press', 'Cambridge, UK'),
(9, NOW(), NOW(), 'Oxford University Press', 'Oxford, UK'),
(10, NOW(), NOW(), 'McGraw-Hill', 'New York, NY, USA'),
(11, NOW(), NOW(), 'Packt Publishing', 'Birmingham, UK'),
(12, NOW(), NOW(), 'Manning Publications', 'Shelter Island, NY, USA'),
(13, NOW(), NOW(), 'Apress', 'New York, NY, USA'),
(14, NOW(), NOW(), 'No Starch Press', 'San Francisco, CA, USA'),
(15, NOW(), NOW(), 'The Pragmatic Bookshelf', 'Dallas, TX, USA'),
(16, NOW(), NOW(), 'Microsoft Press', 'Redmond, WA, USA'),
(17, NOW(), NOW(), 'Morgan Kaufmann', 'Burlington, MA, USA'),
(18, NOW(), NOW(), 'Academic Press', 'Cambridge, MA, USA'),
(19, NOW(), NOW(), 'Wrox Press', 'Indianapolis, IN, USA'),
(20, NOW(), NOW(), 'Sams Publishing', 'Indianapolis, IN, USA');

-- ============================================================
-- CATEGORIES (20 records)
-- ============================================================
INSERT INTO categories (id, created_at, updated_at, category_name, parent_category_id) VALUES
(1, NOW(), NOW(), 'Software Engineering', NULL),
(2, NOW(), NOW(), 'Programming Languages', NULL),
(3, NOW(), NOW(), 'Database Systems', NULL),
(4, NOW(), NOW(), 'Computer Networks', NULL),
(5, NOW(), NOW(), 'Operating Systems', NULL),
(6, NOW(), NOW(), 'Algorithms', NULL),
(7, NOW(), NOW(), 'Software Architecture', NULL),
(8, NOW(), NOW(), 'Web Development', NULL),
(9, NOW(), NOW(), 'Mobile Development', NULL),
(10, NOW(), NOW(), 'Artificial Intelligence', NULL),
(11, NOW(), NOW(), 'Data Science', NULL),
(12, NOW(), NOW(), 'Cybersecurity', NULL),
(13, NOW(), NOW(), 'Cloud Computing', NULL),
(14, NOW(), NOW(), 'DevOps', NULL),
(15, NOW(), NOW(), 'Testing', NULL),
(16, NOW(), NOW(), 'Agile', NULL),
(17, NOW(), NOW(), 'Design Patterns', NULL),
(18, NOW(), NOW(), 'Computer Graphics', NULL),
(19, NOW(), NOW(), 'Theory of Computation', NULL),
(20, NOW(), NOW(), 'Human-Computer Interaction', NULL);

-- ============================================================
-- TAGS (20 records)
-- ============================================================
INSERT INTO tags (id, created_at, updated_at, tag_name) VALUES
(1, NOW(), NOW(), 'Clean Code'),
(2, NOW(), NOW(), 'Best Practices'),
(3, NOW(), NOW(), 'Beginner Friendly'),
(4, NOW(), NOW(), 'Advanced'),
(5, NOW(), NOW(), 'Reference'),
(6, NOW(), NOW(), 'Tutorial'),
(7, NOW(), NOW(), 'Classic'),
(8, NOW(), NOW(), 'Modern'),
(9, NOW(), NOW(), 'Practical'),
(10, NOW(), NOW(), 'Theoretical'),
(11, NOW(), NOW(), 'Enterprise'),
(12, NOW(), NOW(), 'Open Source'),
(13, NOW(), NOW(), 'Hands-on'),
(14, NOW(), NOW(), 'Case Study'),
(15, NOW(), NOW(), 'Industry Standard'),
(16, NOW(), NOW(), 'Award Winner'),
(17, NOW(), NOW(), 'Must Read'),
(18, NOW(), NOW(), 'Updated Edition'),
(19, NOW(), NOW(), 'Comprehensive'),
(20, NOW(), NOW(), 'Quick Reference');

-- ============================================================
-- PUBLICATIONS (20 records)
-- ============================================================
INSERT INTO publications (id, created_at, updated_at, title, isbn, description, publication_year, language, number_of_pages, size, weight, publisher_id) VALUES
(1, NOW(), NOW(), 'Clean Code', '9780132350884', 'A Handbook of Agile Software Craftsmanship', 2008, 'English', 464, '23x15x3 cm', 700.0, 1),
(2, NOW(), NOW(), 'The Pragmatic Programmer', '9780135957059', 'Your Journey to Mastery', 2019, 'English', 352, '23x15x2.5 cm', 650.0, 3),
(3, NOW(), NOW(), 'Design Patterns', '9780201633610', 'Elements of Reusable Object-Oriented Software', 1994, 'English', 416, '24x16x3 cm', 800.0, 3),
(4, NOW(), NOW(), 'Domain-Driven Design', '9780321125217', 'Tackling Complexity in the Heart of Software', 2003, 'English', 560, '24x17x3.5 cm', 900.0, 3),
(5, NOW(), NOW(), 'Refactoring', '9780134757599', 'Improving the Design of Existing Code', 2018, 'English', 448, '23x15x3 cm', 750.0, 3),
(6, NOW(), NOW(), 'Code Complete', '9780735619678', 'A Practical Handbook of Software Construction', 2004, 'English', 960, '24x18x5 cm', 1200.0, 16),
(7, NOW(), NOW(), 'The Mythical Man-Month', '9780201835953', 'Essays on Software Engineering', 1995, 'English', 336, '23x15x2 cm', 600.0, 3),
(8, NOW(), NOW(), 'Introduction to Algorithms', '9780262033848', 'Third Edition', 2009, 'English', 1312, '26x20x6 cm', 1800.0, 4),
(9, NOW(), NOW(), 'The C Programming Language', '9780131103627', 'Second Edition', 1988, 'English', 272, '23x15x2 cm', 500.0, 1),
(10, NOW(), NOW(), 'Effective Java', '9780134685991', 'Third Edition', 2017, 'English', 416, '23x15x3 cm', 700.0, 3),
(11, NOW(), NOW(), 'Head First Design Patterns', '9780596007126', 'Building Extensible and Maintainable Object-Oriented Software', 2004, 'English', 694, '24x20x4 cm', 1000.0, 2),
(12, NOW(), NOW(), 'Clean Architecture', '9780134494166', 'A Craftsman''s Guide to Software Structure and Design', 2017, 'English', 432, '23x15x3 cm', 750.0, 1),
(13, NOW(), NOW(), 'Working Effectively with Legacy Code', '9780131177055', 'Practical techniques for improving existing code', 2004, 'English', 464, '23x15x3 cm', 750.0, 1),
(14, NOW(), NOW(), 'Implementing Domain-Driven Design', '9780321834577', 'Practical DDD in enterprise', 2013, 'English', 656, '24x17x4 cm', 1000.0, 3),
(15, NOW(), NOW(), 'Test Driven Development', '9780321146533', 'By Example', 2002, 'English', 240, '23x15x2 cm', 450.0, 3),
(16, NOW(), NOW(), 'Continuous Delivery', '9780321601919', 'Reliable Software Releases through Build, Test, and Deployment Automation', 2010, 'English', 512, '24x16x3 cm', 850.0, 3),
(17, NOW(), NOW(), 'The Art of Computer Programming Vol 1', '9780201896831', 'Fundamental Algorithms', 1997, 'English', 672, '24x16x4 cm', 1100.0, 3),
(18, NOW(), NOW(), 'Structure and Interpretation of Computer Programs', '9780262510875', 'Second Edition', 1996, 'English', 657, '24x17x4 cm', 1000.0, 4),
(19, NOW(), NOW(), 'Database System Concepts', '9780078022159', 'Seventh Edition', 2019, 'English', 1376, '26x20x6 cm', 2000.0, 10),
(20, NOW(), NOW(), 'Computer Networks', '9780132126953', 'Fifth Edition', 2010, 'English', 960, '24x18x5 cm', 1400.0, 7);
-- ============================================================
-- PUBLICATION_AUTHORS (Link publications to authors)
-- ============================================================
INSERT INTO publication_authors (publication_id, author_id) VALUES
(1, 1),   -- Clean Code -> Robert C. Martin
(2, 14),  -- The Pragmatic Programmer -> Andrew Hunt
(2, 15),  -- The Pragmatic Programmer -> David Thomas
(3, 6),   -- Design Patterns -> Erich Gamma
(4, 3),   -- Domain-Driven Design -> Eric Evans
(5, 2),   -- Refactoring -> Martin Fowler
(6, 16),  -- Code Complete -> Steve McConnell
(7, 17),  -- The Mythical Man-Month -> Frederick Brooks
(8, 7),   -- Introduction to Algorithms -> Donald Knuth
(9, 8),   -- The C Programming Language -> Brian Kernighan
(9, 9),   -- The C Programming Language -> Dennis Ritchie
(10, 5),  -- Effective Java -> Joshua Bloch
(11, 6),  -- Head First Design Patterns -> Erich Gamma
(12, 1),  -- Clean Architecture -> Robert C. Martin
(13, 19), -- Working Effectively with Legacy Code -> Michael Feathers
(14, 20), -- Implementing Domain-Driven Design -> Vaughn Vernon
(15, 4),  -- Test Driven Development -> Kent Beck
(16, 2),  -- Continuous Delivery -> Martin Fowler
(17, 7),  -- The Art of Computer Programming -> Donald Knuth
(18, 4),  -- Structure and Interpretation -> Kent Beck
(19, 18), -- Database System Concepts -> Grady Booch
(20, 7);  -- Computer Networks -> Donald Knuth

-- ============================================================
-- PUBLICATION_CATEGORIES (Link publications to categories)
-- ============================================================
INSERT INTO publication_categories (publication_id, category_id) VALUES
(1, 1),   -- Clean Code -> Software Engineering
(2, 1),   -- The Pragmatic Programmer -> Software Engineering
(3, 17),  -- Design Patterns -> Design Patterns
(4, 7),   -- Domain-Driven Design -> Software Architecture
(5, 1),   -- Refactoring -> Software Engineering
(6, 1),   -- Code Complete -> Software Engineering
(7, 1),   -- The Mythical Man-Month -> Software Engineering
(8, 6),   -- Introduction to Algorithms -> Algorithms
(9, 2),   -- The C Programming Language -> Programming Languages
(10, 2),  -- Effective Java -> Programming Languages
(11, 17), -- Head First Design Patterns -> Design Patterns
(12, 7),  -- Clean Architecture -> Software Architecture
(13, 1),  -- Working Effectively with Legacy Code -> Software Engineering
(14, 7),  -- Implementing Domain-Driven Design -> Software Architecture
(15, 15), -- Test Driven Development -> Testing
(16, 14), -- Continuous Delivery -> DevOps
(17, 6),  -- The Art of Computer Programming -> Algorithms
(18, 2),  -- Structure and Interpretation -> Programming Languages
(19, 3),  -- Database System Concepts -> Database Systems
(20, 4);  -- Computer Networks -> Computer Networks

-- ============================================================
-- PUBLICATION_TAGS (Link publications to tags)
-- ============================================================
INSERT INTO publication_tags (publication_id, tag_id) VALUES
(1, 1),   -- Clean Code -> Clean Code
(1, 2),   -- Clean Code -> Best Practices
(2, 2),   -- The Pragmatic Programmer -> Best Practices
(2, 3),   -- The Pragmatic Programmer -> Beginner Friendly
(3, 7),   -- Design Patterns -> Classic
(3, 17),  -- Design Patterns -> Must Read
(4, 9),   -- Domain-Driven Design -> Practical
(4, 10),  -- Domain-Driven Design -> Theoretical
(5, 1),   -- Refactoring -> Clean Code
(5, 2),   -- Refactoring -> Best Practices
(6, 5),   -- Code Complete -> Reference
(6, 19),  -- Code Complete -> Comprehensive
(7, 7),   -- The Mythical Man-Month -> Classic
(7, 14),  -- The Mythical Man-Month -> Case Study
(8, 5),   -- Introduction to Algorithms -> Reference
(8, 19),  -- Introduction to Algorithms -> Comprehensive
(9, 7),   -- The C Programming Language -> Classic
(9, 15),  -- The C Programming Language -> Industry Standard
(10, 2),  -- Effective Java -> Best Practices
(10, 5),  -- Effective Java -> Reference
(11, 3),  -- Head First Design Patterns -> Beginner Friendly
(11, 9),  -- Head First Design Patterns -> Practical
(12, 1),  -- Clean Architecture -> Clean Code
(12, 10), -- Clean Architecture -> Theoretical
(13, 1),  -- Working Effectively with Legacy Code -> Clean Code
(13, 9),  -- Working Effectively with Legacy Code -> Practical
(14, 10), -- Implementing Domain-Driven Design -> Theoretical
(14, 11), -- Implementing Domain-Driven Design -> Enterprise
(15, 2),  -- Test Driven Development -> Best Practices
(15, 13), -- Test Driven Development -> Hands-on
(16, 2),  -- Continuous Delivery -> Best Practices
(16, 13), -- Continuous Delivery -> Hands-on
(17, 5),  -- The Art of Computer Programming -> Reference
(17, 19), -- The Art of Computer Programming -> Comprehensive
(18, 19), -- Structure and Interpretation -> Comprehensive
(18, 3),  -- Structure and Interpretation -> Beginner Friendly
(19, 5),  -- Database System Concepts -> Reference
(19, 15), -- Database System Concepts -> Industry Standard
(20, 5),  -- Computer Networks -> Reference
(20, 15); -- Computer Networks -> Industry Standard

-- ============================================================
-- ITEMS (20 records - 1 per publication)
-- ============================================================
INSERT INTO items (id, created_at, updated_at, barcode, publication_id, acquired_date, item_type, status, location) VALUES
(1, NOW(), NOW(), 'BK000101', 1, CURRENT_DATE - INTERVAL '120 days', 'PAPERBACK', 'AVAILABLE', 'Shelf A-01'),
(2, NOW(), NOW(), 'BK000201', 2, CURRENT_DATE - INTERVAL '90 days', 'PAPERBACK', 'AVAILABLE', 'Shelf A-02'),
(3, NOW(), NOW(), 'BK000301', 3, CURRENT_DATE - INTERVAL '200 days', 'HARDCOVER', 'AVAILABLE', 'Shelf A-03'),
(4, NOW(), NOW(), 'BK000401', 4, CURRENT_DATE - INTERVAL '150 days', 'HARDCOVER', 'AVAILABLE', 'Shelf A-04'),
(5, NOW(), NOW(), 'BK000501', 5, CURRENT_DATE - INTERVAL '60 days', 'PAPERBACK', 'AVAILABLE', 'Shelf A-05'),
(6, NOW(), NOW(), 'BK000601', 6, CURRENT_DATE - INTERVAL '180 days', 'HARDCOVER', 'AVAILABLE', 'Shelf B-01'),
(7, NOW(), NOW(), 'BK000701', 7, CURRENT_DATE - INTERVAL '240 days', 'PAPERBACK', 'AVAILABLE', 'Shelf B-02'),
(8, NOW(), NOW(), 'BK000801', 8, CURRENT_DATE - INTERVAL '100 days', 'HARDCOVER', 'AVAILABLE', 'Shelf B-03'),
(9, NOW(), NOW(), 'BK000901', 9, CURRENT_DATE - INTERVAL '300 days', 'PAPERBACK', 'AVAILABLE', 'Shelf B-04'),
(10, NOW(), NOW(), 'BK001001', 10, CURRENT_DATE - INTERVAL '50 days', 'PAPERBACK', 'AVAILABLE', 'Shelf B-05'),
(11, NOW(), NOW(), 'BK001101', 11, CURRENT_DATE - INTERVAL '140 days', 'PAPERBACK', 'AVAILABLE', 'Shelf C-01'),
(12, NOW(), NOW(), 'BK001201', 12, CURRENT_DATE - INTERVAL '70 days', 'PAPERBACK', 'AVAILABLE', 'Shelf C-02'),
(13, NOW(), NOW(), 'BK001301', 13, CURRENT_DATE - INTERVAL '160 days', 'PAPERBACK', 'AVAILABLE', 'Shelf C-03'),
(14, NOW(), NOW(), 'BK001401', 14, CURRENT_DATE - INTERVAL '110 days', 'HARDCOVER', 'AVAILABLE', 'Shelf C-04'),
(15, NOW(), NOW(), 'BK001501', 15, CURRENT_DATE - INTERVAL '220 days', 'PAPERBACK', 'AVAILABLE', 'Shelf C-05'),
(16, NOW(), NOW(), 'BK001601', 16, CURRENT_DATE - INTERVAL '130 days', 'PAPERBACK', 'AVAILABLE', 'Shelf D-01'),
(17, NOW(), NOW(), 'BK001701', 17, CURRENT_DATE - INTERVAL '280 days', 'HARDCOVER', 'AVAILABLE', 'Shelf D-02'),
(18, NOW(), NOW(), 'BK001801', 18, CURRENT_DATE - INTERVAL '250 days', 'PAPERBACK', 'AVAILABLE', 'Shelf D-03'),
(19, NOW(), NOW(), 'BK001901', 19, CURRENT_DATE - INTERVAL '80 days', 'HARDCOVER', 'AVAILABLE', 'Shelf D-04'),
(20, NOW(), NOW(), 'BK002001', 20, CURRENT_DATE - INTERVAL '170 days', 'HARDCOVER', 'AVAILABLE', 'Shelf D-05');
