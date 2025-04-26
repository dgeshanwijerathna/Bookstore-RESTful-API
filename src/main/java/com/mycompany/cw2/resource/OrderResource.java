/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Order;
import com.mycompany.cw2.model.Cart;

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
    // customerId -> List of Orders
    private static Map<Integer, List<Order>> orderStore = new HashMap<>();
    private static int currentOrderId = 1;

    // POST /customers/{customerId}/orders
    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        Cart cart = CartResource.getCartByCustomerId(customerId);
        if (cart == null || cart.getBookIds().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart is empty or does not exist").build();
        }

        Order order = new Order(currentOrderId++, customerId, new ArrayList<>(cart.getBookIds()));
        orderStore.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);

        CartResource.clearCart(customerId); // clear cart after placing order
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    // GET /customers/{customerId}/orders
    @GET
    public Response getAllOrdersForCustomer(@PathParam("customerId") int customerId) {
        List<Order> orders = orderStore.get(customerId);
        if (orders == null || orders.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No orders found for customer").build();
        }
        return Response.ok(orders).build();
    }

    // GET /customers/{customerId}/orders/{orderId}
    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId,
                                 @PathParam("orderId") int orderId) {
        List<Order> orders = orderStore.get(customerId);
        if (orders != null) {
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    return Response.ok(order).build();
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Order not found for this customer").build();
    }
}