package com.shariful.mb.accountservice.entities.dtos;

 import com.fasterxml.jackson.annotation.JsonInclude;
 import com.shariful.mb.accountservice.utilities.util.TransactionSourceDestination;
 import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long transactionId;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private TransactionSourceDestination transactionSourceDestination;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal balanceAfterTransaction;
}
