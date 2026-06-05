package com.examen.musicapp.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistResponse {
    private Long id;
    private String nom;
    private String description;
    private LocalDateTime dateCreation;
    private int nombreChansons;
    private int dureeTotale;
    private List<ChansonResponse> chansons;
}
