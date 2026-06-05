package com.examen.musicapp.service.impl;

import com.examen.musicapp.dto.RechercheResponse;
import com.examen.musicapp.mapper.AlbumMapper;
import com.examen.musicapp.mapper.ArtisteMapper;
import com.examen.musicapp.mapper.ChansonMapper;
import com.examen.musicapp.repository.AlbumRepository;
import com.examen.musicapp.repository.ArtisteRepository;
import com.examen.musicapp.repository.ChansonRepository;
import com.examen.musicapp.service.RechercheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RechercheServiceImpl implements RechercheService {

    private final ArtisteRepository artisteRepository;
    private final AlbumRepository albumRepository;
    private final ChansonRepository chansonRepository;
    private final ArtisteMapper artisteMapper;
    private final AlbumMapper albumMapper;
    private final ChansonMapper chansonMapper;

    @Override
    public RechercheResponse rechercherPartout(String query) {
        String q = query == null ? "" : query.trim();
        return RechercheResponse.builder()
                .artistes(artisteMapper.toResponseList(artisteRepository.findByNomContainingIgnoreCase(q)))
                .albums(albumMapper.toResponseList(albumRepository.findByTitreContainingIgnoreCase(q)))
                .chansons(chansonMapper.toResponseList(chansonRepository.findByTitreContainingIgnoreCase(q)))
                .build();
    }
}
