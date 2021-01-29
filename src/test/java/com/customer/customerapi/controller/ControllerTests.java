package com.customer.customerapi.controller;

import com.customer.customerapi.model.Customer;
import com.customer.customerapi.model.CustomerResponse;
import com.customer.customerapi.repository.CustomerRepository;
import com.customer.customerapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
//
//    @Test
//    public void getCustomerById() throws Exception {
//        String id = "41acbb7a-ebc8-40b7-8281-70635e3466b4";
//
//        Customer customer = TestUtils.getCustomerByIdMock(id);
//
//        mockMvc.perform(get("/api/customers/" + id))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString(customer.getFirstName())))
//                .andExpect(content().string(containsString(customer.getId())))
//                .andExpect(content().string(containsString(customer.getLastName())))
//                .andExpect(content().string(containsString(customer.getAddress())))
//                .andExpect(content().string(containsString(customer.getPhoneNumber())));
//    }
//
//    @Test
//    public void deleteCustomerById() throws Exception {
//        String id = "41acbb7a-ebc8-40b7-8281-70635e3466b8";
//
//        mockMvc.perform(delete("/api/customers/" + id))
//                .andExpect(status().isNoContent());
//    }
}
