package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.dto.GenreRequest;
import com.examen.musicapp.dto.GenreResponse;
import com.examen.musicapp.entity.Genre;
import com.examen.musicapp.mapper.AlbumMapper;
import com.examen.musicapp.mapper.GenreMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.GenreRepository;
import com.examen.musicapp.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final AlbumRepository albumRepository;
    private final GenreMapper genreMapper;
    private final AlbumMapper albumMapper;

    @Override
    public GenreResponse creer(GenreRequest request) {
        return genreMapper.toResponse(genreRepository.save(genreMapper.toEntity(request)));
    }

    @Override
    public GenreResponse getById(Long id) {
        return genreMapper.toResponse(getGenre(id));
    }

    @Override
    public List<GenreResponse> getAll() {
        return genreMapper.toResponseList(genreRepository.findAll());
    }

    @Override
    public GenreResponse modifier(Long id, GenreRequest request) {
        Genre genre = getGenre(id);
        genre.setNom(request.getNom());
        genre.setDescription(request.getDescription());
        return genreMapper.toResponse(genreRepository.save(genre));
    }

    @Override
    public void supprimer(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Genre non trouve avec l'id: " + id);
        }
        genreRepository.deleteById(id);
    }

    @Override
    public List<AlbumResponse> getAlbumsByGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Genre non trouve avec l'id: " + id);
        }
        return albumMapper.toResponseList(albumRepository.findByGenreId(id));
    }

    @Override
    public List<GenreResponse> rechercherParNom(String nom) {
        return genreMapper.toResponseList(genreRepository.findByNomContainingIgnoreCase(nom));
    }

    private Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre non trouve avec l'id: " + id));
    }
}
