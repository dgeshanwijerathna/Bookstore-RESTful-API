/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.exception;

/**
 *
 * @author ASUS
 */
public class BookNotFoundException extends RuntimeException {
    private int bookId;
    
    public BookNotFoundException(int bookId) {
        super("Book with ID " + bookId + " not found");
        this.bookId = bookId;
    }
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public int getBookId() {
        return bookId;
    }
}
