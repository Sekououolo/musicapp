package repository;
import entity.Artiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ArtisteRepository extends JpaRepository<Artiste,Long>{
    // JpaRepository<Artiste, Long> :
    //   - Artiste = la classe concernée
    //   - Long    = le type de l'ID

    // Spring comprend le nom de la méthode et génère le SQL tout seul !
    List<Artiste> findByNomContainingIgnoreCase(String nom); // recherche par nom

}
