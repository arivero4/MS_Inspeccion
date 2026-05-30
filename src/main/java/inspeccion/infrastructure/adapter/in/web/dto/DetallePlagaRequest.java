package inspeccion.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Diccionario: detalle_plaga
 * Campos: id_detalle_plaga, plantas_afectadas (NOT NULL), incidencia (NOT NULL),
 *         id_detalle (FK NOT NULL), id_plaga (NOT NULL)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePlagaRequest {

    @NotNull(message = "El ID de la plaga es obligatorio")
    private Long plagaId;

    @NotNull(message = "El número de plantas afectadas es obligatorio")
    @Positive(message = "Las plantas afectadas deben ser mayor a cero")
    private Integer plantasAfectadas;

    @NotNull(message = "La incidencia es obligatoria")
    @Positive(message = "La incidencia debe ser un valor positivo")
    private Double incidencia;

    // Campos legacy opcionales (compatibilidad hacia atrás)
    private String nombrePlaga;
    private String nombreCientifico;
    private Double areaAfectada;
    private String observaciones;
}
