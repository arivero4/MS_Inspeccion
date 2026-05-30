package inspeccion.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Diccionario: detalle_inspeccion
 * Campos: id_detalle, total_plantas (NOT NULL), id_inspeccion (FK), id_lote (NOT NULL)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInspeccionRequest {

    @NotNull(message = "El total de plantas inspeccionadas es obligatorio")
    @Positive(message = "El total de plantas debe ser mayor a cero")
    private Integer totalPlantas;

    @NotNull(message = "El lote a inspeccionar es obligatorio")
    private Long idLote;

    // Campos legacy opcionales (compatibilidad hacia atrás, ignorados en nuevo esquema)
    private String nombreCultivo;
    private Double areaInspeccionada;
    private Integer plantasMuestreadas;
    private String observaciones;
}
