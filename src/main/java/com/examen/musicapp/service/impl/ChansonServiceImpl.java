package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import com.examen.musicapp.entity.Album;
import com.examen.musicapp.entity.Chanson;
import com.examen.musicapp.mapper.ChansonMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.ChansonRepository;
import com.examen.musicapp.service.ChansonService;
import com.examen.musicapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChansonServiceImpl implements ChansonService {

    private final ChansonRepository chansonRepository;
    private final AlbumRepository albumRepository;
    private final ChansonMapper mapper;
    private final FileStorageService fileStorageService;

    @Override
    public ChansonResponse creer(ChansonRequest request) {
        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new RuntimeException("Album non trouvé avec l'id: " + request.getAlbumId()));
        Chanson chanson = mapper.toEntity(request);
        chanson.setAlbum(album);
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    public ChansonResponse getById(Long id) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chanson non trouvée avec l'id: " + id));
        return mapper.toResponse(chanson);
    }

    @Override
    public List<ChansonResponse> getAll() {
        return mapper.toResponseList(chansonRepository.findAll());
    }

    @Override
    public ChansonResponse modifier(Long id, ChansonRequest request) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chanson non trouvée avec l'id: " + id));
        chanson.setTitre(request.getTitre());
        chanson.setDuree(request.getDuree());
        chanson.setNumeroOrdre(request.getNumeroOrdre());
        if (request.getAlbumId() != null) {
            Album album = albumRepository.findById(request.getAlbumId())
                    .orElseThrow(() -> new RuntimeException("Album non trouvé"));
            chanson.setAlbum(album);
        }
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    public void supprimer(Long id) {
        if (!chansonRepository.existsById(id)) {
            throw new RuntimeException("Chanson non trouvée avec l'id: " + id);
        }
        chansonRepository.deleteById(id);
    }

    @Override
    public List<ChansonResponse> getByAlbum(Long albumId) {
        return mapper.toResponseList(chansonRepository.findByAlbumId(albumId));
    }

    @Override
    @Transactional
    public ChansonResponse uploadMp3(Long id, MultipartFile file) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chanson non trouvée avec l'id: " + id));
        
        String fileName = fileStorageService.storeFile(file, id);
        chanson.setFichierMp3(fileName);
        
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    @Transactional
    public Resource getAudioFile(Long id) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chanson non trouvée avec l'id: " + id));

        if (chanson.getFichierMp3() == null || chanson.getFichierMp3().isEmpty()) {
            throw new RuntimeException("Aucun fichier audio associé à cette chanson.");
        }

        // Incrémentation du nombre d'écoutes
        chansonRepository.incrementerEcoutes(id);

        try {
            Path filePath = fileStorageService.loadFile(chanson.getFichierMp3());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Le fichier audio est introuvable ou illisible.");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Erreur lors de la lecture du fichier audio.", ex);
        }
    }

    @Override
    public List<ChansonResponse> getTop10() {
        return mapper.toResponseList(chansonRepository.findTop10ByOrderByNombreEcoutesDesc());
    }
}