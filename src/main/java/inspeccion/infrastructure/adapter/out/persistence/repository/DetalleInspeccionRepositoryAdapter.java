package inspeccion.infrastructure.adapter.out.persistence.repository;

import inspeccion.application.port.out.DetalleInspeccionRepositoryPort;
import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.infrastructure.adapter.out.persistence.mapper.DetalleInspeccionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetalleInspeccionRepositoryAdapter implements DetalleInspeccionRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final DetalleInspeccionMapper mapper;

    private static final String SELECT_BASE =
        "SELECT ID_DETALLE, ID_INSPECCION, NOMBRE_CULTIVO, AREA_INSPECCIONADA, " +
        "TOTAL_PLANTAS, PLANTAS_MUESTREADAS, RESULTADO, OBSERVACIONES, FECHA_CREACION " +
        "FROM DETALLE_INSPECCION";

    private final RowMapper<inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity> rowMapper =
        (rs, rowNum) -> inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity.builder()
            .idDetalle(rs.getLong("ID_DETALLE"))
            .idInspeccion(rs.getLong("ID_INSPECCION"))
            .nombreCultivo(rs.getString("NOMBRE_CULTIVO"))
            .areaInspeccionada(rs.getDouble("AREA_INSPECCIONADA"))
            .totalPlantas(rs.getInt("TOTAL_PLANTAS"))
            .plantasMuestreadas(rs.getInt("PLANTAS_MUESTREADAS"))
            .resultado(rs.getString("RESULTADO"))
            .observaciones(rs.getString("OBSERVACIONES"))
            .fechaCreacion(rs.getTimestamp("FECHA_CREACION") != null
                ? rs.getTimestamp("FECHA_CREACION").toLocalDateTime() : null)
            .build();

    @Override
    public DetalleInspeccion guardar(DetalleInspeccion detalle) {
        Long id = jdbcTemplate.queryForObject("SELECT SEQ_DETALLE_INSPECCION.NEXTVAL FROM DUAL", Long.class);
        jdbcTemplate.update(
            "INSERT INTO DETALLE_INSPECCION (ID_DETALLE, ID_INSPECCION, NOMBRE_CULTIVO, " +
            "AREA_INSPECCIONADA, TOTAL_PLANTAS, PLANTAS_MUESTREADAS, RESULTADO, OBSERVACIONES, FECHA_CREACION) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            id, detalle.getIdInspeccion(), detalle.getNombreCultivo(),
            detalle.getAreaInspeccionada(), detalle.getTotalPlantas(), detalle.getPlantasMuestreadas(),
            detalle.getResultado(), detalle.getObservaciones(),
            Timestamp.valueOf(LocalDateTime.now()));
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public DetalleInspeccion actualizar(DetalleInspeccion detalle) {
        jdbcTemplate.update(
            "UPDATE DETALLE_INSPECCION SET NOMBRE_CULTIVO = ?, AREA_INSPECCIONADA = ?, " +
            "TOTAL_PLANTAS = ?, PLANTAS_MUESTREADAS = ?, RESULTADO = ?, OBSERVACIONES = ? " +
            "WHERE ID_DETALLE = ?",
            detalle.getNombreCultivo(), detalle.getAreaInspeccionada(),
            detalle.getTotalPlantas(), detalle.getPlantasMuestreadas(),
            detalle.getResultado(), detalle.getObservaciones(),
            detalle.getIdDetalle());
        return buscarPorId(detalle.getIdDetalle()).orElseThrow();
    }

    @Override
    public Optional<DetalleInspeccion> buscarPorId(Long idDetalle) {
        try {
            var entity = jdbcTemplate.queryForObject(SELECT_BASE + " WHERE ID_DETALLE = ?", rowMapper, idDetalle);
            return Optional.ofNullable(entity).map(mapper::entityToDomain);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DetalleInspeccion> buscarPorInspeccion(Long idInspeccion) {
        return jdbcTemplate.query(SELECT_BASE + " WHERE ID_INSPECCION = ? ORDER BY ID_DETALLE",
                rowMapper, idInspeccion)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long idDetalle) {
        jdbcTemplate.update("DELETE FROM DETALLE_INSPECCION WHERE ID_DETALLE = ?", idDetalle);
    }

    @Override
    public void eliminarPorInspeccion(Long idInspeccion) {
        jdbcTemplate.update("DELETE FROM DETALLE_INSPECCION WHERE ID_INSPECCION = ?", idInspeccion);
    }
}
