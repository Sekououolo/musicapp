package com.examen.musicapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtisteRequest {
    // Seulement les champs que le client doit fournir
    // Pas d'ID (c'est la base qui le génère)
    // Pas de liste d'albums (on les ajoute séparément)
    private String nom;
    private String nationalite;
    private String biographie;
}