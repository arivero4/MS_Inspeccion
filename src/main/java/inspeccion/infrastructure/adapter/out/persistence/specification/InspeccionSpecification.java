package inspeccion.infrastructure.adapter.out.persistence.specification;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
/**
 * Especificaciones de filtros dinamicos para consultas de inspecciones.
 * Usadas en combinacion con JdbcTemplate para construir WHERE clauses dinamicas.
 */

@Data
@Builder
public class InspeccionSpecification {
    private EstadoInspeccion estado;
    private TipoInspeccion tipo;
    private Long idLote;
    private String cedulaInspector;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double nivelIncidenciaMinimo;

    public String buildWhereClause() {
        StringBuilder sb = new StringBuilder(" WHERE 1=1");
        if (estado != null) sb.append(" AND i.ESTADO = '").append(estado.name()).append("'");
        if (tipo != null) sb.append(" AND i.TIPO = '").append(tipo.name()).append("'");
        // ID_LOTE moved to DETALLE_INSPECCION in new schema — skip for now
        // CEDULA_INSPECTOR removed from new schema — skip
        if (fechaInicio != null) sb.append(" AND i.FECHA_INSPECCION >= DATE '").append(fechaInicio).append("'");
        if (fechaFin != null) sb.append(" AND i.FECHA_INSPECCION <= DATE '").append(fechaFin).append("'");
        return sb.toString();
    }
}
