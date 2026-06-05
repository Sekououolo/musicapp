package com.examen.musicapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreResponse {
    private Long id;
    private String nom;
    private String description;
    private int nombreAlbums;
}
