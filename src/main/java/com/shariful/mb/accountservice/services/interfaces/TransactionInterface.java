package com.shariful.mb.accountservice.services.interfaces;

 import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
 import org.springframework.transaction.IllegalTransactionStateException;

 import java.util.List;

public interface TransactionInterface {
    TransactionDto createTransaction(TransactionDto transactionDto);

    List<TransactionDto> findByAccountId(Long accountId) throws IllegalTransactionStateException;
}
