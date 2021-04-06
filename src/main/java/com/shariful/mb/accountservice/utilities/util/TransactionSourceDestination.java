package com.shariful.mb.accountservice.utilities.util;

public enum TransactionSourceDestination {
    IN, OUT;

    public static boolean contains(String incomingDirectionOfTransaction) {
        for (TransactionSourceDestination transactionSourceDestination : TransactionSourceDestination.values()) {
            if (transactionSourceDestination.name().equals(incomingDirectionOfTransaction)) {
                return true;
            }
        }
        return false;
    }
}
