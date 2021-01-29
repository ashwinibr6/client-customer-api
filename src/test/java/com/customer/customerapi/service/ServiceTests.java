package com.customer.customerapi.service;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.transaction.annotation.Transactional;
import utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ServiceTests {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    public void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(TestUtils.initializeCustomersData());
        List<Customer> customerList = customerService.getCustomers();

        verify(customerRepository, times(1)).findAll();
        assertEquals(TestUtils.initializeCustomersData(),customerList);
    }

    @Test
    public void createCustomer() {
        when(customerRepository.save(any())).thenReturn(TestUtils.customerToBeCreated());
        Customer customer = customerService.createCustomer(TestUtils.customerToBeCreated());
        verify(customerRepository, times(1)).save(any());

        assertEquals(TestUtils.customerToBeCreated(), customer);
    }

    @Test
    public void getCustomerById() {
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(TestUtils.initializeCustomersData().get(0)));
        Optional<Customer> customerFounded = customerService.getCustomerById(1L);

        verify(customerRepository, times(1)).findById(any());
        assertEquals(Optional.ofNullable(TestUtils.initializeCustomersData().get(0)),customerFounded);
    }
}
