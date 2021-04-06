package com.shariful.mb.accountservice.repositories;

 import com.shariful.mb.accountservice.entities.dbentities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByCustomerId(Long customerId);
}
