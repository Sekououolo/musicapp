package repository;
import entity.Chanson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChansonRepository extends JpaRepository<Chanson, Long> {
    List<Chanson> findByAlbumId(Long albumId);  // toutes les chansons d'un album
}
