package inspeccion.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInspeccionResponse {
    private Long idDetalle;
    private Long idInspeccion;
    private String nombreCultivo;
    private Double areaInspeccionada;
    private Integer totalPlantas;
    private Integer plantasMuestreadas;
    private Double porcentajeMuestreado;
    private String resultado;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private List<DetallePlagaResponse> plagas;
}
