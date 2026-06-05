package com.examen.musicapp.service.impl;

import com.examen.musicapp.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${app.upload.dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le dossier de stockage des MP3.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file, Long chansonId) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide.");
        }

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        
        // Validation du format MP3
        if (originalFilename == null || 
            (!originalFilename.toLowerCase().endsWith(".mp3") && 
             (contentType == null || !contentType.equals("audio/mpeg")))) {
            throw new RuntimeException("Seuls les fichiers MP3 sont autorisés.");
        }

        try {
            // Nom de fichier normalisé : chanson_ID.mp3
            String fileName = "chanson_" + chansonId + ".mp3";
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName; // On retourne le nom du fichier stocké
        } catch (IOException ex) {
            throw new RuntimeException("Erreur lors du stockage du fichier MP3.", ex);
        }
    }

    @Override
    public Path loadFile(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }
}
