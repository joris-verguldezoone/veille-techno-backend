package org.acme.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    
    @NotBlank(message = "Ancien mdp")
    public String oldPassword;

    @NotBlank(message = "Nouveau mdp")
    @Size(min = 6, message = "Le nouveau mdp doit faire au moins 6 caractères")
    public String newPassword;
}