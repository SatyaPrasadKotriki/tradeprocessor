DROP TABLE IF EXISTS trade;

CREATE TABLE trade (
  tradeid VARCHAR(10)  PRIMARY KEY,
  version INT NOT NULL,
  counterPartyId VARCHAR(10) NOT NULL,
  bookId VARCHAR(10) NOT NULL,
  maturityDate DATE,
  createdTime TIMESTAMP,
  isExpired CHAR
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  userId VARCHAR(50)  PRIMARY KEY,
  fromAddress VARCHAR(40) NOT NULL
);

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
  transactionId INT  PRIMARY KEY,
  fromAddress VARCHAR(40) NOT NULL,
  toAddress VARCHAR(40) NOT NULL,
  amount DOUBLE NOT NULL,
  note VARCHAR(40) NOT NULL,
  userID VARCHAR(50) NOT NULL,
  status VARCHAR(20) NOT NULL
);