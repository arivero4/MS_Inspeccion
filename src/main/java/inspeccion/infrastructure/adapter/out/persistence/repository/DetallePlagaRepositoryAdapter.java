package inspeccion.infrastructure.adapter.out.persistence.repository;

import inspeccion.application.port.out.DetallePlagaRepositoryPort;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.infrastructure.adapter.out.persistence.mapper.DetallePlagaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetallePlagaRepositoryAdapter implements DetallePlagaRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final DetallePlagaMapper mapper;

    private static final String SELECT_BASE =
        "SELECT ID_DETALLE_PLAGA, ID_DETALLE, NOMBRE_PLAGA, NOMBRE_CIENTIFICO, " +
        "PLANTAS_AFECTADAS, NIVEL_INCIDENCIA, NIVEL_SEVERIDAD, AREA_AFECTADA, " +
        "ACCION_RECOMENDADA, FECHA_DETECCION FROM DETALLE_PLAGA";

    private final RowMapper<inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity> rowMapper =
        (rs, rowNum) -> inspeccion.infrastructure.adapter.out.persistence.entity.DetallePlagaEntity.builder()
            .idDetallePlaga(rs.getLong("ID_DETALLE_PLAGA"))
            .idDetalle(rs.getLong("ID_DETALLE"))
            .nombrePlaga(rs.getString("NOMBRE_PLAGA"))
            .nombreCientifico(rs.getString("NOMBRE_CIENTIFICO"))
            .plantasAfectadas(rs.getInt("PLANTAS_AFECTADAS"))
            .nivelIncidencia(rs.getDouble("NIVEL_INCIDENCIA"))
            .nivelSeveridad(rs.getString("NIVEL_SEVERIDAD"))
            .areaAfectada(rs.getDouble("AREA_AFECTADA"))
            .accionRecomendada(rs.getString("ACCION_RECOMENDADA"))
            .fechaDeteccion(rs.getDate("FECHA_DETECCION") != null
                ? rs.getDate("FECHA_DETECCION").toLocalDate() : null)
            .build();

    @Override
    public DetallePlaga guardar(DetallePlaga plaga) {
        Long id = jdbcTemplate.queryForObject("SELECT SEQ_DETALLE_PLAGA.NEXTVAL FROM DUAL", Long.class);
        jdbcTemplate.update(
            "INSERT INTO DETALLE_PLAGA (ID_DETALLE_PLAGA, ID_DETALLE, NOMBRE_PLAGA, NOMBRE_CIENTIFICO, " +
            "PLANTAS_AFECTADAS, NIVEL_INCIDENCIA, NIVEL_SEVERIDAD, AREA_AFECTADA, ACCION_RECOMENDADA, FECHA_DETECCION) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            id, plaga.getIdDetalle(), plaga.getNombrePlaga(), plaga.getNombreCientifico(),
            plaga.getPlantasAfectadas(), plaga.getNivelIncidencia(), plaga.getNivelSeveridad(),
            plaga.getAreaAfectada(), plaga.getAccionRecomendada(),
            plaga.getFechaDeteccion() != null ? Date.valueOf(plaga.getFechaDeteccion()) : null);
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public DetallePlaga actualizar(DetallePlaga plaga) {
        jdbcTemplate.update(
            "UPDATE DETALLE_PLAGA SET NOMBRE_PLAGA = ?, NOMBRE_CIENTIFICO = ?, PLANTAS_AFECTADAS = ?, " +
            "NIVEL_INCIDENCIA = ?, NIVEL_SEVERIDAD = ?, AREA_AFECTADA = ?, ACCION_RECOMENDADA = ?, FECHA_DETECCION = ? " +
            "WHERE ID_DETALLE_PLAGA = ?",
            plaga.getNombrePlaga(), plaga.getNombreCientifico(), plaga.getPlantasAfectadas(),
            plaga.getNivelIncidencia(), plaga.getNivelSeveridad(), plaga.getAreaAfectada(),
            plaga.getAccionRecomendada(),
            plaga.getFechaDeteccion() != null ? Date.valueOf(plaga.getFechaDeteccion()) : null,
            plaga.getIdDetallePlaga());
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
        return jdbcTemplate.query(SELECT_BASE + " WHERE ID_DETALLE = ? ORDER BY NIVEL_INCIDENCIA DESC",
                rowMapper, idDetalle)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<DetallePlaga> buscarConIncidenciaAlta() {
        return jdbcTemplate.query(SELECT_BASE + " WHERE NIVEL_INCIDENCIA >= 30 ORDER BY NIVEL_INCIDENCIA DESC",
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
