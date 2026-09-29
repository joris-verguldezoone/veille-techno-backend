package org.acme.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatchNameRequest {

    @NotBlank(message = "Nouveau username")
    @Size(min = 1, max=32, message="Le nouveau username doit faire maximum 32 caractères")
    public String name;
}