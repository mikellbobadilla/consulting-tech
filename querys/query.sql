DROP DATABASE consultoria;

CREATE DATABASE consultoria;

-- Add Users --
INSERT INTO app_users(email, name, password, user_role, username)
VALUES('bobadilla@gmail.com', 'sting', '$2a$10$HIRLoCghJdosHGPHJdbWa.XK9DsHLepyqNCgZYbqFEl086AE7UAr6', 'USER', 'nakamura');