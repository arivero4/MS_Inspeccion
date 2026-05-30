package inspeccion.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInspeccionEntity {
    private Long idDetalle;
    private Long idInspeccion;
    private Integer totalPlantas;
    private Long idLote;              // new: ID_LOTE

    // Fields removed from new schema — kept for domain mapper compatibility
    private String nombreCultivo;
    private Double areaInspeccionada;
    private Integer plantasMuestreadas;
    private String resultado;
    private String observaciones;
    private LocalDateTime fechaCreacion;
}
