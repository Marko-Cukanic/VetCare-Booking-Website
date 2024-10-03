INSERT INTO User (fullname, email, password)
VALUES ('Admin User', 'admin@vetcare.com', 'admin')
ON DUPLICATE KEY UPDATE email = email;