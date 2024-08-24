package com.erick.examencursojava.repository;

import com.erick.examencursojava.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
