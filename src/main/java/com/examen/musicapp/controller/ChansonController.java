package com.examen.musicapp.controller;

import com.examen.musicapp.dto.ChansonRequest;
import com.examen.musicapp.dto.ChansonResponse;
import com.examen.musicapp.service.ChansonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    // GET /api/v1/chansons/top10
    @GetMapping("/top10")
    @Operation(summary = "Récupérer le top 10 des chansons les plus écoutées")
    public ResponseEntity<List<ChansonResponse>> getTop10() {
        return ResponseEntity.ok(service.getTop10());
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

    // POST /api/v1/chansons/1/upload
    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Uploader un fichier MP3 pour une chanson")
    public ResponseEntity<ChansonResponse> uploadMp3(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadMp3(id, file));
    }

    // GET /api/v1/chansons/1/stream
    @GetMapping("/{id}/stream")
    @Operation(summary = "Streamer le fichier MP3 d'une chanson")
    public ResponseEntity<?> streamMp3(@PathVariable Long id, @RequestHeader HttpHeaders headers) throws IOException {
        Resource resource = service.getAudioFile(id);
        long contentLength = resource.contentLength();
        List<HttpRange> ranges = headers.getRange();
        MediaType audioMpeg = MediaType.parseMediaType("audio/mpeg");

        if (ranges.isEmpty()) {
            return ResponseEntity.ok()
                    .contentType(audioMpeg)
                    .contentLength(contentLength)
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }

        ResourceRegion region = ranges.get(0).toResourceRegion(resource);
        long start = region.getPosition();
        long end = Math.min(start + region.getCount() - 1, contentLength - 1);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(audioMpeg)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + contentLength)
                .body(region);
    }
}
