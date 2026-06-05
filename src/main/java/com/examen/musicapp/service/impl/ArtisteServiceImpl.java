package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.ArtisteRequest;
import com.examen.musicapp.dto.ArtisteResponse;
import com.examen.musicapp.entity.Artiste;
import com.examen.musicapp.exception.ResourceNotFoundException;
import com.examen.musicapp.mapper.ArtisteMapper;
import com.examen.musicapp.repository.ArtisteRepository;
import com.examen.musicapp.service.ArtisteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtisteServiceImpl implements ArtisteService {

    private final ArtisteRepository repository;
    private final ArtisteMapper mapper;

    @Override
    public ArtisteResponse creer(ArtisteRequest request) {
        Artiste artiste = mapper.toEntity(request);
        Artiste saved = repository.save(artiste);
        return mapper.toResponse(saved);
    }

    @Override
    public ArtisteResponse getById(Long id) {
        Artiste artiste = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artiste", id));
        return mapper.toResponse(artiste);
    }

    @Override
    public List<ArtisteResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public ArtisteResponse modifier(Long id, ArtisteRequest request) {
        Artiste artiste = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artiste", id));
        artiste.setNom(request.getNom());
        artiste.setNationalite(request.getNationalite());
        artiste.setBiographie(request.getBiographie());
        return mapper.toResponse(repository.save(artiste));
    }

    @Override
    public void supprimer(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Artiste", id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<ArtisteResponse> rechercherParNom(String nom) {
        return mapper.toResponseList(repository.findByNomContainingIgnoreCase(nom));
    }
}