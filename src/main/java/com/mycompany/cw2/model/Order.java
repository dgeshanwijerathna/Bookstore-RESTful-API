/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.model;

import java.util.List;
/**
 *
 * @author ASUS
 */
public class Order {
    private int id;
    private int customerId;
    private List<Integer> bookIds;

    public Order() {}

    public Order(int id, int customerId, List<Integer> bookIds) {
        this.id = id;
        this.customerId = customerId;
        this.bookIds = bookIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

