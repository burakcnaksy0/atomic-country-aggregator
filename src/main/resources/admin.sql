INSERT INTO users (name, surname, username, email, password, user_role, created_at, updated_at)
SELECT 'Admin', 'User', 'admin', 'admin@id3.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ROLE_ADMIN', GETDATE(), GETDATE()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin'); 