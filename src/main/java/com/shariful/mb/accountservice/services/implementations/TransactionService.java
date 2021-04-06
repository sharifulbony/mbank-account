package com.shariful.mb.accountservice.services.implementations;

 import com.shariful.mb.accountservice.entities.dbentities.Account;
 import com.shariful.mb.accountservice.entities.dbentities.Balance;
import com.shariful.mb.accountservice.entities.dbentities.Transaction;
 import com.shariful.mb.accountservice.repositories.AccountRepository;
import com.shariful.mb.accountservice.repositories.TransactionRepository;
import com.shariful.mb.accountservice.services.interfaces.BalanceInterface;
import com.shariful.mb.accountservice.services.interfaces.MessageQueueInterface;
import com.shariful.mb.accountservice.services.interfaces.TransactionInterface;
import com.shariful.mb.accountservice.utilities.util.TransactionUtil;
import com.shariful.mb.accountservice.utilities.config.Constant;
import com.shariful.mb.accountservice.utilities.util.TransactionSourceDestination;
import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
import com.shariful.mb.accountservice.entities.rabbitmq.TransactionMap;

import lombok.AllArgsConstructor;
 import org.modelmapper.ModelMapper;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.IllegalTransactionStateException;

 import javax.transaction.Transactional;
 import java.util.ArrayList;
 import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements TransactionInterface {
    private final TransactionRepository transactionRepository;
    private final BalanceInterface balanceInterface;
    private final MessageQueueInterface messageQueueService;
    private final AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;


    public static Transaction toTransaction(TransactionDto transactionDto, Balance balance){
        return Transaction.builder()
                .account(balance.getAccount())
                .amount(transactionDto.getAmount())
                .currency(transactionDto.getCurrency())
                .transactionSourceDestination(transactionDto.getTransactionSourceDestination())
                .description(transactionDto.getDescription())
                .build();
    }
    public  TransactionDto toTransactionDto(Transaction transaction){
        TransactionDto transactionDto=modelMapper.map(transaction,TransactionDto.class);
        return transactionDto;
    }
    public  List<TransactionDto> toTransactionDto(List<Transaction> transactions){
        List<TransactionDto>  transactionDtos= new ArrayList<>();
        for (Transaction transaction:transactions
             ) {
            TransactionDto transactionDto=modelMapper.map(transaction,TransactionDto.class);
            transactionDtos.add(transactionDto);
        }
        return transactionDtos;
    }

    public static Transaction toNewTransaction(Transaction transaction){
        return Transaction.builder()
                .account(transaction.getAccount())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .transactionSourceDestination(transaction.getTransactionSourceDestination())
                .description(transaction.getDescription())
                .build();
    }
    @Transactional
    @Override
    public TransactionDto createTransaction(
            TransactionDto transactionDto
    ) {
        Balance balance = balanceInterface.findByAccountIdAndCurrency(
                        transactionDto.getAccountId(), transactionDto.getCurrency()
                );

        TransactionUtil.checkTransactionValidity(transactionDto, balance);

        balance.setAccount(accountRepository.findByAccountId(transactionDto.getAccountId()));

        changeBalanceAmountByDirection(transactionDto, balance);
        Balance savedBalance = balanceInterface.saveBalance(balance);

        Transaction savedTransaction =
                transactionRepository.save(toTransaction(transactionDto, balance));

        TransactionDto savedTransactionDto =
                toTransactionDto(savedTransaction);

        savedTransactionDto.setBalanceAfterTransaction(balance.getCurrentBalance());

        messageQueueService.publishCreateTransaction(
                TransactionMap.builder()
                    .transaction(toTransactionDto(savedTransaction))
                    .balance(BalanceService.toBalanceMQDto(savedBalance, transactionDto.getAccountId()))
                    .build()
        );

        return savedTransactionDto;
    }

    @Override
    public List<TransactionDto> findByAccountId(Long accountId) throws IllegalTransactionStateException {
        Account account=accountRepository.findByAccountId(accountId);
        if (account == null)
            throw new IllegalTransactionStateException(Constant.ACCOUNT_NOT_FOUND);
        return toTransactionDto(transactionRepository.findByAccount_AccountId(accountId));
    }

    private void changeBalanceAmountByDirection(TransactionDto transactionDto, Balance balance){
        if(transactionDto.getTransactionSourceDestination() ==  TransactionSourceDestination.IN ){
            balance.setCurrentBalance(balance.getCurrentBalance().add(transactionDto.getAmount()));
        }
        else if(transactionDto.getTransactionSourceDestination() == TransactionSourceDestination.OUT){
            balance.setCurrentBalance(balance.getCurrentBalance().subtract(transactionDto.getAmount()));
        }
    }
}
