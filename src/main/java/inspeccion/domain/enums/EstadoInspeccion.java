package inspeccion.domain.enums;

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
