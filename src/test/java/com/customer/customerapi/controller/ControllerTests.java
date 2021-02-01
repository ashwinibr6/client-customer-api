package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.model.CustomerResponse;
import com.customer.customerapi.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utils.TestUtils;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ControllerTests {
    @Autowired
    MockMvc mockMvc;
    List<Customer> customerList;

    @Autowired
    private CustomerRepository customerRepository;
    ObjectMapper mapper;


    @BeforeEach
    void setUp() {
        customerList = TestUtils.initializeCustomersData();
        mapper = new ObjectMapper();
        this.customerRepository.deleteAll();
    }

    @Test
    public void getCustomers() throws Exception {
        customerRepository.save(TestUtils.initializeCustomersData().get(0));
        customerRepository.save(TestUtils.initializeCustomersData().get(1));
        customerRepository.save(TestUtils.initializeCustomersData().get(2));

        MvcResult mvcResult = mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk()).andReturn();

        CustomerResponse customerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);
        assertEquals(TestUtils.initializeCustomersData(), customerResponse.getData());
    }

    @Test
    public void createCustomer() throws Exception {
        Customer requestObject = TestUtils.customerToBeCreated();
        String requestJson = mapper.writeValueAsString(requestObject);

        MvcResult mvcResult = mockMvc.perform(post("/api/customers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        CustomerResponse customerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);

        assertEquals(TestUtils.customerToBeCreated(), customerResponse.getData().get(0));
    }

    @Test
    public void getCustomerById() throws Exception {
        Customer c = customerRepository.save(TestUtils.initializeCustomersData().get(0));

        mockMvc.perform(get("/api/customers/" + c.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(c.getFirstName())))
                .andExpect(content().string(containsString(String.valueOf(c.getId()))))
                .andExpect(content().string(containsString(c.getLastName())))
                .andExpect(content().string(containsString(c.getAddress())))
                .andExpect(content().string(containsString(c.getPhoneNumber())));
    }

    @Test
    public void deleteCustomerById() throws Exception {
        Customer c = customerRepository.save(TestUtils.initializeCustomersData().get(0));

        mockMvc.perform(delete("/api/customers/" + c.getId()))
                .andExpect(status().isNoContent());
        //NOT COMPLETE CODE COVERAGE - NOT SUFFICIENT TIME
    }

//    @Test
//    public void updateCustomerById() throws Exception {
//        Customer c = customerRepository.save(TestUtils.initializeCustomersData().get(0));
//
//        String requestJson = mapper.writeValueAsString(c);
//
//        mockMvc.perform(post("/api/customers")
//                .contentType(APPLICATION_JSON_UTF8)
//                .content(requestJson))
//                .andExpect(status().isCreated());
//
//        c.setFirstName("CHANGED");
//
//        MvcResult mvcResult = mockMvc.perform(put("/api/customers/" + c))
//                .andExpect(status().isOk()).andReturn();
//
//        CustomerResponse customerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerResponse.class);
//
//        assertEquals(c, customerResponse.getData().get(0));
//    }
}
