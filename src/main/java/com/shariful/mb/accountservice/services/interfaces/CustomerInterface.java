package com.shariful.mb.accountservice.services.interfaces;

 import com.shariful.mb.accountservice.entities.dtos.AccountDto;
 import com.shariful.mb.accountservice.entities.dtos.CustomerDto;

public interface CustomerInterface {
    AccountDto createCustomer(CustomerDto customerDto);

}
