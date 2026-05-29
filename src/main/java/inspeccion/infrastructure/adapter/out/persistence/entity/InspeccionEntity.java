package inspeccion.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionEntity {
    private Long idInspeccion;
    private String numeroInspeccion;
    private LocalDate fechaInspeccion;
    private String tipoInspeccion;
    private String estado;
    private Long idLote;
    private String codigoLote;
    private String nombreInspector;
    private String cedulaInspector;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
