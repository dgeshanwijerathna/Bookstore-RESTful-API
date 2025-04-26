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
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid author ID: Author does not exist")
                    .build();
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
    public Response getBookById(@PathParam("id") int id) {
        Book book = bookStore.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existing = bookStore.get(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        updatedBook.setId(id);
        bookStore.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = bookStore.remove(id);
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        return Response.noContent().build();
    }
}
