package com.examen.musicapp.mapper;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import com.examen.musicapp.entity.Chanson;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChansonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "fichierMp3", ignore = true)
    Chanson toEntity(ChansonRequest request);

    @Mapping(target = "titreAlbum", expression = "java(chanson.getAlbum().getTitre())")
    @Mapping(target = "nomArtiste", expression = "java(chanson.getAlbum().getArtiste().getNom())")
    @Mapping(target = "hasMp3", expression = "java(chanson.getFichierMp3() != null)")
    ChansonResponse toResponse(Chanson chanson);

    List<ChansonResponse> toResponseList(List<Chanson> chansons);
}