package com.shariful.mb.accountservice.entities.dtos;

 import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long accountId;
    private Long customerId;
    private List<BalanceDto> balances;
}
