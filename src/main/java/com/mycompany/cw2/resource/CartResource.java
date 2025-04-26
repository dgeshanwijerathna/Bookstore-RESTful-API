/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Cart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
/**
 *
 * @author ASUS
 */


@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private static Map<Integer, Cart> cartStore = new HashMap<>();

   // Define a class to receive the book ID in JSON format
    public static class BookIdRequest {
        private int bookId;

        // Default constructor needed for JSON deserialization
        public BookIdRequest() {}

        // Getters and setters
        public int getBookId() { return bookId; }
        public void setBookId(int bookId) { this.bookId = bookId; }
    }

// POST /customers/{customerId}/cart/items
@POST
@Path("/items")
public Response addBookToCart(@PathParam("customerId") int customerId, BookIdRequest request) {
    Cart cart = cartStore.computeIfAbsent(customerId, Cart::new);
    cart.addBook(request.getBookId());
    return Response.status(Response.Status.CREATED).entity(cart).build();
}

    // GET /customers/{customerId}/cart
    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        return Response.ok(cart).build();
    }

    // PUT /customers/{customerId}/cart/items/{bookId}
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        // Optional: implement quantity update
        cart.addBook(bookId); // Simulating as a re-add for now
        return Response.ok(cart).build();
    }

    // DELETE /customers/{customerId}/cart/items/{bookId}
    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        cart.removeBook(bookId);
        return Response.ok(cart).build();
    }
    
    public static Cart getCartByCustomerId(int customerId) {
        return cartStore.get(customerId);
    }

    public static void clearCart(int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart != null) {
            cart.getBookIds().clear();
        }
    }
}

