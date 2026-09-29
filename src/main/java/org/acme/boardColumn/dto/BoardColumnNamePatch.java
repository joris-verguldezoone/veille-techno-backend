package org.acme.boardColumn.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

public class BoardColumnNamePatch {
    
    @NotNull 
    public Long columnId;

    @NotBlank(message = "Le titre de la colonne est obligatoire pour le renommer")
    public String title;
}