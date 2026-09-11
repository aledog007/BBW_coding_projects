DROP DATABASE IF EXISTS tresordb;
CREATE DATABASE tresordb;
USE tresordb;

CREATE USER IF NOT EXISTS 'tresoruser'@'%' IDENTIFIED BY 'tresorpassword';
GRANT ALL PRIVILEGES ON tresordb.* TO 'tresoruser'@'%';
FLUSH PRIVILEGES;

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password LONGTEXT NOT NULL,
    salt VARCHAR(255),
    reset_token VARCHAR(255),
    reset_token_expiry DATETIME,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    totp_secret VARCHAR(255) DEFAULT NULL,
    two_factor_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    failed_login_attempts INT NOT NULL DEFAULT 0,
    account_locked_until DATETIME DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE secret (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    content LONGTEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);
