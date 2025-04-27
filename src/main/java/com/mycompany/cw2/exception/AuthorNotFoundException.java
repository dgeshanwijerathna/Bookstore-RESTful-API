/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.exception;

/**
 *
 * @author ASUS
 */

public class AuthorNotFoundException extends RuntimeException {
    private int authorId;
    
    public AuthorNotFoundException(int authorId) {
        super("Author with ID " + authorId + " not found");
        this.authorId = authorId;
    }
    
    public AuthorNotFoundException(String message) {
        super(message);
    }
    
    public int getAuthorId() {
        return authorId;
    }
}
