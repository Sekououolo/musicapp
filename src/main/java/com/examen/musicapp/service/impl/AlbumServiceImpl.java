package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.AlbumRequest;
import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.entity.Album;
import com.examen.musicapp.entity.Artiste;
import com.examen.musicapp.mapper.AlbumMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.ArtisteRepository;
import com.examen.musicapp.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtisteRepository artisteRepository;
    private final AlbumMapper mapper;

    @Override
    public AlbumResponse creer(AlbumRequest request) {
        // On récupère l'artiste depuis la BD avec son ID
        Artiste artiste = artisteRepository.findById(request.getArtisteId())
                .orElseThrow(() -> new RuntimeException("Artiste non trouvé avec l'id: " + request.getArtisteId()));
        Album album = mapper.toEntity(request);
        album.setArtiste(artiste);  // On lie l'album à l'artiste
        return mapper.toResponse(albumRepository.save(album));
    }

    @Override
    public AlbumResponse getById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album non trouvé avec l'id: " + id));
        return mapper.toResponse(album);
    }

    @Override
    public List<AlbumResponse> getAll() {
        return mapper.toResponseList(albumRepository.findAll());
    }

    @Override
    public AlbumResponse modifier(Long id, AlbumRequest request) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album non trouvé avec l'id: " + id));
        album.setTitre(request.getTitre());
        album.setAnnee(request.getAnnee());
        album.setGenre(request.getGenre());
        // Si on change d'artiste
        if (request.getArtisteId() != null) {
            Artiste artiste = artisteRepository.findById(request.getArtisteId())
                    .orElseThrow(() -> new RuntimeException("Artiste non trouvé"));
            album.setArtiste(artiste);
        }
        return mapper.toResponse(albumRepository.save(album));
    }

    @Override
    public void supprimer(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Album non trouvé avec l'id: " + id);
        }
        albumRepository.deleteById(id);
    }

    @Override
    public List<AlbumResponse> getByArtiste(Long artisteId) {
        return mapper.toResponseList(albumRepository.findByArtisteId(artisteId));
    }

    @Override
    public List<AlbumResponse> getByGenre(String genre) {
        return mapper.toResponseList(albumRepository.findByGenre(genre));
    }
}