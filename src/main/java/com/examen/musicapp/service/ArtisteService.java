package com.examen.musicapp.service;

import com.examen.musicapp.dto.ArtisteRequest;
import com.examen.musicapp.dto.ArtisteResponse;
import java.util.List;

public interface ArtisteService {
    ArtisteResponse creer(ArtisteRequest request);
    ArtisteResponse getById(Long id);
    List<ArtisteResponse> getAll();
    ArtisteResponse modifier(Long id, ArtisteRequest request);
    void supprimer(Long id);
    List<ArtisteResponse> rechercherParNom(String nom);
}