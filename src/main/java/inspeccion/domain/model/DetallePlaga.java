package inspeccion.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * Entidad de dominio que representa una plaga detectada en un muestreo.
 *
 * <p>Registra el numero de plantas afectadas por una plaga especifica
 * ({@link #idPlaga}, FK a la tabla PLAGA del esquema territorial) y el
 * porcentaje de incidencia calculado ({@link #nivelIncidencia}).</p>
 *
 * <p>Los umbrales de severidad son los del ICA Colombia:
 * BAJO {@literal <} 10%, MEDIO 10-29%, ALTO {@literal >=} 30%.</p>
 *
 * <p>Tabla Oracle: {@code DETALLE_PLAGA} con FKs {@code ID_DETALLE} e {@code ID_PLAGA}.</p>
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePlaga {

    private Long idDetallePlaga;
    private Long idDetalle;
    private Integer plantasAfectadas;
    private Double nivelIncidencia;   // maps to INCIDENCIA column
    private Long idPlaga;             // new: FK to PLAGA table

    // Fields not in new schema — kept for backward compat
    private String nombrePlaga;
    private String nombreCientifico;
    private String nivelSeveridad;
    private Double areaAfectada;
    private String accionRecomendada;
    private LocalDate fechaDeteccion;

    public boolean esIncidenciaAlta() {
        return nivelIncidencia != null && nivelIncidencia >= 30.0;
    }

    public boolean esIncidenciaMedia() {
        return nivelIncidencia != null && nivelIncidencia >= 10.0 && nivelIncidencia < 30.0;
    }

    public boolean esIncidenciaBaja() {
        return nivelIncidencia != null && nivelIncidencia < 10.0;
    }

    public boolean requiereAccionInmediata() {
        return esIncidenciaAlta() || "CUARENTENA".equalsIgnoreCase(nivelSeveridad);
    }

    public String clasificarNivelSeveridad() {
        if (nivelIncidencia == null) return "NO_DETERMINADO";
        if (nivelIncidencia >= 30.0) return "ALTO";
        if (nivelIncidencia >= 10.0) return "MEDIO";
        return "BAJO";
    }
}
