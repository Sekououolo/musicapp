package com.examen.musicapp.mapper;

import com.examen.musicapp.dto.AlbumRequest;
import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.entity.Album;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artiste", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "chansons", ignore = true)
    Album toEntity(AlbumRequest request);

    @Mapping(target = "genreId", expression = "java(album.getGenre() == null ? null : album.getGenre().getId())")
    @Mapping(target = "nomGenre", expression = "java(album.getGenre() == null ? null : album.getGenre().getNom())")
    @Mapping(target = "nomArtiste", expression = "java(album.getArtiste() == null ? null : album.getArtiste().getNom())")
    @Mapping(target = "nombreChansons", expression = "java(getNombreChansons(album))")
    @Mapping(target = "titresChansons", expression = "java(getTitresChansons(album))")
    AlbumResponse toResponse(Album album);

    List<AlbumResponse> toResponseList(List<Album> albums);

    default int getNombreChansons(Album album) {
        if (album.getChansons() == null) return 0;
        return album.getChansons().size();
    }

    default List<String> getTitresChansons(Album album) {
        if (album.getChansons() == null) return List.of();
        return album.getChansons().stream()
                .map(c -> c.getTitre())
                .collect(Collectors.toList());
    }
}
