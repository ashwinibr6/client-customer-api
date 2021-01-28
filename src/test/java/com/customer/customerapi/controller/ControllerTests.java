package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    MockMvc mockMvc;
    List<Customer> customerList;
    String customersAsString;


    @BeforeEach
    void setUp() throws IOException {
        customerList = TestUtils.initializeCustomersData();
        customersAsString = TestUtils.getAllCustomersAsString();
    }

    @Test
    public void getCustomers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/customers")).andExpect(status().isOk()).andReturn();

        String actual = mvcResult.getResponse().getContentAsString();

        assertEquals(customersAsString, actual);
        //      mockMvc.perform(get("/api/customers"))
//            .andExpect(status().isOk())
//            .andExpect(content().string(containsString(customerList.get(0).getFirstName())))
//            .andExpect(content().string(containsString(customerList.get(0).getId())))
//            .andExpect(content().string(containsString(customerList.get(0).getLastName())))
//            .andExpect(content().string(containsString(customerList.get(0).getAddress())))
//            .andExpect(content().string(containsString(customerList.get(0).getPhoneNumber())));
    }




}
