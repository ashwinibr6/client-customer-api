package com.customer.customerapi.service;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
         return customerRepository.save(customer);
    }
}
