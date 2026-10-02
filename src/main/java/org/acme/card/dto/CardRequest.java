package org.acme.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CardRequest {
    @NotNull(message = "L'ID de la colonne est obligatoire")
    public Long columnId;
    
    @NotBlank(message = "Le titre de la tâche est obligatoire")
    public String title;

    public String description; // Pas de @NotBlank car il peut être vide
}