package org.acme.user;

import java.util.Map;
import org.acme.auth.entity.User;
import org.acme.user.dto.ChangePasswordRequest;
import org.acme.user.dto.ChangeRoleRequest;
import org.acme.user.dto.PatchNameRequest;

import io.quarkus.elytron.security.common.BcryptUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserService {

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = User.findById(userId);
        
        if (user == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Utilisateur introuvable")).build()
            );
        }

        // Verif ancien mdp, nouveau mdp 
        if (!BcryptUtil.matches(request.oldPassword, user.password)) {
            throw new WebApplicationException(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "L'ancien mot de passe est incorrect"))
                        .build()
            );
        }

        user.password = BcryptUtil.bcryptHash(request.newPassword);
        user.persist();
    }

    @Transactional // Active la modification de bdd, le dirty checking pour update
    public User updateName(Long userId,PatchNameRequest request){
        User user = User.findById(userId);

        if (user == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Utilisateur introuvable").build()
            );
        }

        user.name = request.name;

        return user; 
    }

    @Transactional
    public User changeRole(Long targetUserId, ChangeRoleRequest request) {
        User targetUser = User.findById(targetUserId);

        if (targetUser == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Utilisateur introuvable").build()
            );
        }

        // On assigne le nouvel enum
        // A définir dans des input dans le frontend
        targetUser.role = request.role; 

        return targetUser;
    }
}