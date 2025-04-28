/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Cart;
import com.mycompany.cw2.exception.CartNotFoundException;
import com.mycompany.cw2.exception.BookNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
import com.mycompany.cw2.storage.CartDataStore;
import com.mycompany.cw2.storage.BookDataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ASUS
 */

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    // Inner class to receive bookId from POST/PUT requests
    public static class BookIdRequest {
        private int bookId;

        public BookIdRequest() {}

        public int getBookId() { return bookId; }
        public void setBookId(int bookId) { this.bookId = bookId; }
    }

    @POST
    @Path("/items")
    public Response addBookToCart(@PathParam("customerId") int customerId, BookIdRequest request) {
        validateCustomerId(customerId);
        validateBookId(request.getBookId());

        if (BookDataStore.getBookById(request.getBookId()) == null) {
            throw new BookNotFoundException(request.getBookId());
        }

        Cart cart = CartDataStore.getOrCreateCart(customerId);
        cart.addBook(request.getBookId());
        CartDataStore.saveCart(customerId, cart);

        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        validateCustomerId(customerId);

        Cart cart = CartDataStore.getCartByCustomerId(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        validateCustomerId(customerId);
        validateBookId(bookId);

        if (BookDataStore.getBookById(bookId) == null) {
            throw new BookNotFoundException(bookId);
        }

        Cart cart = CartDataStore.getCartByCustomerId(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }

        cart.addBook(bookId);
        CartDataStore.saveCart(customerId, cart);

        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        validateCustomerId(customerId);
        validateBookId(bookId);

        Cart cart = CartDataStore.getCartByCustomerId(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }

        if (!cart.getBookIds().contains(bookId)) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
        }

        cart.removeBook(bookId);
        CartDataStore.saveCart(customerId, cart);

        return Response.ok(cart).build();
    }

    // Utility methods
    private void validateCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
    }

    private void validateBookId(int bookId) {
        if (bookId <= 0) {
            throw new InvalidInputException("Book ID must be a positive number");
        }
    }

    public static Cart getCartByCustomerIdStatic(int customerId) {
        Cart cart = CartDataStore.getCartByCustomerId(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        return cart;
    }

    public static void clearCartStatic(int customerId) {
        Cart cart = CartDataStore.getCartByCustomerId(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        CartDataStore.clearCart(customerId);
    }
}