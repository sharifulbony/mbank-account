package com.shariful.mb.accountservice.services.interfaces;

 import com.shariful.mb.accountservice.entities.dbentities.Balance;

import java.util.List;

public interface BalanceInterface {
    Balance saveBalance(Balance balance);
    Balance findByAccountIdAndCurrency(Long accountId, String currency);
    List<Balance> saveBalance(List<Balance> balance);
    List<Balance> findByAccountId(Long accountId);
}
