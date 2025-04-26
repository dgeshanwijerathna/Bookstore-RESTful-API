/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;
import com.mycompany.cw2.model.Customer;

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
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerStore.get(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existing = customerStore.get(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        updatedCustomer.setId(id);
        customerStore.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removed = customerStore.remove(id);
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        return Response.noContent().build();
    }
}

