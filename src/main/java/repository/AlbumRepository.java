package repository;
import entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtisteId(Long artisteId);  // tous les albums d'un artiste
    List<Album> findByGenre(String genre);  // tous les albums d'un genre
}
