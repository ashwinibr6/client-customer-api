package utils;

import com.customer.customerapi.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    static ObjectMapper mapper;
    static ArrayList<Customer> customerList;

    static String customersJsonPath = "src/test/data/customers.json"; // 4 customers
    static String customerJsonPath = "src/test/data/existingCustomer.json"; // 1 customer
    static String newCustomerJsonPath = "src/test/data/newCustomer.json"; // 1 customer

    // TEST UTILITIES ----------------------------------------------------
    public static List<Customer> initializeCustomersData() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        return mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });
    }

    public static String createJsonAsCustomer() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(newCustomerJsonPath);
        Customer customer = mapper.readValue(customerFile, Customer.class);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(customer);
    }

    public static String getAllCustomersAsString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(customersJsonPath);
        List<Customer> customer = mapper.readValue(customerFile, new TypeReference<ArrayList<Customer>>() {
        });
        return mapper.writeValueAsString(customer);
    }

    public static Customer getCustomerByIdMock(String id) throws IOException {
        return initializeCustomersData().stream().filter(customer -> customer.getId().equals(id)).findFirst().get();
    }
}
