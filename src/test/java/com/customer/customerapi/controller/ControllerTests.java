package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.model.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utils.TestUtils;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        ObjectMapper mapper = new ObjectMapper();

        CustomerResponse customerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);

        assertEquals(customerList, customerResponse.getData());
    }

    @Test
    public void createCustomer() throws Exception {
        String requestJson = TestUtils.createJsonAsCustomer();
        mockMvc.perform(post("/api/customers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(customerList.get(customerList.size() - 1).getFirstName())))
                .andExpect(content().string(containsString(customerList.get(customerList.size() - 1).getId())))
                .andExpect(content().string(containsString(customerList.get(customerList.size() - 1).getLastName())))
                .andExpect(content().string(containsString(customerList.get(customerList.size() - 1).getAddress())))
                .andExpect(content().string(containsString(customerList.get(customerList.size() - 1).getPhoneNumber())));
    }

    @Test
    public void getCustomerById() throws Exception {
        String id = "41acbb7a-ebc8-40b7-8281-70635e3466b4";

        Customer customer = TestUtils.getCustomerByIdMock(id);

        mockMvc.perform(get("/api/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(customer.getFirstName())))
                .andExpect(content().string(containsString(customer.getId())))
                .andExpect(content().string(containsString(customer.getLastName())))
                .andExpect(content().string(containsString(customer.getAddress())))
                .andExpect(content().string(containsString(customer.getPhoneNumber())));
    }

    @Test
    public void deleteCustomerById() throws Exception {
        String id = "41acbb7a-ebc8-40b7-8281-70635e3466b8";

        mockMvc.perform(delete("/api/customers/" + id))
                .andExpect(status().isNoContent());
    }
}
