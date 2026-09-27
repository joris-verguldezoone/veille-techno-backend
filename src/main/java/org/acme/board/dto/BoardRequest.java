package org.acme.board.dto;

import jakarta.validation.constraints.NotBlank;

public class BoardRequest {
    @NotBlank(message = "Le titre du tableau est obligatoire et unique, parmi vos autres tableaux")
    public String title;
}
