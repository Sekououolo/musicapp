package com.examen.musicapp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponse {
    private Long id;
    private String titre;
    private int annee;
    private Long genreId;
    private String nomGenre;
    private String nomArtiste;      // juste le nom, pas tout l'objet Artiste
    private int nombreChansons;     // info utile pour le front
    private List<String> titresChansons;
}
