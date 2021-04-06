package com.shariful.mb.accountservice.entities.rabbitmq;

import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionMap {
    private TransactionDto transaction;
    private BalanceMap balance;
}
