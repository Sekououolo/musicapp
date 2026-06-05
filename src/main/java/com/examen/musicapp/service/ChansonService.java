package com.examen.musicapp.service;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import java.util.List;

public interface ChansonService {
    ChansonResponse creer(ChansonRequest request);
    ChansonResponse getById(Long id);
    List<ChansonResponse> getAll();
    ChansonResponse modifier(Long id, ChansonRequest request);
    void supprimer(Long id);
    List<ChansonResponse> getByAlbum(Long albumId);
}