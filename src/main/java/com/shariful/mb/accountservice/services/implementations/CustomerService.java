package com.shariful.mb.accountservice.services.implementations;

import com.shariful.mb.accountservice.entities.dbentities.Account;
import com.shariful.mb.accountservice.entities.dbentities.Balance;
import com.shariful.mb.accountservice.entities.dbentities.Customer;
import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import com.shariful.mb.accountservice.entities.dtos.BalanceDto;
import com.shariful.mb.accountservice.repositories.CustomerRepository;
import com.shariful.mb.accountservice.services.interfaces.AccountInterface;
import com.shariful.mb.accountservice.services.interfaces.BalanceInterface;
import com.shariful.mb.accountservice.services.interfaces.CustomerInterface;
import com.shariful.mb.accountservice.services.interfaces.MessageQueueInterface;
import com.shariful.mb.accountservice.entities.dtos.CustomerDto;
import com.shariful.mb.accountservice.entities.rabbitmq.AccountMap;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService implements CustomerInterface {
    private final CustomerRepository customerRepository;
    private final AccountInterface accountInterface;
    private final BalanceInterface balanceInterface;
    private final MessageQueueInterface messageQueueService;

    @Autowired
    ModelMapper modelMapper;

    public static Customer toCustomer(CustomerDto customer) {
        return Customer.builder()
                .customerId(customer.getCustomerId())
                .country(customer.getCountry())
                .build();
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .country(customer.getCountry())
                .build();
    }

    @Transactional
    @Override
    public AccountDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.save(toCustomer(customerDto));

        Account account = accountInterface.
                createAccount(AccountService.toAccountFromCustomer(customer));

        List<Balance> balances = balanceInterface.saveBalance(
                BalanceService.toBalancesFromCurrencies(
                        customerDto.getCurrencies(),
                        account));

        List<BalanceDto> balanceDtos = new ArrayList<>();
        for (Balance balance : balances
        ) {
            BalanceDto balanceDto = modelMapper.map(balance, BalanceDto.class);
            balanceDtos.add(balanceDto);
        }
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        messageQueueService.publishCreateAccount(
                AccountMap.builder()
                        .customer(toCustomerDto(customer))
                        .account(accountDto)
                        .balances(BalanceService.toBalanceDtoList(balances, account.getAccountId()))
                        .build()
        );
        accountDto.setBalances(balanceDtos);
        return accountDto;
    }
}
