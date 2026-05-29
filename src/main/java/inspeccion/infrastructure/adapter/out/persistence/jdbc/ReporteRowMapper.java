package inspeccion.infrastructure.adapter.out.persistence.jdbc;

import inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity;
import inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ReporteRowMapper implements RowMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> fila = new LinkedHashMap<>();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rs.getMetaData().getColumnLabel(i).toLowerCase();
            Object value = rs.getObject(i);
            if (value instanceof java.sql.Date) {
                value = ((java.sql.Date) value).toLocalDate();
            } else if (value instanceof Timestamp) {
                value = ((Timestamp) value).toLocalDateTime();
            }
            fila.put(columnName, value);
        }
        return fila;
    }
}
