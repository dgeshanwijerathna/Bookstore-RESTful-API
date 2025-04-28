/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Order;
import com.mycompany.cw2.model.Cart;
import com.mycompany.cw2.exception.CartNotFoundException;
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
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static Map<Integer, List<Order>> orderStore = new HashMap<>();
    private static int currentOrderId = 1;
    
    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        // Validate customerId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        Cart cart = CartResource.getCartByCustomerId(customerId);
        
        // Check if cart is empty
        if (cart.getBookIds().isEmpty()) {
            throw new InvalidInputException("Cannot place order with an empty cart");
        }
        
        Order order = new Order(currentOrderId++, customerId, new ArrayList<>(cart.getBookIds()));
        orderStore.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);
        
        CartResource.clearCart(customerId);
        
        return Response.status(Response.Status.CREATED).entity(order).build();
    }
    
    @GET
    public Response getAllOrdersForCustomer(@PathParam("customerId") int customerId) {
        // Validate customerId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        List<Order> orders = orderStore.get(customerId);
        if (orders == null || orders.isEmpty()) {
            throw new CustomerNotFoundException("No orders found for customer with ID " + customerId);
        }
        
        return Response.ok(orders).build();
    }
    
    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId,
                                 @PathParam("orderId") int orderId) {
        // Validate customerId and orderId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        if (orderId <= 0) {
            throw new InvalidInputException("Order ID must be a positive number");
        }
        
        List<Order> orders = orderStore.get(customerId);
        if (orders == null) {
            throw new CustomerNotFoundException("No orders found for customer with ID " + customerId);
        }
        
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return Response.ok(order).build();
            }
        }
        
        throw new CustomerNotFoundException("Order with ID " + orderId + " not found for customer with ID " + customerId);
    }
}