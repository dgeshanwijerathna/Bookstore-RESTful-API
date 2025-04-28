package com.mycompany.cw2.storage;

import com.mycompany.cw2.model.Author;
import java.util.*;

public class AuthorDataStore {
    private static final Map<Integer, Author> authorStore = new HashMap<>();
    private static int currentId = 1;

    public static Author createAuthor(Author author) {
        author.setId(currentId++);
        authorStore.put(author.getId(), author);
        return author;
    }

    public static Collection<Author> getAllAuthors() {
        return authorStore.values();
    }

    public static Author getAuthorById(int id) {
        return authorStore.get(id);
    }

    public static Author updateAuthor(int id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        authorStore.put(id, updatedAuthor);
        return updatedAuthor;
    }

    public static Author deleteAuthor(int id) {
        return authorStore.remove(id);
    }

    public static boolean existsById(int id) {
        return authorStore.containsKey(id);
    }
}
