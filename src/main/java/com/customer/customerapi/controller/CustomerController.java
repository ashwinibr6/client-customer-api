package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Customer> list =  mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
