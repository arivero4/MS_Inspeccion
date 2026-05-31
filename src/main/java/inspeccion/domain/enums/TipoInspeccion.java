package inspeccion.domain.enums;
/**
 * Tipo de inspeccion fitosanitaria segun la urgencia y el proposito.
 *
 * <ul>
 *   <li>{@code RUTINARIA}   - Control periodico planificado.</li>
 *   <li>{@code EMERGENCIA}  - Inspeccion urgente por dano detectado. Requiere atencion inmediata.</li>
 *   <li>{@code SEGUIMIENTO} - Verificacion post-tratamiento fitosanitario.</li>
 *   <li>{@code CUARENTENA}  - Inspeccion por sospecha de plaga cuarentenaria. Alta prioridad.</li>
 * </ul>
 *
 * <p>Los tipos {@code EMERGENCIA} y {@code CUARENTENA} se consideran urgentes
 * segun el metodo {@link #esUrgente()}.</p>
 */

public enum TipoInspeccion {
    RUTINARIA,
    EMERGENCIA,
    SEGUIMIENTO,
    CUARENTENA;

    public boolean esUrgente() {
        return this == EMERGENCIA || this == CUARENTENA;
    }
}
