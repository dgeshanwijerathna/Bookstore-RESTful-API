package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Customer;
import com.mycompany.cw2.exception.CustomerNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
import com.mycompany.cw2.storage.CustomerDataStore;

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

    @POST
    public Response createCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidInputException("Customer data cannot be null");
        }

        validateCustomer(customer);
        Customer createdCustomer = CustomerDataStore.createCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }

    @GET
    public Collection<Customer> getAllCustomers() {
        return CustomerDataStore.getAllCustomers();
    }

    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        Customer customer = CustomerDataStore.getCustomerById(id);
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

        if (!CustomerDataStore.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }

        validateCustomer(updatedCustomer);
        return CustomerDataStore.updateCustomer(id, updatedCustomer);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer deleted = CustomerDataStore.deleteCustomer(id);
        if (deleted == null) {
            throw new CustomerNotFoundException(id);
        }
        return Response.noContent().build();
    }

    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
    }
}