package com.shariful.mb.accountservice.services.implementations;

import com.shariful.mb.accountservice.entities.dbentities.Account;
import com.shariful.mb.accountservice.entities.dbentities.Balance;
import com.shariful.mb.accountservice.entities.dtos.BalanceDto;
import com.shariful.mb.accountservice.entities.rabbitmq.BalanceMap;
import com.shariful.mb.accountservice.repositories.BalanceRepository;
import com.shariful.mb.accountservice.services.interfaces.BalanceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BalanceService implements BalanceInterface {
    private final BalanceRepository balanceRepository;

    public static List<Balance> toBalancesFromCurrencies(List<String> currencies, Account account) {
        List<Balance> balances = new ArrayList<>();
        currencies.stream()
                .map(currency -> Balance.builder()
                        .account(account)
                        .currency(currency)
                        .currentBalance(BigDecimal.ZERO)
                        .build())
                .forEach(
                        balances::add
                );
        return balances;

    }

    public static BalanceDto toBalanceDto(Balance balance) {

        return BalanceDto.builder()
                .currentBalance(balance.getCurrentBalance())
                .currency(balance.getCurrency())
                .build();
    }

    public static List<BalanceMap> toBalanceDtoList(List<Balance> balance, Long accountId) {
        return balance.stream()
                .map(eachBalance -> toBalanceMQDto(eachBalance, accountId))
                .collect(Collectors.toList());
    }

    public static List<BalanceDto> toBalanceDtoList(List<Balance> balance) {
        return balance.stream()
                .map(BalanceService::toBalanceDto)
                .collect(Collectors.toList());
    }

    public static BalanceMap toBalanceMQDto(Balance balance, Long accountId) {
        return BalanceMap.builder()
                .accountId(accountId)
                .balanceId(balance.getBalanceId())
                .balance(balance.getCurrentBalance())
                .currency(balance.getCurrency())
                .build();
    }

    @Override
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Balance findByAccountIdAndCurrency(Long accountId, String currency) {
        return balanceRepository.findByAccount_AccountIdAndCurrency(accountId, currency);
    }

    @Override
    public List<Balance> saveBalance(List<Balance> balances) {
        return StreamSupport.stream(
                balanceRepository.saveAll(balances).spliterator(),
                false
        ).collect(Collectors.toList());
    }

    @Override
    public List<Balance> findByAccountId(Long accountId) {
        return balanceRepository.findByAccount_AccountId(accountId);
    }
}
