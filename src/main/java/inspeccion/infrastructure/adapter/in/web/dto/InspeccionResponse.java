package inspeccion.infrastructure.adapter.in.web.dto;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionResponse {
    private Long idInspeccion;
    private String numeroInspeccion;
    private LocalDate fechaInspeccion;
    private TipoInspeccion tipoInspeccion;
    private EstadoInspeccion estado;
    private Long idLote;
    private String codigoLote;
    private String nombreInspector;
    private String cedulaInspector;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<DetalleInspeccionResponse> detalles;
}
