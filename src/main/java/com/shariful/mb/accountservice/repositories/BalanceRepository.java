package com.shariful.mb.accountservice.repositories;

import com.shariful.mb.accountservice.entities.dbentities.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
    List<Balance> findByAccount_AccountId(long accountId);

    Balance findByAccount_AccountIdAndCurrency(long accountId,String currency);
 }
