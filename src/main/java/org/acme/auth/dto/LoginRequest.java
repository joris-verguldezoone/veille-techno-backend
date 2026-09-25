package org.acme.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    
    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "Le format de l'email est invalide")
    public String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit faire au moins 6 caractères")
    public String password;

} // Pas besoin de l'username, email password ça suffit