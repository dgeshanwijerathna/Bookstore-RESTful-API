/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import com.mycompany.cw2.model.Book;
import com.mycompany.cw2.exception.BookNotFoundException;
import com.mycompany.cw2.exception.AuthorNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
/**
 *
 * @author ASUS
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    public static Map<Integer, Book> bookStore = new HashMap<>();
    private static int currentId = 1;
    
    @POST
    public Response createBook(Book book) {
        // Check if authorId exists in authorStore
        if (!AuthorResource.authorStore.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }
        
        // Validate the book fields
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be empty");
        }
        if (book.getPublicationYear() <= 0) {
            throw new InvalidInputException("Publication year must be a positive number");
        }
        if (book.getPrice() < 0) {
            throw new InvalidInputException("Price cannot be negative");
        }
        
        book.setId(currentId++);
        bookStore.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }
    
    @GET
    public Collection<Book> getAllBooks() {
        return bookStore.values();
    }
    
    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") int id) {
        Book book = bookStore.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }
    
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existing = bookStore.get(id);
        if (existing == null) {
            throw new BookNotFoundException(id);
        }
        
        // Check if authorId exists
        if (!AuthorResource.authorStore.containsKey(updatedBook.getAuthorId())) {
            throw new AuthorNotFoundException(updatedBook.getAuthorId());
        }
        
        // Validate the book fields
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }
        if (updatedBook.getIsbn() == null || updatedBook.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be empty");
        }
        if (updatedBook.getPublicationYear() <= 0) {
            throw new InvalidInputException("Publication year must be a positive number");
        }
        if (updatedBook.getPrice() < 0) {
            throw new InvalidInputException("Price cannot be negative");
        }
        
        updatedBook.setId(id);
        bookStore.put(id, updatedBook);
        return updatedBook;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = bookStore.remove(id);
        if (removed == null) {
            throw new BookNotFoundException(id);
        }
        return Response.noContent().build();
    }
}