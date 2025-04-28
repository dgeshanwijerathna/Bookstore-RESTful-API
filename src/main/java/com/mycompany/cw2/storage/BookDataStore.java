package com.mycompany.cw2.storage;

import com.mycompany.cw2.model.Book;
import java.util.*;

public class BookDataStore {
    private static final Map<Integer, Book> bookStore = new HashMap<>();
    private static int currentId = 1;

    public static Book createBook(Book book) {
        book.setId(currentId++);
        bookStore.put(book.getId(), book);
        return book;
    }

    public static Collection<Book> getAllBooks() {
        return bookStore.values();
    }

    public static Book getBookById(int id) {
        return bookStore.get(id);
    }

    public static Book updateBook(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookStore.put(id, updatedBook);
        return updatedBook;
    }

    public static Book deleteBook(int id) {
        return bookStore.remove(id);
    }

    public static boolean existsById(int id) {
        return bookStore.containsKey(id);
    }
}
