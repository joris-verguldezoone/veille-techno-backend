package org.acme.user; // Adapte le package si besoin

import org.acme.auth.entity.User;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/me")
    @Authenticated // Equivalent du guard pour les user qui n'ont pas de JWT
    public Response getCurrentUser() {
        
        String userId = jwt.getSubject();

        User user = User.findById(Long.parseLong(userId));

        return Response.ok(user).build();
    }
}