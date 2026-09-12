DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

CREATE TABLE account (
    id INT PRIMARY KEY,
    customer_id INT,
    type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE transaction (
    id INT PRIMARY KEY,
    account_id INT,
    type VARCHAR(20),
    amount DECIMAL(10,2),
    description VARCHAR(100),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

INSERT INTO customer VALUES (1, 'John', 'Unyime');

INSERT INTO account VALUES (12345, 1, 'CHECKING', 1000.00);
INSERT INTO account VALUES (12346, 1, 'SAVINGS', 500.00);

INSERT INTO transaction VALUES (1, 12345, 'Debit', 50.00, 'Transfer to Savings');
INSERT INTO transaction VALUES (2, 12346, 'Credit', 50.00, 'Transfer from Checking');