package com.examen.musicapp.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {
    /**
     * Sauvegarde le fichier MP3 sur le disque pour une chanson donnée.
     * @param file Le fichier MultipartFile envoyé par le client.
     * @param chansonId L'identifiant de la chanson.
     * @return Le chemin relatif du fichier stocké (pour enregistrement en base).
     */
    String storeFile(MultipartFile file, Long chansonId);

    /**
     * Récupère le chemin absolu d'un fichier à partir de son chemin relatif.
     * @param relativePath Le chemin relatif stocké en base.
     * @return Le Path absolu vers le fichier.
     */
    Path loadFile(String relativePath);
}
