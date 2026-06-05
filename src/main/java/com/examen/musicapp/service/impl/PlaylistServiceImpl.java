package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.PlaylistRequest;
import com.examen.musicapp.dto.PlaylistResponse;
import com.examen.musicapp.entity.Chanson;
import com.examen.musicapp.entity.Playlist;
import com.examen.musicapp.mapper.PlaylistMapper;
import com.examen.musicapp.repository.ChansonRepository;
import com.examen.musicapp.repository.PlaylistRepository;
import com.examen.musicapp.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ChansonRepository chansonRepository;
    private final PlaylistMapper mapper;

    @Override
    public PlaylistResponse creer(PlaylistRequest request) {
        return mapper.toResponse(playlistRepository.save(mapper.toEntity(request)));
    }

    @Override
    public PlaylistResponse getById(Long id) {
        return mapper.toResponse(getPlaylist(id));
    }

    @Override
    public List<PlaylistResponse> getAll() {
        return mapper.toResponseList(playlistRepository.findAll());
    }

    @Override
    public PlaylistResponse modifier(Long id, PlaylistRequest request) {
        Playlist playlist = getPlaylist(id);
        playlist.setNom(request.getNom());
        playlist.setDescription(request.getDescription());
        return mapper.toResponse(playlistRepository.save(playlist));
    }

    @Override
    public void supprimer(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist non trouvee avec l'id: " + id);
        }
        playlistRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PlaylistResponse ajouterChanson(Long playlistId, Long chansonId) {
        Playlist playlist = getPlaylist(playlistId);
        Chanson chanson = getChanson(chansonId);

        boolean exists = playlist.getChansons().stream()
                .anyMatch(existing -> existing.getId().equals(chansonId));
        if (!exists) {
            playlist.getChansons().add(chanson);
        }

        return mapper.toResponse(playlistRepository.save(playlist));
    }

    @Override
    @Transactional
    public PlaylistResponse retirerChanson(Long playlistId, Long chansonId) {
        Playlist playlist = getPlaylist(playlistId);
        playlist.getChansons().removeIf(chanson -> chanson.getId().equals(chansonId));
        return mapper.toResponse(playlistRepository.save(playlist));
    }

    @Override
    public List<PlaylistResponse> rechercherParNom(String nom) {
        return mapper.toResponseList(playlistRepository.findByNomContainingIgnoreCase(nom));
    }

    private Playlist getPlaylist(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist non trouvee avec l'id: " + id));
    }

    private Chanson getChanson(Long id) {
        return chansonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chanson non trouvee avec l'id: " + id));
    }
}
