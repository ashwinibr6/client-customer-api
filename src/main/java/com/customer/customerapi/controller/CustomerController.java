package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.model.CustomerResponse;
import com.customer.customerapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<CustomerResponse> getCustomers() {
        List<Customer> customersList = customerService.getCustomers();
        CustomerResponse customerResponse = new CustomerResponse(customersList, HttpStatus.OK, 200);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Customer customer) {
        //List<Customer> customerCreated = Collections.singletonList(customer);
        Customer customerAdded = customerService.createCustomer(customer);
        List<Customer> listCustomer = Collections.singletonList(customerAdded);
        CustomerResponse customerResponse = new CustomerResponse(listCustomer, HttpStatus.OK, 201);

        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        List<Customer> customerList = Collections.singletonList(optionalCustomer.get());
        CustomerResponse customerResponse = new CustomerResponse(customerList, HttpStatus.OK, 200);
        //another way of response, instead of creating customerresponse?
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable Long id)  {
        customerService.deleteCustomerById(id);
    }

//    @PutMapping("/customers/{id}")
//    public ResponseEntity<CustomerResponse> updateCustomerById(@RequestBody Customer customer) {
//        CustomerResponse customerResponse = new CustomerResponse(new ArrayList<>(), HttpStatus.OK, 200);
//        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
//    }
}
