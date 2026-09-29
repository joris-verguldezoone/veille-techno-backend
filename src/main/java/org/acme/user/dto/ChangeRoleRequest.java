package org.acme.user.dto;

import org.acme.auth.entity.User;
import jakarta.validation.constraints.NotNull;

public class ChangeRoleRequest {
    // si le role n'est pas respecté, Quarkus renverra une erreur 400 Bad Request automatiquement
    @NotNull(message = "Le rôle est obligatoire (ex: ADMIN, USER)")
    public User.Role role; 
}