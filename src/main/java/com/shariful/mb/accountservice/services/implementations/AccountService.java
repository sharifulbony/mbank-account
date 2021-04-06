package com.shariful.mb.accountservice.services.implementations;
import com.shariful.mb.accountservice.entities.dbentities.Account;
import com.shariful.mb.accountservice.entities.dbentities.Customer;
import com.shariful.mb.accountservice.repositories.AccountRepository;
import com.shariful.mb.accountservice.services.interfaces.AccountInterface;
import com.shariful.mb.accountservice.services.interfaces.BalanceInterface;
import com.shariful.mb.accountservice.utilities.config.Constant;
import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

@AllArgsConstructor
@Service
public class AccountService implements AccountInterface {
    private final AccountRepository accountRepository;
    private final BalanceInterface balanceInterface;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountDto findByAccountId(Long accountId) throws IllegalTransactionStateException {
        Account account = accountRepository.findByAccountId(accountId);
        if (account == null)
            throw new IllegalTransactionStateException(Constant.ACCOUNT_NOT_FOUND);
        return modelMapper.map(account,AccountDto.class);
    }

    public static Account toAccountFromCustomer(Customer customer) {
        return Account.builder()
                .customer(customer)
                .build();
    }

    public static Account toAccount(AccountDto account) {
        return Account.builder()
                .accountId(account.getAccountId())
                .build();
    }
}
