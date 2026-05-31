package inspeccion.domain.exception;
/**
 * Excepcion de dominio lanzada cuando no se encuentra un DetalleInspeccion o DetallePlaga.
 *
 * <p>RuntimeException no verificada. Convertida en HTTP 404 por el GlobalExceptionHandler.</p>
 */

public class DetalleNoEncontradoException extends RuntimeException {

    private final Long idDetalle;

    public DetalleNoEncontradoException(Long idDetalle) {
        super("Detalle de inspección no encontrado con ID: " + idDetalle);
        this.idDetalle = idDetalle;
    }

    public DetalleNoEncontradoException(Long idDetalle, String contexto) {
        super("Detalle de inspección no encontrado con ID: " + idDetalle + " [" + contexto + "]");
        this.idDetalle = idDetalle;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }
}
