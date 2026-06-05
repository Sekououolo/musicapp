package com.examen.musicapp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtisteResponse {
    private Long id;
    private String nom;
    private String nationalite;
    private String biographie;
    // On renvoie juste les titres des albums, pas les objets complets
    // Ça évite la boucle infinie Artiste -> Album -> Artiste -> ...
    private List<String> titresAlbums;
}