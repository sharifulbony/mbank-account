package com.shariful.mb.accountservice.entities.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceMap {
    private Long balanceId;
    private Long accountId;
    private String currency;
    private BigDecimal balance;
}
