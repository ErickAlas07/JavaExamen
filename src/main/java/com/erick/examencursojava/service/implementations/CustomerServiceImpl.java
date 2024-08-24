package com.erick.examencursojava.service.implementations;

import com.erick.examencursojava.entity.Customer;
import com.erick.examencursojava.repository.CustomerRepository;
import com.erick.examencursojava.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        boolean optionalCustomer = customerRepository.existsByEmail(customer.getEmail());
        if(optionalCustomer){
            throw new IllegalArgumentException("Customer already exists with email: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Optional<Customer> update(Long id, Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer customerDb = optionalCustomer.orElseThrow();

            customerDb.setName(customer.getName());
            customerDb.setEmail(customer.getEmail());
            customerDb.setAddress(customer.getAddress());
            return Optional.of(customerRepository.save(customerDb));
        }

        return optionalCustomer;
    }

    @Override
    public Optional<Customer> delete(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        optionalCustomer.ifPresent(customerDb -> {
            customerRepository.delete(customerDb);
        });
        return optionalCustomer;
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
