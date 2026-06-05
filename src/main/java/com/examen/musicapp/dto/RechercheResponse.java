package com.examen.musicapp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RechercheResponse {
    private List<ArtisteResponse> artistes;
    private List<AlbumResponse> albums;
    private List<ChansonResponse> chansons;
}
