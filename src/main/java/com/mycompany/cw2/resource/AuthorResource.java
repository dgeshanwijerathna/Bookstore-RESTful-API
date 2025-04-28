/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;
import com.mycompany.cw2.model.Author;
import com.mycompany.cw2.model.Book;
import com.mycompany.cw2.exception.AuthorNotFoundException;
import com.mycompany.cw2.exception.InvalidInputException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import com.mycompany.cw2.storage.AuthorDataStore;
import com.mycompany.cw2.storage.BookDataStore;
/**
 *
 * @author ASUS
 */

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @POST
    public Response createAuthor(Author author) {
        validateAuthor(author);
        Author createdAuthor = AuthorDataStore.createAuthor(author);
        return Response.status(Response.Status.CREATED).entity(createdAuthor).build();
    }

    @GET
    public Collection<Author> getAllAuthors() {
        return AuthorDataStore.getAllAuthors();
    }

    @GET
    @Path("/{id}")
    public Author getAuthorById(@PathParam("id") int id) {
        Author author = AuthorDataStore.getAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }

    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (!AuthorDataStore.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        validateAuthor(updatedAuthor);
        return AuthorDataStore.updateAuthor(id, updatedAuthor);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = AuthorDataStore.deleteAuthor(id);
        if (removed == null) {
            throw new AuthorNotFoundException(id);
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        Author author = AuthorDataStore.getAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }

        List<Book> books = new ArrayList<>();
        for (Book book : BookDataStore.getAllBooks()) {
            if (book.getAuthorId() == id) {
                books.add(book);
            }
        }
        return books;
    }

    private void validateAuthor(Author author) {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }
    }
}