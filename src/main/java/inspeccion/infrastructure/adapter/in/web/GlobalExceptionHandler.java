package inspeccion.infrastructure.adapter.in.web;

import inspeccion.domain.exception.DetalleNoEncontradoException;
import inspeccion.domain.exception.InspeccionNoEncontradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Manejador global de excepciones para ms-inspeccion.
 *
 * <p>Captura excepciones de dominio y las convierte en respuestas HTTP estructuradas:</p>
 * <ul>
 *   <li>{@code InspeccionNoEncontradaException} / {@code DetalleNoEncontradoException} -> HTTP 404.</li>
 *   <li>{@code IllegalStateException} (transicion de estado invalida) -> HTTP 400.</li>
 *   <li>{@code IllegalArgumentException} (datos invalidos) -> HTTP 400.</li>
 *   <li>Otras excepciones -> HTTP 500.</li>
 * </ul>
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InspeccionNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleInspeccionNoEncontrada(InspeccionNoEncontradaException ex) {
        log.warn("Inspeccion no encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(DetalleNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleDetalleNoEncontrado(DetalleNoEncontradoException ex) {
        log.warn("Detalle no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        log.warn("Error de estado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento invalido: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            errores.put(campo, error.getDefaultMessage());
        });
        Map<String, Object> body = buildError(HttpStatus.BAD_REQUEST, "Error de validacion en los datos enviados");
        body.put("erroresValidacion", errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"));
    }

    private Map<String, Object> buildError(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensaje", message);
        return body;
    }
}
