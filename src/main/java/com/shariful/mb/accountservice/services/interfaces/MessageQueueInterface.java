package com.shariful.mb.accountservice.services.interfaces;

import com.shariful.mb.accountservice.entities.rabbitmq.AccountMap;
import com.shariful.mb.accountservice.entities.rabbitmq.TransactionMap;

public interface MessageQueueInterface {
    void publishCreateAccount(AccountMap accountMap);

    void publishCreateTransaction(TransactionMap transactionMap);
}
