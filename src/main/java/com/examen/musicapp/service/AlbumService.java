package com.examen.musicapp.service;

import com.examen.musicapp.dto.AlbumRequest;
import com.examen.musicapp.dto.AlbumResponse;
import java.util.List;

public interface AlbumService {
    AlbumResponse creer(AlbumRequest request);
    AlbumResponse getById(Long id);
    List<AlbumResponse> getAll();
    AlbumResponse modifier(Long id, AlbumRequest request);
    void supprimer(Long id);
    List<AlbumResponse> getByArtiste(Long artisteId);
    List<AlbumResponse> getByGenre(String genre);
}