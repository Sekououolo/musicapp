package com.examen.musicapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {
    private String titre;
    private int annee;
    private String genre;
    private Long artisteId; // on envoie juste l'ID de l'artiste
}