/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author ASUS
 */
@Path("/hello")
public class HelloWorldResource {
 @GET
 @Produces(MediaType.TEXT_PLAIN)
 public String getHello() {
 return "Hello, World Eshan Wije!";
 }
}
