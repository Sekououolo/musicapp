package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.AlbumRequest;
import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.entity.Album;
import com.examen.musicapp.entity.Artiste;
import com.examen.musicapp.entity.Genre;
import com.examen.musicapp.exception.ResourceNotFoundException;
import com.examen.musicapp.mapper.AlbumMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.ArtisteRepository;
import com.examen.musicapp.repository.GenreRepository;
import com.examen.musicapp.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtisteRepository artisteRepository;
    private final GenreRepository genreRepository;
    private final AlbumMapper mapper;

    @Override
    public AlbumResponse creer(AlbumRequest request) {
        Artiste artiste = artisteRepository.findById(request.getArtisteId())
                .orElseThrow(() -> new ResourceNotFoundException("Artiste", request.getArtisteId()));

        Album album = mapper.toEntity(request);
        album.setArtiste(artiste);
        if (request.getGenreId() != null) {
            album.setGenre(getGenre(request.getGenreId()));
        }
        return mapper.toResponse(albumRepository.save(album));
    }

    @Override
    public AlbumResponse getById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        return mapper.toResponse(album);
    }

    @Override
    public List<AlbumResponse> getAll() {
        return mapper.toResponseList(albumRepository.findAll());
    }

    @Override
    public AlbumResponse modifier(Long id, AlbumRequest request) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));

        album.setTitre(request.getTitre());
        album.setAnnee(request.getAnnee());
        album.setGenre(request.getGenreId() == null ? null : getGenre(request.getGenreId()));

        if (request.getArtisteId() != null) {
            Artiste artiste = artisteRepository.findById(request.getArtisteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artiste", request.getArtisteId()));
            album.setArtiste(artiste);
        }
        return mapper.toResponse(albumRepository.save(album));
    }

    @Override
    public void supprimer(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Album", id);
        }
        albumRepository.deleteById(id);
    }

    @Override
    public List<AlbumResponse> getByArtiste(Long artisteId) {
        return mapper.toResponseList(albumRepository.findByArtisteId(artisteId));
    }

    @Override
    public List<AlbumResponse> getByGenre(String genre) {
        return mapper.toResponseList(albumRepository.findByGenreNomContainingIgnoreCase(genre));
    }

    private Genre getGenre(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", genreId));
    }
}