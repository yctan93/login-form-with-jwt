DROP DATABASE IF EXISTS users_db;

CREATE DATABASE users_db;

USE users_db;

CREATE TABLE app_users(
	id int PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	dob DATETIME NOT NULL,
	address VARCHAR(50),
	CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
	UPDATED_DATE DATETIME ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles(
	id int PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
);

INSERT INTO app_users (username, password, firstname, lastname, email, dob) VALUES ('yctan', '123456', 'Tan', 'YC', 'yc@gmail.com', '2000-01-01');
INSERT INTO app_users (username, password, firstname, lastname, email, dob) VALUES ('mariotan', '224455', 'Tan', 'Mario', 'mario@gmail.com', '2001-02-02');
INSERT INTO app_users (username, password, firstname, lastname, email, dob) VALUES ('luigitan', '335566','Tan', 'Luigi', 'luigi@gmail.com', '2002-03-03');
INSERT INTO app_users (username, password, firstname, lastname, email, dob) VALUES ('yoshitan', '654321', 'Tan', 'Yoshi', 'yoshi@gmail.com', '2003-04-04');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
