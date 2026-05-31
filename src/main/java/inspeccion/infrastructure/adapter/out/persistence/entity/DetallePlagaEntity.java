package inspeccion.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
/** Entidad de persistencia para la tabla DETALLE_PLAGA (plaga detectada en muestreo). */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePlagaEntity {
    private Long idDetallePlaga;
    private Long idDetalle;
    private Integer plantasAfectadas;
    private Double incidencia;        // new: INCIDENCIA (was NIVEL_INCIDENCIA)
    private Long idPlaga;             // new: ID_PLAGA FK

    // Fields removed from new schema — kept for domain mapper compatibility
    private String nombrePlaga;
    private String nombreCientifico;
    private Double nivelIncidencia;   // same as incidencia
    private String nivelSeveridad;
    private Double areaAfectada;
    private String accionRecomendada;
    private LocalDate fechaDeteccion;
}
