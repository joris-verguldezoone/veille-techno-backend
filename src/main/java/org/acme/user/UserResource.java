package org.acme.user; 

import java.util.List;

import javax.smartcardio.Card;

import org.acme.auth.entity.User;
import org.acme.boardColumn.BoardColumnService;
import org.acme.user.dto.ChangePasswordRequest;
import org.acme.user.dto.ChangeRoleRequest;
import org.acme.user.dto.PatchNameRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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

    @PATCH // automatique, hibernate va détecter les changements et patch tout seul
    @Path("/me/name")
    public Response patchName(@Valid PatchNameRequest request) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        User updateName = userService.updateName(userId, request);
        
        return Response.ok(updateName).build();
    }

    @PATCH
    @Path("/{userId}/role")
    public Response changeRole(@PathParam("userId") Long targetUserId, @Valid ChangeRoleRequest request) {
        // On vérifie qui fait la requete        
        Long currentUserId = Long.parseLong(jwt.getSubject());
        User currentUser = User.findById(currentUserId);
        
        // Suis-je admin ? 
        if (currentUser == null || currentUser.role != User.Role.ADMIN) {
            return Response.status(Response.Status.FORBIDDEN)
                           .entity("Seul un administrateur peut changer les rôles")
                           .build();
        }

        User updatedUser = userService.changeRole(targetUserId, request);
        
        return Response.ok(updatedUser).build();
    }

    @GET // pour du dev, a suppr ou a mettre en droit admin
    public Response getAllUsers(){
        List<User> users = User.findAll().list();
        return Response.ok(users).build();
    }
}