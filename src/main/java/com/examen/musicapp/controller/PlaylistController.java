package com.examen.musicapp.controller;

import com.examen.musicapp.dto.PlaylistRequest;
import com.examen.musicapp.dto.PlaylistResponse;
import com.examen.musicapp.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
@Tag(name = "Playlists", description = "Gestion des playlists")
public class PlaylistController {

    private final PlaylistService service;

    @PostMapping
    @Operation(summary = "Creer une playlist")
    public ResponseEntity<PlaylistResponse> creer(@RequestBody PlaylistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
    }

    @GetMapping
    @Operation(summary = "Recuperer toutes les playlists")
    public ResponseEntity<List<PlaylistResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une playlist par ID")
    public ResponseEntity<PlaylistResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une playlist")
    public ResponseEntity<PlaylistResponse> modifier(@PathVariable Long id, @RequestBody PlaylistRequest request) {
        return ResponseEntity.ok(service.modifier(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une playlist")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/chansons/{chansonId}")
    @Operation(summary = "Ajouter une chanson a une playlist")
    public ResponseEntity<PlaylistResponse> ajouterChanson(@PathVariable Long id, @PathVariable Long chansonId) {
        return ResponseEntity.ok(service.ajouterChanson(id, chansonId));
    }

    @DeleteMapping("/{id}/chansons/{chansonId}")
    @Operation(summary = "Retirer une chanson d'une playlist")
    public ResponseEntity<PlaylistResponse> retirerChanson(@PathVariable Long id, @PathVariable Long chansonId) {
        return ResponseEntity.ok(service.retirerChanson(id, chansonId));
    }

    @GetMapping("/recherche")
    @Operation(summary = "Rechercher des playlists par nom")
    public ResponseEntity<List<PlaylistResponse>> rechercher(@RequestParam String nom) {
        return ResponseEntity.ok(service.rechercherParNom(nom));
    }
}
