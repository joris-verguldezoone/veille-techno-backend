package org.acme.auth;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/register")
public class AuthResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String register() {
        
        
        return "Hello from Quarkus REST";
    }
}
