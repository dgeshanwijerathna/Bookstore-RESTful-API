/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.exception;

/**
 *
 * @author ASUS
 */
public class CartNotFoundException extends RuntimeException {
    private int customerId;
    
    public CartNotFoundException(int customerId) {
        super("Cart for customer with ID " + customerId + " not found");
        this.customerId = customerId;
    }
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public int getCustomerId() {
        return customerId;
    }
}
