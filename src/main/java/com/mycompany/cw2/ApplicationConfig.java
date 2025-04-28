/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw2;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
/**
 *
 * @author ASUS
 */
@ApplicationPath("/bookstore")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("com.mycompany.cw2.resource", "com.mycompany.cw2.exception");
        register(org.glassfish.jersey.jackson.JacksonFeature.class);
        
    }
}
