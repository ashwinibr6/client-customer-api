package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    ObjectMapper mapper;
    String customersJsonPath = "src/test/data/customers.json"; // 4 customer

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> list = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });

        customerList.add(customer);

        File resultFileCustomers = new File(customersJsonPath);
        mapper.writeValue(resultFileCustomers, customerList);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        List<Customer> customers = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });

        Customer foundedCustomer = customers.stream().filter(customer -> customer.getId().equals(id)).findFirst().get();
        return new ResponseEntity<>(foundedCustomer, HttpStatus.OK);
    }
}
