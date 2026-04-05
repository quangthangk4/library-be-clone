-- add column credit_score in users table
ALTER TABLE users ADD COLUMN credit_score int NOT NULL DEFAULT 100;


-- publication
ALTER TABLE publications ADD COLUMN ai_summary TEXT;
ALTER TABLE publications ADD COLUMN ai_target_audience VARCHAR(100);
ALTER TABLE publications ADD COLUMN file_url VARCHAR(255);




