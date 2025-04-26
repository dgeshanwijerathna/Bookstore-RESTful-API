/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.model;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */



public class Cart {
    private int customerId;
    private List<Integer> bookIds = new ArrayList<>();

    public Cart() {}

    public Cart(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    public void addBook(int bookId) {
        this.bookIds.add(bookId);
    }

    public void removeBook(int bookId) {
        this.bookIds.remove(Integer.valueOf(bookId));
    }

    public void clearCart() {
        this.bookIds.clear();
    }
}

