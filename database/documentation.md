# Users Table

## id
- **Data Type**: INT
- **Constraints**: PRIMARY KEY, IDENTITY(1,1), NOT NULL
- **Description**: Unique identifier for each user.
- **Reason**: The IDENTITY constraint ensures each user has a unique ID.

## username
- **Data Type**: VARCHAR(16)
- **Constraints**: UNIQUE, NOT NULL
- **Description**: The username of the user.
- **Reason**: Limited to 16 characters for short usernames, only unique usernames to prevent duplicate accounts.
- **Checks**:
  - `CK_username_length`: Ensures the length of the username is between 3 and 16 characters.
  - `CK_username_characters`: Ensures the username contains only alphanumeric characters.

## password
- **Data Type**: VARCHAR(60)
- **Constraints**: NOT NULL
- **Description**: The user's password, stored as a BCrypt hash.
- **Reason**: Limited to 60 characters because BCrypt hashes are a maximum of 60 characters. (Based on self-conducted tests)

---

# Claims Table

## id
- **Data Type**: INT
- **Constraints**: PRIMARY KEY, IDENTITY(1,1), NOT NULL
- **Description**: Unique identifier for each claim.
- **Reason**: The IDENTITY constraint ensures each claim has a unique ID.

## user_id
- **Data Type**: INT
- **Constraints**: FOREIGN KEY, NOT NULL
- **Description**: Refers to the user who submitted the claim.
- **Reason**: Ensures each claim is associated with an existing user.
- **Checks**:
  - `FK_Claim_User`: Ensures `user_id` refers to an existing user in the Users table.

## title
- **Data Type**: VARCHAR(10)
- **Constraints**: NOT NULL
- **Description**: Title of the claim.
- **Reason**: Limited to 10 characters to encourage concise descriptions.
- **Checks**:
  - `CK_title_length`: Ensures the length of the title is between 3 and 10 characters.

## description
- **Data Type**: VARCHAR(50)
- **Constraints**: NOT NULL
- **Description**: Detailed description of the claim.
- **Reason**: 50 characters provide enough space for clarity while remaining manageable.
- **Checks**:
  - `CK_description_length`: Ensures the length of the description is between 3 and 50 characters.

## amount
- **Data Type**: SMALLMONEY
- **Constraints**: NOT NULL
- **Description**: Monetary value of the claim.
- **Reason**: Based on the case description, amounts under 10,000 are expected. SMALLMONEY was chosen for more efficient storage.
- **Checks**:
  - `CK_amount_limit`: Ensures the amount is greater than 0 and less than or equal to 10,000.
