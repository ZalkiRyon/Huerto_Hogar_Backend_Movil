package com.backend.huertohogar.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Servicio para gestionar subida de imágenes a Cloudinary (API externa).
 * 
 * Cloudinary proporciona:
 * - Almacenamiento en la nube (CDN global)
 * - URLs permanentes y directas
 * - Transformaciones de imagen on-the-fly
 * - Plan gratuito: 25GB storage, 25GB bandwidth/mes
 */
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Sube una imagen a Cloudinary y retorna la URL segura (HTTPS).
     * 
     * @param file Archivo MultipartFile desde el frontend
     * @param folder Carpeta en Cloudinary (ej: "productos", "usuarios")
     * @return URL pública de la imagen subida
     * @throws IOException Si hay error en la subida
     */
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        // Validaciones básicas
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen");
        }

        // Upload a Cloudinary
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", folder,              // Organizar en carpetas
                "resource_type", "image",      // Tipo de recurso
                "overwrite", false,            // No sobrescribir archivos existentes
                "unique_filename", true        // Generar nombres únicos
            )
        );

        // Retornar URL segura (HTTPS)
        return (String) uploadResult.get("secure_url");
    }

    /**
     * Elimina una imagen de Cloudinary usando su public_id.
     * 
     * @param publicId ID público de la imagen (extraído de la URL)
     * @throws IOException Si hay error en la eliminación
     */
    @SuppressWarnings("unchecked")
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    /**
     * Extrae el public_id de una URL de Cloudinary.
     * Ejemplo URL: https://res.cloudinary.com/dg7dcbcjn/image/upload/v123/productos/abc123.jpg
     * Public ID: productos/abc123
     * 
     * @param imageUrl URL completa de la imagen
     * @return Public ID para operaciones de Cloudinary
     */
    public String extractPublicId(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("/upload/")) {
            return null;
        }
        
        // Extraer la parte después de /upload/v###/
        String[] parts = imageUrl.split("/upload/");
        if (parts.length < 2) {
            return null;
        }
        
        String afterUpload = parts[1];
        // Remover versión (v123456/)
        String[] versionParts = afterUpload.split("/", 2);
        if (versionParts.length < 2) {
            return null;
        }
        
        // Remover extensión (.jpg, .png, etc)
        String publicIdWithExtension = versionParts[1];
        return publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf('.'));
    }
}
