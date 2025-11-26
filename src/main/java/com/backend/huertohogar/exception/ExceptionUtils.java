package com.backend.huertohogar.exception;

import java.util.Optional;

public class ExceptionUtils {

    private ExceptionUtils() {
        // Constructor privado para evitar instancias
    }

    public static <T> T orThrow(Optional<T> optional, String entityName) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(entityName + " no encontrado"));
    }

    public static <T> T orThrow2(Optional<T> optional, String entityName) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(entityName + " no encontrada"));
    }
}
