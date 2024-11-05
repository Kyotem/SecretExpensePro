/*
TravelExpensePro DB & Table Creation script
FSWD 2024 - Herkansing Finn Panhuijsen
Laatst bijgewerkt: 05/11/2024   
*/

-- Create DB
USE master
GO

CREATE DATABASE TravelExpensePro
GO

USE TravelExpensePro
GO

-- Create Tables
CREATE TABLE Users
(
	id INT IDENTITY(1,1) NOT NULL,
	username VARCHAR(16) NOT NULL UNIQUE,
	password VARCHAR(60) NOT NULL,
	CONSTRAINT PK_User PRIMARY KEY (id),
	CONSTRAINT CK_username_length CHECK (LEN(username) >= 3 AND LEN(username) <= 16),
	CONSTRAINT CK_username_characters CHECK (username NOT LIKE '%[^a-zA-Z0-9]%')
)


CREATE TABLE Claims
(
    id INT IDENTITY(1,1) NOT NULL,	
    user_id INT NOT NULL,
    title VARCHAR(10) NOT NULL,
    description VARCHAR(50) NOT NULL,
    amount SMALLMONEY NOT NULL,
    CONSTRAINT PK_Claim PRIMARY KEY (id),
    CONSTRAINT FK_Claim_User FOREIGN KEY (user_id) REFERENCES Users(id),
    CONSTRAINT CK_title_length CHECK (LEN(title) >= 3 AND LEN(title) <= 10),
    CONSTRAINT CK_description_length CHECK (LEN(description) >= 3 AND LEN(description) <= 50),
    CONSTRAINT CK_amount_limit CHECK (amount > 0 AND amount <= 10000)
);

-- Run population scripts below this point
