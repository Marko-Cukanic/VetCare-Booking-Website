INSERT INTO user (fullname, email, password)
VALUES ('Admin User', 'admin@vetcare.com', 'admin')
ON DUPLICATE KEY UPDATE email = email;