package com.examen.musicapp.controller;

import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.dto.GenreRequest;
import com.examen.musicapp.dto.GenreResponse;
import com.examen.musicapp.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
@Tag(name = "Genres", description = "Gestion des genres musicaux")
public class GenreController {

    private final GenreService service;

    @PostMapping
    @Operation(summary = "Creer un genre")
    public ResponseEntity<GenreResponse> creer(@RequestBody GenreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
    }

    @GetMapping
    @Operation(summary = "Recuperer tous les genres")
    public ResponseEntity<List<GenreResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un genre par ID")
    public ResponseEntity<GenreResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un genre")
    public ResponseEntity<GenreResponse> modifier(@PathVariable Long id, @RequestBody GenreRequest request) {
        return ResponseEntity.ok(service.modifier(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un genre")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/albums")
    @Operation(summary = "Albums d'un genre")
    public ResponseEntity<List<AlbumResponse>> getAlbumsByGenre(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAlbumsByGenre(id));
    }

    @GetMapping("/recherche")
    @Operation(summary = "Rechercher des genres par nom")
    public ResponseEntity<List<GenreResponse>> rechercher(@RequestParam String nom) {
        return ResponseEntity.ok(service.rechercherParNom(nom));
    }
}
