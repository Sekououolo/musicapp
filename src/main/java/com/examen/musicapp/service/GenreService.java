package com.examen.musicapp.service;

import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.dto.GenreRequest;
import com.examen.musicapp.dto.GenreResponse;
import java.util.List;

public interface GenreService {
    GenreResponse creer(GenreRequest request);
    GenreResponse getById(Long id);
    List<GenreResponse> getAll();
    GenreResponse modifier(Long id, GenreRequest request);
    void supprimer(Long id);
    List<AlbumResponse> getAlbumsByGenre(Long id);
    List<GenreResponse> rechercherParNom(String nom);
}
