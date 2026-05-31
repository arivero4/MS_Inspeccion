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
/**
 * Adaptador JDBC que implementa {@link inspeccion.application.port.out.DetalleInspeccionRepositoryPort}.
 *
 * <p>Gestiona la tabla {@code DETALLE_INSPECCION} con secuencia {@code SEQ_DETALLE_INSPECCION}.
 * Cada detalle pertenece a una inspeccion (FK {@code ID_INSPECCION}) y a un lote (FK {@code ID_LOTE}).</p>
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetalleInspeccionRepositoryAdapter implements DetalleInspeccionRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final DetalleInspeccionMapper mapper;

    private static final String SELECT_BASE =
        "SELECT ID_DETALLE, ID_INSPECCION, TOTAL_PLANTAS, ID_LOTE " +
        "FROM DETALLE_INSPECCION";

    private final RowMapper<inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity> rowMapper =
        (rs, rowNum) -> inspeccion.infrastructure.adapter.out.persistence.entity.DetalleInspeccionEntity.builder()
            .idDetalle(rs.getLong("ID_DETALLE"))
            .idInspeccion(rs.getLong("ID_INSPECCION"))
            .totalPlantas(rs.getObject("TOTAL_PLANTAS") != null ? rs.getInt("TOTAL_PLANTAS") : null)
            .idLote(rs.getObject("ID_LOTE") != null ? rs.getLong("ID_LOTE") : null)
            .build();

    @Override
    public DetalleInspeccion guardar(DetalleInspeccion detalle) {
        Long id = jdbcTemplate.queryForObject("SELECT SEQ_DETALLE_INSPECCION.NEXTVAL FROM DUAL", Long.class);
        jdbcTemplate.update(
            "INSERT INTO DETALLE_INSPECCION (ID_DETALLE, ID_INSPECCION, TOTAL_PLANTAS, ID_LOTE) " +
            "VALUES (?, ?, ?, ?)",
            id, detalle.getIdInspeccion(), detalle.getTotalPlantas(), detalle.getIdLote());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public DetalleInspeccion actualizar(DetalleInspeccion detalle) {
        jdbcTemplate.update(
            "UPDATE DETALLE_INSPECCION SET TOTAL_PLANTAS = ?, ID_LOTE = ? " +
            "WHERE ID_DETALLE = ?",
            detalle.getTotalPlantas(), detalle.getIdLote(), detalle.getIdDetalle());
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
