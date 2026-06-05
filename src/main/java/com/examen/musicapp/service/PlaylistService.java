package com.examen.musicapp.service;

import com.examen.musicapp.dto.PlaylistRequest;
import com.examen.musicapp.dto.PlaylistResponse;
import java.util.List;

public interface PlaylistService {
    PlaylistResponse creer(PlaylistRequest request);
    PlaylistResponse getById(Long id);
    List<PlaylistResponse> getAll();
    PlaylistResponse modifier(Long id, PlaylistRequest request);
    void supprimer(Long id);
    PlaylistResponse ajouterChanson(Long playlistId, Long chansonId);
    PlaylistResponse retirerChanson(Long playlistId, Long chansonId);
    List<PlaylistResponse> rechercherParNom(String nom);
}
