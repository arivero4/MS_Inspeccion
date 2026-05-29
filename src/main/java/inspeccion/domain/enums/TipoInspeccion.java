package inspeccion.domain.enums;

public enum TipoInspeccion {
    RUTINARIA,
    EMERGENCIA,
    SEGUIMIENTO,
    CUARENTENA;

    public boolean esUrgente() {
        return this == EMERGENCIA || this == CUARENTENA;
    }
}
