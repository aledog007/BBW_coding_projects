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
    PRIMARY KEY (id)
);

CREATE TABLE secret (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    content LONGTEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `password`, `salt`, `reset_token`, `reset_token_expiry`) VALUES
(1, 'Hans', 'Muster', 'hans.muster@bbw.ch', 'abcd', 'S0FNRUxfU0FMVF8xMjM=', NULL, NULL),
(2, 'Paula', 'Kuster', 'paula.kuster@bbw.ch', 'efgh', 'U0FMVF9QQVVMQV80NTY=', NULL, NULL),
(3, 'Andrea', 'Oester', 'andrea.oester@bbw.ch', 'ijkl', 'QU5EUkVBX1NBTFRfNzg5', NULL, NULL);

