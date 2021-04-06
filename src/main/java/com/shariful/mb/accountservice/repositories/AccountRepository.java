package com.shariful.mb.accountservice.repositories;

 import com.shariful.mb.accountservice.entities.dbentities.Account;
 import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

 Account findByAccountId(long accountId);

}
