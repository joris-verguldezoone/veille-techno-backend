package org.acme.boardColumn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BoardColumnRequest {
    
    @NotNull(message = "l'id doit être transmit automatiquement")
    public Long boardId;
    
    @NotBlank(message = "Le titre de la colonne est obligatoire")
    public String title;
}