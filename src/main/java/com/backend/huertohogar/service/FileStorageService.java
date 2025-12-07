package com.backend.huertohogar.service;

import com.backend.huertohogar.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        System.out.println("Ruta de almacenamiento de archivos: " + this.fileStorageLocation.toString());
        // Crear el directorio si no existe
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento.", ex);
        }
    }


    public String storeFile(MultipartFile file) {
        // Normalizar el nombre de archivo (evita caracteres especiales)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            // Podrías lanzar una FileTypeNotAllowedException
            throw new RuntimeException("Tipo de archivo no soportado: " + contentType);
        }

        // Generar un nombre de archivo único (UUID)
        String filename = UUID.randomUUID().toString() + fileExtension;

        try {
            // Verificar si el archivo está vacío
            if (file.isEmpty()) {
                throw new RuntimeException("Fallo al almacenar un archivo vacío.");
            }

            // Copiar el archivo al destino
            Path targetLocation = this.fileStorageLocation.resolve(filename);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                   return filename;
        } catch (IOException ex) {


            throw new RuntimeException("Error al escribir el archivo: " + ex.getMessage(), ex);
        }
    }

    public Path loadFileAsResource(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }

}
