package com.erick.examencursojava.service;

import com.erick.examencursojava.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    Optional<Customer> update(Long id, Customer customer);

    Optional<Customer> delete(Long id);

    void deleteAll();
}
