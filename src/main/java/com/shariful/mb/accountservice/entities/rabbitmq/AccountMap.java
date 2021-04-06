package com.shariful.mb.accountservice.entities.rabbitmq;

import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import com.shariful.mb.accountservice.entities.dtos.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountMap {
    private CustomerDto customer;
    private AccountDto account;
    private List<BalanceMap> balances;
}
