package inspeccion.domain.enums;
/**
 * Enumeracion que define el ciclo de vida de una inspeccion fitosanitaria.
 *
 * <p><b>Flujo permitido:</b></p>
 * <pre>
 *   PROGRAMADA -> EN_PROCESO -> COMPLETADA -> PENDIENTE_REVISION -> APROBADA
 *                                          -> CANCELADA (desde PROGRAMADA o EN_PROCESO)
 *                            <- EN_PROCESO  (devolucion desde PENDIENTE_REVISION)
 * </pre>
 *
 * <p>Cada constante lleva metodos de transicion guardados en la clase
 * {@link inspeccion.domain.model.InspeccionFitosanitaria} que validan
 * las transiciones permitidas antes de cambiar el estado.</p>
 */

public enum EstadoInspeccion {
    PROGRAMADA,
    EN_PROCESO,
    COMPLETADA,
    CANCELADA,
    PENDIENTE_REVISION,
    APROBADA;

    public boolean permiteAgregarDetalles() {
        return this == EN_PROCESO;
    }

    public boolean permiteCancelacion() {
        return this == PROGRAMADA || this == EN_PROCESO;
    }

    public boolean permiteCompletarse() {
        return this == EN_PROCESO;
    }

    public boolean permiteIniciarse() {
        return this == PROGRAMADA;
    }
}
