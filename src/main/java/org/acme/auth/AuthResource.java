package org.acme.auth;

import org.acme.auth.dto.RegisterRequest;
import org.acme.auth.entity.User;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject // C'est ça qui permet de faire le lien entre les classes
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) { // @Valid permet d'utiliser le dto 
        User user = authService.register(request);
        return Response.status(Response.Status.CREATED).entity(user).build();
        // Response est un standard de Java

    }
}
