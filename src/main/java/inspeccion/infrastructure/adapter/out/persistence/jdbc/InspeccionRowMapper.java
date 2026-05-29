package inspeccion.infrastructure.adapter.out.persistence.jdbc;

import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class InspeccionRowMapper implements RowMapper<InspeccionEntity> {

    @Override
    public InspeccionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return InspeccionEntity.builder()
                .idInspeccion(rs.getLong("ID_INSPECCION"))
                .numeroInspeccion(rs.getString("NUMERO_INSPECCION"))
                .fechaInspeccion(toLocalDate(rs, "FECHA_INSPECCION"))
                .tipoInspeccion(rs.getString("TIPO_INSPECCION"))
                .estado(rs.getString("ESTADO"))
                .idLote(rs.getLong("ID_LOTE"))
                .codigoLote(rs.getString("CODIGO_LOTE"))
                .nombreInspector(rs.getString("NOMBRE_INSPECTOR"))
                .cedulaInspector(rs.getString("CEDULA_INSPECTOR"))
                .observaciones(rs.getString("OBSERVACIONES"))
                .fechaCreacion(toLocalDateTime(rs, "FECHA_CREACION"))
                .fechaActualizacion(toLocalDateTime(rs, "FECHA_ACTUALIZACION"))
                .build();
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
