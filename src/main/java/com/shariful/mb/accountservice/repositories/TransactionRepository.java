package com.shariful.mb.accountservice.repositories;

 import com.shariful.mb.accountservice.entities.dbentities.Transaction;
import org.springframework.data.repository.CrudRepository;

 import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

 List<Transaction> findByAccount_AccountId(long accountId);
}
