package org.acme.user; 

import org.acme.auth.entity.User;
import org.acme.boardColumn.BoardColumnService;
import org.acme.user.dto.ChangePasswordRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/me")
    @Authenticated // Obtenir les infos user sans tout mettre dans le token
    @SecurityRequirement(name = "jwt")
    public Response getCurrentUser() {
        
        String userId = jwt.getSubject();

        User user = User.findById(Long.parseLong(userId));

        return Response.ok(user).build();
    }

    @PUT
    @Path("/me/password")
    public Response changePassword(@Valid ChangePasswordRequest request) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        userService.changePassword(userId, request);
        
        return Response.noContent().build(); // 204 No Content (succès sans body)
    }
}