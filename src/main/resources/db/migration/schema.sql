-- Customer Table
CREATE SEQUENCE seq_customer_id;
CREATE TABLE customer
(
    customer_id bigint NOT NULL
        default nextval('seq_customer_id') primary key,
    country varchar(255) not null,
    CONSTRAINT customer_pkey UNIQUE (customer_id)
);
ALTER SEQUENCE seq_customer_id
    owned by customer.customer_id;

CREATE UNIQUE INDEX customer_idx ON customer (customer_id);

-- Account Table
CREATE SEQUENCE seq_account_id;
CREATE TABLE account
(
    account_id bigint NOT NULL
        default NEXTVAL('seq_account_id') primary key ,
    customer_id bigint,
    CONSTRAINT account_pkey_unique UNIQUE (account_id),
    CONSTRAINT fk_customer
        FOREIGN KEY(customer_id)
            REFERENCES customer(customer_id)
);
ALTER SEQUENCE seq_account_id
    owned by account.account_id;

CREATE UNIQUE INDEX account_idx ON account (account_id);

-- Balance Table
CREATE SEQUENCE seq_balance_id;
CREATE TABLE balance
(
    balance_id bigint NOT NULL
        default NEXTVAL('seq_balance_id') primary key ,
    account_id bigint,
    current_balance numeric(19,2) NOT NULL,
    currency varchar(255) NOT NULL ,
    CONSTRAINT balance_pkey_unique UNIQUE (balance_id, currency),
    CONSTRAINT fk_account
        FOREIGN KEY(account_id)
        REFERENCES account(account_id)
);
ALTER SEQUENCE seq_balance_id
    owned by balance.balance_id;

CREATE UNIQUE INDEX balance_idx ON balance (account_id, currency);

-- Transaction Table
CREATE SEQUENCE seq_transaction_id;
CREATE TABLE transaction
(
    transaction_id bigint NOT NULL
        default nextval('seq_transaction_id') primary key,
    account_id bigint,
    amount numeric(19,2) NOT NULL ,
    currency varchar(255) NOT NULL ,
    description varchar(255) NOT NULL ,
    direction_of_transaction varchar(255) NOT NULL ,
    CONSTRAINT transaction_pkey UNIQUE (transaction_id),
    CONSTRAINT fk_account
        FOREIGN KEY(account_id)
            REFERENCES account(account_id)
);
ALTER SEQUENCE seq_transaction_id
    owned by transaction.transaction_id;

CREATE INDEX transaction_idx ON transaction (account_id);
