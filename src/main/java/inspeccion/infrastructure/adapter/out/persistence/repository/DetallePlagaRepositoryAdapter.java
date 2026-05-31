package inspeccion.infrastructure.adapter.out.persistence.repository;

import inspeccion.application.port.out.DetallePlagaRepositoryPort;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.mapper.DetallePlagaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Adaptador JDBC que implementa {@link inspeccion.application.port.out.DetallePlagaRepositoryPort}.
 *
 * <p>Gestiona la tabla {@code DETALLE_PLAGA} con secuencia {@code SEQ_DETALLE_PLAGA}.
 * Cada registro vincula un detalle de muestreo con una plaga del catalogo territorial
 * ({@code ID_PLAGA}), registrando las plantas afectadas y el porcentaje de incidencia.</p>
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetallePlagaRepositoryAdapter implements DetallePlagaRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final DetallePlagaMapper mapper;

    private static final String SELECT_BASE =
        "SELECT ID_DETALLE_PLAGA, ID_DETALLE, PLANTAS_AFECTADAS, INCIDENCIA, ID_PLAGA " +
        "FROM DETALLE_PLAGA";

    private final RowMapper<inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity> rowMapper =
        (rs, rowNum) -> {
            inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity e =
                inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity.builder()
                    .idDetallePlaga(rs.getLong("ID_DETALLE_PLAGA"))
                    .idDetalle(rs.getLong("ID_DETALLE"))
                    .plantasAfectadas(rs.getObject("PLANTAS_AFECTADAS") != null ? rs.getInt("PLANTAS_AFECTADAS") : null)
                    .incidencia(rs.getObject("INCIDENCIA") != null ? rs.getDouble("INCIDENCIA") : null)
                    .idPlaga(rs.getObject("ID_PLAGA") != null ? rs.getLong("ID_PLAGA") : null)
                    .build();
            // Populate compat fields
            e.setNivelIncidencia(e.getIncidencia());
            return e;
        };

    @Override
    public DetallePlaga guardar(DetallePlaga plaga) {
        Long id = jdbcTemplate.queryForObject("SELECT SEQ_DETALLE_PLAGA.NEXTVAL FROM DUAL", Long.class);
        jdbcTemplate.update(
            "INSERT INTO DETALLE_PLAGA (ID_DETALLE_PLAGA, ID_DETALLE, PLANTAS_AFECTADAS, INCIDENCIA, ID_PLAGA) " +
            "VALUES (?, ?, ?, ?, ?)",
            id, plaga.getIdDetalle(), plaga.getPlantasAfectadas(),
            plaga.getNivelIncidencia(), plaga.getIdPlaga());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public DetallePlaga actualizar(DetallePlaga plaga) {
        jdbcTemplate.update(
            "UPDATE DETALLE_PLAGA SET PLANTAS_AFECTADAS = ?, INCIDENCIA = ?, ID_PLAGA = ? " +
            "WHERE ID_DETALLE_PLAGA = ?",
            plaga.getPlantasAfectadas(), plaga.getNivelIncidencia(),
            plaga.getIdPlaga(), plaga.getIdDetallePlaga());
        return buscarPorId(plaga.getIdDetallePlaga()).orElseThrow();
    }

    @Override
    public Optional<DetallePlaga> buscarPorId(Long idDetallePlaga) {
        try {
            var entity = jdbcTemplate.queryForObject(
                SELECT_BASE + " WHERE ID_DETALLE_PLAGA = ?", rowMapper, idDetallePlaga);
            return Optional.ofNullable(entity).map(mapper::entityToDomain);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DetallePlaga> buscarPorDetalle(Long idDetalle) {
        return jdbcTemplate.query(SELECT_BASE + " WHERE ID_DETALLE = ? ORDER BY INCIDENCIA DESC",
                rowMapper, idDetalle)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<DetallePlaga> buscarConIncidenciaAlta() {
        return jdbcTemplate.query(SELECT_BASE + " WHERE INCIDENCIA >= 30 ORDER BY INCIDENCIA DESC",
                rowMapper)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long idDetallePlaga) {
        jdbcTemplate.update("DELETE FROM DETALLE_PLAGA WHERE ID_DETALLE_PLAGA = ?", idDetallePlaga);
    }

    @Override
    public void eliminarPorDetalle(Long idDetalle) {
        jdbcTemplate.update("DELETE FROM DETALLE_PLAGA WHERE ID_DETALLE = ?", idDetalle);
    }
}
