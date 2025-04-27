/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import com.mycompany.cw2.model.Cart;
import com.mycompany.cw2.exception.CartNotFoundException;
import com.mycompany.cw2.exception.BookNotFoundException;
import com.mycompany.cw2.exception.CustomerNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
//import com.mycompany.cw2.service.BookService;

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
    private BookResource bookService = new BookResource();
    
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
        // Validate customerId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        // Validate bookId
        int bookId = request.getBookId();
        if (bookId <= 0) {
            throw new InvalidInputException("Book ID must be a positive number");
        }
        
        // Check if book exists
        if (bookService.getBookById(bookId) == null) {
            throw new BookNotFoundException(bookId);
        }
        
        // Get or create cart
        Cart cart = cartStore.computeIfAbsent(customerId, Cart::new);
        cart.addBook(bookId);
        
        return Response.status(Response.Status.CREATED).entity(cart).build();
    }
    
    // GET /customers/{customerId}/cart
    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        // Validate customerId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        
        return Response.ok(cart).build();
    }
    
    // PUT /customers/{customerId}/cart/items/{bookId}
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        // Validate customerId and bookId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        if (bookId <= 0) {
            throw new InvalidInputException("Book ID must be a positive number");
        }
        
        // Check if book exists
        if (bookService.getBookById(bookId) == null) {
            throw new BookNotFoundException(bookId);
        }
        
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        
        // Optional: implement quantity update
        cart.addBook(bookId); // Simulating as a re-add for now
        
        return Response.ok(cart).build();
    }
    
    // DELETE /customers/{customerId}/cart/items/{bookId}
    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        // Validate customerId and bookId
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive number");
        }
        
        if (bookId <= 0) {
            throw new InvalidInputException("Book ID must be a positive number");
        }
        
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        
        // Check if book exists in cart before removing
        if (!cart.getBookIds().contains(bookId)) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
        }
        
        cart.removeBook(bookId);
        
        return Response.ok(cart).build();
    }
    
    public static Cart getCartByCustomerId(int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        return cart;
    }
    
    public static void clearCart(int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        cart.getBookIds().clear();
    }
}