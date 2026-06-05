package com.examen.musicapp.repository;
import com.examen.musicapp.entity.Chanson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ChansonRepository extends JpaRepository<Chanson, Long> {
    List<Chanson> findByAlbumId(Long albumId);  // toutes les chansons d'un album

    @Modifying
    @Query("UPDATE Chanson c SET c.nombreEcoutes = c.nombreEcoutes + 1 WHERE c.id = :id")
    void incrementerEcoutes(@Param("id") Long id);

    List<Chanson> findTop10ByOrderByNombreEcoutesDesc();

    List<Chanson> findByTitreContainingIgnoreCase(String titre);
}
