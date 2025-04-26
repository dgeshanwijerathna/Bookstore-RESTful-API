/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;
import com.mycompany.cw2.model.Author;
import com.mycompany.cw2.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
/**
 *
 * @author ASUS
 */

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    public static Map<Integer, Author> authorStore = new HashMap<>();
    private static Map<Integer, Book> bookStore = BookResource.bookStore;
    private static int currentId = 1;

    @POST
    public Response createAuthor(Author author) {
        author.setId(currentId++);
        authorStore.put(author.getId(), author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public Collection<Author> getAllAuthors() {
        return authorStore.values();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = authorStore.get(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author existing = authorStore.get(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        updatedAuthor.setId(id);
        authorStore.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = authorStore.remove(id);
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        List<Book> books = new ArrayList<>();
        for (Book book : bookStore.values()) {
            if (book.getAuthorId() == id) {
                books.add(book);
            }
        }
        return Response.ok(books).build();
    }
}

