package com.examen.musicapp.exception;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;          // Code HTTP (404, 400, 500...)
    private String message;      // Message lisible par l'humain
    private LocalDateTime timestamp;  // Quand l'erreur s'est produite
    private String path;         // Quelle route a causé l'erreur
}