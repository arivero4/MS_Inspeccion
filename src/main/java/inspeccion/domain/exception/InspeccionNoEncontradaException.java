package inspeccion.domain.exception;

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
