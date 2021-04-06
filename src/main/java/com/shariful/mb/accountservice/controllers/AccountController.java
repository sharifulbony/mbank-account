package com.shariful.mb.accountservice.controllers;
import com.shariful.mb.accountservice.entities.dtos.AccountDto;
import com.shariful.mb.accountservice.services.interfaces.AccountInterface;
import com.shariful.mb.accountservice.services.interfaces.CustomerInterface;
import com.shariful.mb.accountservice.utilities.util.CurrencyUtil;
import com.shariful.mb.accountservice.entities.dtos.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final CustomerInterface customerInterface;
    private final AccountInterface accountInterface;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody CustomerDto customerDto){
        CurrencyUtil.checkValidCurrency(customerDto.getCurrencies());
        AccountDto accountDto = customerInterface.createCustomer(customerDto);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId){
        AccountDto accountDto = accountInterface.findByAccountId(accountId);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
}
