DROP TABLE IF EXISTS Cryptos, Transactions, Users;

CREATE TABLE Cryptos (
    crypto_id INT AUTO_INCREMENT PRIMARY KEY,
    crypto_name VARCHAR(255),
    crypto_ticker VARCHAR(255),
    bid_price DECIMAL(38, 20),
    ask_price DECIMAL(38, 20)
);

INSERT INTO Cryptos (crypto_name, crypto_ticker, bid_price, ask_price)
VALUES
    ('Ethereum', 'ETH', 0, 0),
    ('Bitcoin', 'BTC', 0, 0);

CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    transaction_date DATE,
    transaction_type CHAR(4),
    crypto_id INT,
    units VARCHAR(50),
    unit_price DECIMAL(38, 20)
);

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    wallet_balance DECIMAL(38, 20)
);

INSERT INTO Users (username, first_name, last_name, wallet_balance)
VALUES
    ('investor1', 'John', 'Doe', 50000),
    ('investor2', 'Bob', 'Lim', 50000),;