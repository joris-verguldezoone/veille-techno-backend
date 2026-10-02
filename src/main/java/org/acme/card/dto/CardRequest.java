package org.acme.card.dto;

import jakarta.validation.constraints.NotBlank;

public class CardRequest {
    
    @NotBlank(message = "Le titre de la carte est obligatoire")
    public String title;
    
    public String description;
}