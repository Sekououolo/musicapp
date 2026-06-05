package com.examen.musicapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChansonResponse {
    private Long id;
    private String titre;
    private int duree;
    private int numeroOrdre;
    private String titreAlbum;   // juste le titre de l'album
    private String nomArtiste;   // pratique pour le front
    private String fichierMp3;
    private boolean hasMp3;
}