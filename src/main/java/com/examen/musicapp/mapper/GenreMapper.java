package com.examen.musicapp.mapper;

import com.examen.musicapp.dto.GenreRequest;
import com.examen.musicapp.dto.GenreResponse;
import com.examen.musicapp.entity.Genre;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "albums", ignore = true)
    Genre toEntity(GenreRequest request);

    @Mapping(target = "nombreAlbums", expression = "java(getNombreAlbums(genre))")
    GenreResponse toResponse(Genre genre);

    List<GenreResponse> toResponseList(List<Genre> genres);

    default int getNombreAlbums(Genre genre) {
        if (genre.getAlbums() == null) return 0;
        return genre.getAlbums().size();
    }
}
