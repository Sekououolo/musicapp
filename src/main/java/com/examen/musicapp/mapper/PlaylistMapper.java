package com.examen.musicapp.mapper;

import com.examen.musicapp.dto.PlaylistRequest;
import com.examen.musicapp.dto.PlaylistResponse;
import com.examen.musicapp.entity.Chanson;
import com.examen.musicapp.entity.Playlist;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = ChansonMapper.class)
public interface PlaylistMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "chansons", ignore = true)
    Playlist toEntity(PlaylistRequest request);

    @Mapping(target = "nombreChansons", expression = "java(getNombreChansons(playlist))")
    @Mapping(target = "dureeTotale", expression = "java(getDureeTotale(playlist))")
    PlaylistResponse toResponse(Playlist playlist);

    List<PlaylistResponse> toResponseList(List<Playlist> playlists);

    default int getNombreChansons(Playlist playlist) {
        if (playlist.getChansons() == null) return 0;
        return playlist.getChansons().size();
    }

    default int getDureeTotale(Playlist playlist) {
        if (playlist.getChansons() == null) return 0;
        return playlist.getChansons().stream()
                .mapToInt(Chanson::getDuree)
                .sum();
    }
}
