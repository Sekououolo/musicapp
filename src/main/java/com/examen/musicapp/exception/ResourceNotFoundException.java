package com.examen.musicapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
// Dit à Spring : quand cette exception est lancée → code HTTP 404
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructeur pratique : génère le message automatiquement
    // ex: new ResourceNotFoundException("Artiste", 99L)
    // → "Artiste non trouvé avec l'id: 99"
    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " non trouvé(e) avec l'id: " + id);
    }
}