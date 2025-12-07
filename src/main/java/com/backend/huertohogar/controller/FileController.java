package com.backend.huertohogar.controller;

import com.backend.huertohogar.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    // Endpoint para devolver el archivo físico
    @GetMapping("/download/{fileName:.+}") // El regex permite que el punto del nombre del archivo no se trunque
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {

        // Cargar el archivo como recurso
        Path filePath = fileStorageService.loadFileAsResource(fileName);
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

        // Determinar el tipo de contenido (ej: image/jpeg)
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Devolver la respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
