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
    private String codigoIca;          // new: CODIGO_ICA (was NUMERO_INSPECCION)
    private LocalDate fechaInspeccion;
    private String estado;
    private String tipo;               // new: TIPO (was TIPO_INSPECCION)
    private Long idGrupo;              // new: ID_GRUPO NOT NULL
    private LocalDateTime fechaActualizacion;
    private String observaciones;

    // Fields removed from new schema — kept for domain mapper compatibility
    private String numeroInspeccion;   // derived from codigoIca
    private Long idLote;
    private String codigoLote;
    private String nombreInspector;
    private String cedulaInspector;
    private String tipoInspeccion;     // same as tipo
    private LocalDateTime fechaCreacion;
}
