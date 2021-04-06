package com.shariful.mb.accountservice.entities.dtos;

 import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;
    private String country;
    private List<String> currencies;
}
