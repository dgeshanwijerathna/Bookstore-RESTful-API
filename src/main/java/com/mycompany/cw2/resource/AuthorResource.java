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
        // Validate input data
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }
        
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
    public Author getAuthorById(@PathParam("id") int id) {
        Author author = authorStore.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }
    
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author existing = authorStore.get(id);
        if (existing == null) {
            throw new AuthorNotFoundException(id);
        }
        
        // Validate input data
        if (updatedAuthor.getName() == null || updatedAuthor.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }
        
        updatedAuthor.setId(id);
        authorStore.put(id, updatedAuthor);
        return updatedAuthor;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = authorStore.remove(id);
        if (removed == null) {
            throw new AuthorNotFoundException(id);
        }
        
        // Check if any books are associated with this author
        List<Book> associatedBooks = new ArrayList<>();
        for (Book book : bookStore.values()) {
            if (book.getAuthorId() == id) {
                associatedBooks.add(book);
            }
        }
        
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        Author author = authorStore.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        
        List<Book> books = new ArrayList<>();
        for (Book book : bookStore.values()) {
            if (book.getAuthorId() == id) {
                books.add(book);
            }
        }
        return books;
    }
}