package com.shariful.mb.accountservice.controllers;

 import com.shariful.mb.accountservice.services.interfaces.TransactionInterface;
import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionInterface transactionInterface;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<TransactionDto> createTransaction(
            @RequestBody TransactionDto transactionDto
    ){
        TransactionDto transactionResponse = transactionInterface.createTransaction(transactionDto);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<List<TransactionDto>> getTransactions(
            @PathVariable Long accountId
    )  {
        List<TransactionDto> transactions = transactionInterface.findByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
