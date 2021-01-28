package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.model.CustomerResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {

    ObjectMapper mapper;
    String customersJsonPath = "src/test/data/customers.json"; // 4 customer

    @GetMapping("/customers")
    public ResponseEntity<CustomerResponse> getCustomers() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> customers = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });


        CustomerResponse customerResponse = new CustomerResponse(customers, HttpStatus.OK, 20);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Customer customer) throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });

        customerList.add(customer);

        File resultFileCustomers = new File(customersJsonPath);
        mapper.writeValue(resultFileCustomers, customerList);

        List<Customer> customerCreated = Collections.singletonList(customer);
        CustomerResponse customerResponse = new CustomerResponse(customerCreated, HttpStatus.OK, 20);


        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> customers = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });

        List<Customer> foundedCustomer = customers.stream().filter(customer -> customer.getId().equals(id)).collect(Collectors.toList());
        CustomerResponse customerResponse = new CustomerResponse(foundedCustomer, HttpStatus.OK, 20);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
