package com.examen.musicapp.controller;

import com.examen.musicapp.dto.RechercheResponse;
import com.examen.musicapp.service.RechercheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recherche")
@RequiredArgsConstructor
@Tag(name = "Recherche", description = "Recherche globale")
public class RechercheController {

    private final RechercheService service;

    @GetMapping
    @Operation(summary = "Rechercher partout")
    public ResponseEntity<RechercheResponse> rechercher(@RequestParam("q") String query) {
        return ResponseEntity.ok(service.rechercherPartout(query));
    }
}
