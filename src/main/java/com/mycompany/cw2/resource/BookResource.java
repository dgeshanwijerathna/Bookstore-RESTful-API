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
import com.mycompany.cw2.storage.BookDataStore;
import com.mycompany.cw2.storage.AuthorDataStore;

/**
 *
 * @author ASUS
 */

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @POST
    public Response createBook(Book book) {
        validateBook(book);

        if (!AuthorDataStore.existsById(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }


        Book createdBook = BookDataStore.createBook(book);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    @GET
    public Collection<Book> getAllBooks() {
        return BookDataStore.getAllBooks();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") int id) {
        Book book = BookDataStore.getBookById(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book updatedBook) {
        if (!BookDataStore.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        validateBook(updatedBook);

        if (!AuthorDataStore.existsById(updatedBook.getAuthorId())) {
            throw new AuthorNotFoundException(updatedBook.getAuthorId());
        }


        return BookDataStore.updateBook(id, updatedBook);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = BookDataStore.deleteBook(id);
        if (removed == null) {
            throw new BookNotFoundException(id);
        }
        return Response.noContent().build();
    }

    private void validateBook(Book book) {
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
    }
}