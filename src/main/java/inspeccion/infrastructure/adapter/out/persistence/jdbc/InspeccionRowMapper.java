package inspeccion.infrastructure.adapter.out.persistence.jdbc;

import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * RowMapper JDBC para la tabla {@code INSPECCION_FITOSANITARIA}.
 *
 * <p>Lee {@code ID_LOTE} primero y captura {@code rs.wasNull()} inmediatamente
 * (antes de cualquier otra lectura del ResultSet) para retornar {@code null}
 * en lugar de {@code 0} cuando la columna contiene NULL.</p>
 */

@Component
public class InspeccionRowMapper implements RowMapper<InspeccionEntity> {

    @Override
    public InspeccionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Leer ID_LOTE y wasNull antes de cualquier otra lectura del ResultSet
        long idLoteRaw = rs.getLong("ID_LOTE");
        Long idLote = rs.wasNull() ? null : idLoteRaw;

        InspeccionEntity entity = InspeccionEntity.builder()
                .idInspeccion(rs.getLong("ID_INSPECCION"))
                .codigoIca(rs.getString("CODIGO_ICA"))
                .fechaInspeccion(toLocalDate(rs, "FECHA_INSPECCION"))
                .estado(rs.getString("ESTADO"))
                .tipo(rs.getString("TIPO"))
                .idGrupo(rs.getLong("ID_GRUPO"))
                .fechaActualizacion(toLocalDateTime(rs, "FECHA_ACTUALIZACION"))
                .observaciones(rs.getString("OBSERVACIONES"))
                .idLote(idLote)
                .build();

        // Populate compatibility fields
        entity.setNumeroInspeccion(entity.getCodigoIca());
        entity.setTipoInspeccion(entity.getTipo());
        return entity;
    }

    private LocalDate toLocalDate(ResultSet rs, String column) throws SQLException {
        java.sql.Date date = rs.getDate(column);
        return date != null ? date.toLocalDate() : null;
    }

    private LocalDateTime toLocalDateTime(ResultSet rs, String column) throws SQLException {
        Timestamp ts = rs.getTimestamp(column);
        return ts != null ? ts.toLocalDateTime() : null;
    }
}
