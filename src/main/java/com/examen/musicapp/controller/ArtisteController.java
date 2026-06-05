package com.examen.musicapp.controller;

import com.examen.musicapp.dto.ArtisteRequest;
import com.examen.musicapp.dto.ArtisteResponse;
import com.examen.musicapp.service.ArtisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// @RestController = @Controller + @ResponseBody
// Dit à Spring : cette classe gère des requêtes HTTP et renvoie du JSON

@RequestMapping("/api/v1/artistes")
// Toutes les routes de ce controller commencent par /api/v1/artistes
// C'est le VERSIONING : si tu changes l'API plus tard → /api/v2/artistes

@RequiredArgsConstructor
@Tag(name = "Artistes", description = "Gestion des artistes")
// @Tag : pour Swagger, regroupe les routes sous un même titre
public class ArtisteController {

    private final ArtisteService service;

    // POST /api/v1/artistes
    // Créer un nouvel artiste
    @PostMapping
    @Operation(summary = "Créer un artiste")
    public ResponseEntity<ArtisteResponse> creer(@RequestBody ArtisteRequest request) {
        // @RequestBody : Spring lit le JSON de la requête et le convertit en ArtisteRequest
        // ResponseEntity<> : permet de contrôler le code HTTP renvoyé (201, 200, 404...)
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
        // 201 CREATED = ressource créée avec succès
    }

    // GET /api/v1/artistes
    // Récupérer tous les artistes
    @GetMapping
    @Operation(summary = "Récupérer tous les artistes")
    public ResponseEntity<List<ArtisteResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
        // 200 OK
    }

    // GET /api/v1/artistes/1
    // Récupérer un artiste par son ID
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un artiste par ID")
    public ResponseEntity<ArtisteResponse> getById(@PathVariable Long id) {
        // @PathVariable : récupère le {id} dans l'URL
        return ResponseEntity.ok(service.getById(id));
    }

    // PUT /api/v1/artistes/1
    // Modifier un artiste
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un artiste")
    public ResponseEntity<ArtisteResponse> modifier(
            @PathVariable Long id,
            @RequestBody ArtisteRequest request) {
        return ResponseEntity.ok(service.modifier(id, request));
    }

    // DELETE /api/v1/artistes/1
    // Supprimer un artiste
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un artiste")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
        // 204 NO CONTENT = suppression réussie, pas de corps dans la réponse
    }

    // GET /api/v1/artistes/recherche?nom=Drake
    // Rechercher par nom
    @GetMapping("/recherche")
    @Operation(summary = "Rechercher des artistes par nom")
    public ResponseEntity<List<ArtisteResponse>> rechercher(@RequestParam String nom) {
        // @RequestParam : récupère le paramètre ?nom=... dans l'URL
        return ResponseEntity.ok(service.rechercherParNom(nom));
    }
}