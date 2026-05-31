package inspeccion.domain.exception;
/**
 * Excepcion de dominio lanzada cuando no se encuentra una InspeccionFitosanitaria.
 *
 * <p>RuntimeException no verificada. Capturada por
 * {@link inspeccion.infrastructure.adapter.in.web.GlobalExceptionHandler}
 * y convertida en respuesta HTTP 404 Not Found.</p>
 */

public class InspeccionNoEncontradaException extends RuntimeException {

    private final Long idInspeccion;
    private final String numeroInspeccion;

    public InspeccionNoEncontradaException(Long idInspeccion) {
        super("Inspección no encontrada con ID: " + idInspeccion);
        this.idInspeccion = idInspeccion;
        this.numeroInspeccion = null;
    }

    public InspeccionNoEncontradaException(String numeroInspeccion) {
        super("Inspección no encontrada con número: " + numeroInspeccion);
        this.idInspeccion = null;
        this.numeroInspeccion = numeroInspeccion;
    }

    public Long getIdInspeccion() {
        return idInspeccion;
    }

    public String getNumeroInspeccion() {
        return numeroInspeccion;
    }
}
