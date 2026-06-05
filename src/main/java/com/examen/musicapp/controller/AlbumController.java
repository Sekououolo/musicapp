package com.examen.musicapp.controller;

import com.examen.musicapp.dto.AlbumRequest;
import com.examen.musicapp.dto.AlbumResponse;
import com.examen.musicapp.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
@Tag(name = "Albums", description = "Gestion des albums")
public class AlbumController {

    private final AlbumService service;

    @PostMapping
    @Operation(summary = "Créer un album")
    public ResponseEntity<AlbumResponse> creer(@RequestBody AlbumRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les albums")
    public ResponseEntity<List<AlbumResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un album par ID")
    public ResponseEntity<AlbumResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un album")
    public ResponseEntity<AlbumResponse> modifier(
            @PathVariable Long id,
            @RequestBody AlbumRequest request) {
        return ResponseEntity.ok(service.modifier(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un album")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/albums/artiste/1
    // Tous les albums d'un artiste
    @GetMapping("/artiste/{artisteId}")
    @Operation(summary = "Albums d'un artiste")
    public ResponseEntity<List<AlbumResponse>> getByArtiste(@PathVariable Long artisteId) {
        return ResponseEntity.ok(service.getByArtiste(artisteId));
    }

    // GET /api/v1/albums/genre?genre=rap
    @GetMapping("/genre")
    @Operation(summary = "Albums par genre")
    public ResponseEntity<List<AlbumResponse>> getByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(service.getByGenre(genre));
    }
}