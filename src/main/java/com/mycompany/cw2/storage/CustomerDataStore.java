package com.mycompany.cw2.storage;

import com.mycompany.cw2.model.Customer;
import java.util.*;

public class CustomerDataStore {
    private static final Map<Integer, Customer> customerStore = new HashMap<>();
    private static int currentId = 1;

    public static Customer createCustomer(Customer customer) {
        customer.setId(currentId++);
        customerStore.put(customer.getId(), customer);
        return customer;
    }

    public static Collection<Customer> getAllCustomers() {
        return customerStore.values();
    }

    public static Customer getCustomerById(int id) {
        return customerStore.get(id);
    }

    public static Customer updateCustomer(int id, Customer updatedCustomer) {
        updatedCustomer.setId(id);
        customerStore.put(id, updatedCustomer);
        return updatedCustomer;
    }

    public static Customer deleteCustomer(int id) {
        return customerStore.remove(id);
    }

    public static boolean existsById(int id) {
        return customerStore.containsKey(id);
    }
}
