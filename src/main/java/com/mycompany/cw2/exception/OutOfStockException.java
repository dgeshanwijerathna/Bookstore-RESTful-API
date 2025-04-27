/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.exception;

/**
 *
 * @author ASUS
 */
public class OutOfStockException extends RuntimeException {
    private int bookId;
    private int requestedQuantity;
    private int availableQuantity;
    
    public OutOfStockException(int bookId, int requestedQuantity, int availableQuantity) {
        super("Book with ID " + bookId + " is out of stock. Requested: " + 
              requestedQuantity + ", Available: " + availableQuantity);
        this.bookId = bookId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }
    
    public OutOfStockException(String message) {
        super(message);
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public int getRequestedQuantity() {
        return requestedQuantity;
    }
    
    public int getAvailableQuantity() {
        return availableQuantity;
    }
}