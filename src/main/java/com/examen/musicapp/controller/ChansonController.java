package com.examen.musicapp.controller;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import com.examen.musicapp.service.ChansonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chansons")
@RequiredArgsConstructor
@Tag(name = "Chansons", description = "Gestion des chansons")
public class ChansonController {

    private final ChansonService service;

    @PostMapping
    @Operation(summary = "Créer une chanson")
    public ResponseEntity<ChansonResponse> creer(@RequestBody ChansonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les chansons")
    public ResponseEntity<List<ChansonResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une chanson par ID")
    public ResponseEntity<ChansonResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une chanson")
    public ResponseEntity<ChansonResponse> modifier(
            @PathVariable Long id,
            @RequestBody ChansonRequest request) {
        return ResponseEntity.ok(service.modifier(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une chanson")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/chansons/album/1
    @GetMapping("/album/{albumId}")
    @Operation(summary = "Chansons d'un album")
    public ResponseEntity<List<ChansonResponse>> getByAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(service.getByAlbum(albumId));
    }
}