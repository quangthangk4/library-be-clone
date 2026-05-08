-- Cho phép nhiều fine trên cùng 1 transaction (ví dụ: vừa trễ hạn vừa hư/mất sách)
ALTER TABLE fines DROP CONSTRAINT IF EXISTS uk7t1elqs9tdjr1yfed9yobxnpf;
