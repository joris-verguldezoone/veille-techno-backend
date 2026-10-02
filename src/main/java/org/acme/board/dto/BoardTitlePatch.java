package org.acme.board.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

public class BoardTitlePatch {
    
    // @NotNull 
    // public Long userId;

    @NotBlank(message = "Le titre du tableau est obligatoire pour le renommer")
    public String title;
}