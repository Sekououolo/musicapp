package com.examen.musicapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChansonRequest {
    private String titre;
    private int duree;
    private int numeroOrdre;
    private Long albumId; // on envoie juste l'ID de l'album
}