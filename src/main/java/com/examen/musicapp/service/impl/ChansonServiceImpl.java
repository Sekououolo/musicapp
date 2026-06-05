package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import com.examen.musicapp.entity.Album;
import com.examen.musicapp.entity.Chanson;
import com.examen.musicapp.exception.ResourceNotFoundException;
import com.examen.musicapp.mapper.ChansonMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.ChansonRepository;
import com.examen.musicapp.service.ChansonService;
import com.examen.musicapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album", request.getAlbumId()));
        Chanson chanson = mapper.toEntity(request);
        chanson.setAlbum(album);
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    public ChansonResponse getById(Long id) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Chanson", id));
        return mapper.toResponse(chanson);
    }

    @Override
    public List<ChansonResponse> getAll() {
        return mapper.toResponseList(chansonRepository.findAll());
    }

    @Override
    public ChansonResponse modifier(Long id, ChansonRequest request) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Chanson", id));
        chanson.setTitre(request.getTitre());
        chanson.setDuree(request.getDuree());
        chanson.setNumeroOrdre(request.getNumeroOrdre());
        if (request.getAlbumId() != null) {
            Album album = albumRepository.findById(request.getAlbumId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Album", request.getAlbumId()));
            chanson.setAlbum(album);
        }
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    public void supprimer(Long id) {
        if (!chansonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chanson", id);
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Chanson", id));
        String fileName = fileStorageService.storeFile(file, id);
        chanson.setFichierMp3(fileName);
        return mapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    @Transactional
    public Resource getAudioFile(Long id) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Chanson", id));

        if (chanson.getFichierMp3() == null || chanson.getFichierMp3().isEmpty()) {
            throw new ResourceNotFoundException("FichierAudio", id); // ← corrigé
        }

        chansonRepository.incrementerEcoutes(id);

        try {
            Path filePath = fileStorageService.loadFile(chanson.getFichierMp3());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new ResourceNotFoundException("FichierAudio", id); // ← corrigé

        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("FichierAudio", id); // ← corrigé
        }
    }

    @Override
    public List<ChansonResponse> getTop10() {
        return mapper.toResponseList(chansonRepository.findTop10ByOrderByNombreEcoutesDesc());
    }
}