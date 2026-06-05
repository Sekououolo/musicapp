package com.examen.musicapp.repository;
import com.examen.musicapp.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtisteId(Long artisteId);  // tous les albums d'un artiste
    List<Album> findByGenreId(Long genreId);
    List<Album> findByGenreNomContainingIgnoreCase(String genre);
    List<Album> findByTitreContainingIgnoreCase(String titre);
}
