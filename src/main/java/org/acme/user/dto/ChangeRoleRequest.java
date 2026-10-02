package org.acme.user.dto;

import org.acme.auth.entity.User;
import jakarta.validation.constraints.NotNull;

public class ChangeRoleRequest {
    @NotNull(message = "Le rôle est obligatoire (ADMIN, USER)")
    public User.Role role; 
}