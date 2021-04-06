package com.shariful.mb.accountservice.utilities.config;


import java.util.Arrays;
import java.util.List;

public class Constant {

    private Constant() {
    }

    public static final String ACCOUNT_NOT_FOUND = "Account does not exists with provided account id.";
    public static final String INVALID_CURRENCY = "Invalid currency";
    public static final String INVALID_DIRECTION = "Invalid direction";
    public static final String INVALID_AMOUNT = "Invalid amount";
    public static final String INSUFFICIENT_FUNDS = "Insufficient amount";
    public static final String DESCRIPTION_MISSING = "Description Missing";
    public static final String NULL_ERROR_MESSAGE=" can not be null";
    public static final List<String> CURRENCY_CODE_LIST = Arrays.asList(
            "EUR", "SEK", "BP", "USD"
    );

//  Rabbit key
    public static final String ACCOUNT_KEY="account_key";
    public static final String TRANSACTION_KEY="transaction_key";
    public static final String ACCOUNT_ROUTING_KEY="account_routing_key";
    public static final String ACCOUNT_TRANSACTION_KEY="account_transaction_key";
    public static final String TOPIC_KEY="topic_key";

}
