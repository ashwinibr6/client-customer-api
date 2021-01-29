package utils;

import com.customer.customerapi.model.Customer;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    // TEST UTILITIES ----------------------------------------------------
    public static List<Customer> initializeCustomersData() {
        Customer customer1 = new Customer(1L, "Qin", "Zhang", "510-555-2367", "1 Main Street, Topeka, KS 37891");
        Customer customer2 = new Customer(2L,"Hanaan", "Altalib", "204-555-9753", "1826 Truth Place, New York, NY, 20127");
        Customer customer3 = new Customer(3L, "Isabella", "Baumfree", "309-555-1892", "1826 Truth Place, New York, NY 20127");

        List<Customer> customerList = Arrays.asList(customer1, customer2, customer3);

        return customerList;
    }

    public static Customer customerToBeCreated() {
        return new Customer(1L, "Araminta", "Ross", "309-555-1370", "1849 Harriet Ave, Auburn, NY 63102");
    }

//    public static Customer getCustomerByIdMock(String id) {
//        return initializeCustomersData().stream().filter(customer -> customer.getId().equals(id)).findFirst().get();
//    }
}
