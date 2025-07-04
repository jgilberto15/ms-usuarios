package pe.sa.springcloud.sed.usuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo IllegalArgumentException.
     * Estas excepciones ocurren, por ejemplo, cuando un registro no existe.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Cambiar "error" por "mensaje" en la respuesta JSON
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Código de estado 400
                .body(Map.of("mensaje", ex.getMessage())); // Formato JSON {"mensaje": "mensaje de error"}
    }

    /**
     * Maneja excepciones genéricas no controladas.
     * Esto asegura que cualquier error inesperado también tenga un formato consistente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        // Cambiar "error" por "mensaje" en la respuesta JSON
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Código de estado 500
                .body(Map.of("mensaje", "Ocurrió un error inesperado. Por favor, inténtalo más tarde."));
    }
}
