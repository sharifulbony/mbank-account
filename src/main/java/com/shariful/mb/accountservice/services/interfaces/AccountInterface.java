package com.shariful.mb.accountservice.services.interfaces;

 import com.shariful.mb.accountservice.entities.dbentities.Account;
 import com.shariful.mb.accountservice.entities.dtos.AccountDto;
 import org.springframework.transaction.IllegalTransactionStateException;

public interface AccountInterface {
    Account createAccount(Account account);
    AccountDto findByAccountId(Long accountId) throws IllegalTransactionStateException;
}
