/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Order;
import com.mycompany.cw2.model.Cart;

import com.mycompany.cw2.exception.CustomerNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
import com.mycompany.cw2.storage.OrderDataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 *
 * @author ASUS
 */

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        validateCustomerId(customerId);

        Cart cart = CartResource.getCartByCustomerIdStatic(customerId);

        if (cart.getBookIds().isEmpty()) {
            throw new InvalidInputException("Cannot place order with an empty cart");
        }

        Order newOrder = OrderDataStore.createOrder(customerId, cart.getBookIds());

        CartResource.clearCartStatic(customerId);

        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    @GET
    public Response getAllOrdersForCustomer(@PathParam("customerId") int customerId) {
        validateCustomerId(customerId);

        List<Order> orders = OrderDataStore.getAllOrdersForCustomer(customerId);
        if (orders == null || orders.isEmpty()) {
            throw new CustomerNotFoundException("No orders found for customer with ID " + customerId);
        }

        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId,
                                 @PathParam("orderId") int orderId) {
        validateCustomerId(customerId);
        validateOrderId(orderId);

        Order order = OrderDataStore.getOrderById(customerId, orderId);
        if (order == null) {
            throw new CustomerNotFoundException("Order with ID " + orderId + " not found for customer with ID " + customerId);
        }

        return Response.ok(order).build();
    }

    private void validateCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
    }

    private void validateOrderId(int orderId) {
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be a positive number");
        }
    }
}