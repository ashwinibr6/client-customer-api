package com.customer.customerapi.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CustomerResponse {
    private int statusCode;
    private HttpStatus status;
    private List<Customer> data;

    public CustomerResponse(List<Customer> data, HttpStatus status,  int statusCode) {
        this.statusCode = statusCode;
        this.status = status;
        this.data = data;
    }

    public CustomerResponse() {
        
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<Customer> getData() {
        return data;
    }
}
