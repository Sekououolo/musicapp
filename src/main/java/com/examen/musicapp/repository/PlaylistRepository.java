package com.examen.musicapp.repository;

import com.examen.musicapp.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByNomContainingIgnoreCase(String nom);
}
