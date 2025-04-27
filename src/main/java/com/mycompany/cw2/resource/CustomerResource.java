package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Customer;
import com.mycompany.cw2.exception.CustomerNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 *
 * @author ASUS
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static Map<Integer, Customer> customerStore = new HashMap<>();
    private static int currentId = 1;
    
    @POST
    public Response createCustomer(Customer customer) {
        // Validate input
        if (customer == null) {
            throw new InvalidInputException("Customer data cannot be null");
        }
        
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        
        customer.setId(currentId++);
        customerStore.put(customer.getId(), customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }
    
    @GET
    public Collection<Customer> getAllCustomers() {
        return customerStore.values();
    }
    
    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        Customer customer = customerStore.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }
    
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        if (updatedCustomer == null) {
            throw new InvalidInputException("Updated customer data cannot be null");
        }
        
        if (!customerStore.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        
        updatedCustomer.setId(id);
        customerStore.put(id, updatedCustomer);
        return updatedCustomer;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (!customerStore.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        customerStore.remove(id);
        return Response.noContent().build();
    }
}