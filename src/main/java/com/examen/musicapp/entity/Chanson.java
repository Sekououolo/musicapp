package com.examen.musicapp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chansons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Chanson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private int duree;        // Durée en secondes (ex: 213 = 3min33)

    private int numeroOrdre;  // Position dans l'album (piste 1, 2, 3...)

    // Une chanson appartient à UN seul album
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    private String fichierMp3;  // Chemin du fichier MP3 sur le disque

    @Builder.Default
    @Column(nullable = false, columnDefinition = "int default 0")
    private int nombreEcoutes = 0; // Compteur d'écoutes
}
