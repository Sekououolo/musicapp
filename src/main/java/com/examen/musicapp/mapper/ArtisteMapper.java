package com.examen.musicapp.mapper;

import com.examen.musicapp.dto.ArtisteRequest;
import com.examen.musicapp.dto.ArtisteResponse;
import com.examen.musicapp.entity.Artiste;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
// componentModel = "spring" : MapStruct crée un Bean Spring
// comme ça on peut l'injecter avec @Autowired partout
public interface ArtisteMapper {

    // ArtisteRequest → Artiste (pour créer/modifier)
    @Mapping(target = "id", ignore = true)      // l'ID est généré par la BD
    @Mapping(target = "albums", ignore = true)  // on gère les albums séparément
    Artiste toEntity(ArtisteRequest request);

    // Artiste → ArtisteResponse (pour renvoyer au client)
    @Mapping(target = "titresAlbums", expression = "java(getTitresAlbums(artiste))")
    ArtisteResponse toResponse(Artiste artiste);

    // Convertit une liste d'artistes en liste de responses
    List<ArtisteResponse> toResponseList(List<Artiste> artistes);

    // Méthode helper : extrait juste les titres des albums
    default List<String> getTitresAlbums(Artiste artiste) {
        if (artiste.getAlbums() == null) return List.of();
        return artiste.getAlbums().stream()
                .map(album -> album.getTitre())
                .collect(Collectors.toList());
    }
}