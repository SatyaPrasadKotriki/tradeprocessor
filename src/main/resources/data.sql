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