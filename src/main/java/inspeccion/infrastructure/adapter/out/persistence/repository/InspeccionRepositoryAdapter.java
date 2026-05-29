package inspeccion.infrastructure.adapter.out.persistence.repository;

import inspeccion.application.port.out.InspeccionRepositoryPort;
import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.infrastructure.adapter.out.persistence.entity.InspeccionEntity;
import inspeccion.infrastructure.adapter.out.persistence.jdbc.InspeccionRowMapper;
import inspeccion.infrastructure.adapter.out.persistence.mapper.InspeccionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InspeccionRepositoryAdapter implements InspeccionRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;
    private final InspeccionRowMapper rowMapper;
    private final InspeccionMapper mapper;

    private static final String SELECT_BASE =
        "SELECT ID_INSPECCION, NUMERO_INSPECCION, FECHA_INSPECCION, TIPO_INSPECCION, ESTADO, " +
        "ID_LOTE, CODIGO_LOTE, NOMBRE_INSPECTOR, CEDULA_INSPECTOR, OBSERVACIONES, " +
        "FECHA_CREACION, FECHA_ACTUALIZACION FROM INSPECCION_FITOSANITARIA";

    @Override
    public InspeccionFitosanitaria guardar(InspeccionFitosanitaria inspeccion) {
        Long id = jdbcTemplate.queryForObject("SELECT SEQ_INSPECCION.NEXTVAL FROM DUAL", Long.class);
        String sql = "INSERT INTO INSPECCION_FITOSANITARIA " +
            "(ID_INSPECCION, NUMERO_INSPECCION, FECHA_INSPECCION, TIPO_INSPECCION, ESTADO, " +
            "ID_LOTE, CODIGO_LOTE, NOMBRE_INSPECTOR, CEDULA_INSPECTOR, OBSERVACIONES, FECHA_CREACION, FECHA_ACTUALIZACION) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime ahora = LocalDateTime.now();
        jdbcTemplate.update(sql, id,
            inspeccion.getNumeroInspeccion(),
            Date.valueOf(inspeccion.getFechaInspeccion()),
            inspeccion.getTipoInspeccion().name(),
            inspeccion.getEstado().name(),
            inspeccion.getIdLote(),
            inspeccion.getCodigoLote(),
            inspeccion.getNombreInspector(),
            inspeccion.getCedulaInspector(),
            inspeccion.getObservaciones(),
            Timestamp.valueOf(ahora),
            Timestamp.valueOf(ahora));

        return buscarPorId(id).orElseThrow();
    }

    @Override
    public InspeccionFitosanitaria actualizar(InspeccionFitosanitaria inspeccion) {
        String sql = "UPDATE INSPECCION_FITOSANITARIA SET " +
            "FECHA_INSPECCION = ?, TIPO_INSPECCION = ?, ESTADO = ?, " +
            "NOMBRE_INSPECTOR = ?, CEDULA_INSPECTOR = ?, OBSERVACIONES = ?, FECHA_ACTUALIZACION = ? " +
            "WHERE ID_INSPECCION = ?";

        jdbcTemplate.update(sql,
            Date.valueOf(inspeccion.getFechaInspeccion()),
            inspeccion.getTipoInspeccion().name(),
            inspeccion.getEstado().name(),
            inspeccion.getNombreInspector(),
            inspeccion.getCedulaInspector(),
            inspeccion.getObservaciones(),
            Timestamp.valueOf(LocalDateTime.now()),
            inspeccion.getIdInspeccion());

        return buscarPorId(inspeccion.getIdInspeccion()).orElseThrow();
    }

    @Override
    public Optional<InspeccionFitosanitaria> buscarPorId(Long idInspeccion) {
        try {
            InspeccionEntity entity = jdbcTemplate.queryForObject(
                SELECT_BASE + " WHERE ID_INSPECCION = ?", rowMapper, idInspeccion);
            return Optional.ofNullable(entity).map(mapper::entityToDomain);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<InspeccionFitosanitaria> buscarPorNumero(String numeroInspeccion) {
        try {
            InspeccionEntity entity = jdbcTemplate.queryForObject(
                SELECT_BASE + " WHERE NUMERO_INSPECCION = ?", rowMapper, numeroInspeccion);
            return Optional.ofNullable(entity).map(mapper::entityToDomain);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<InspeccionFitosanitaria> buscarTodas() {
        return jdbcTemplate.query(SELECT_BASE + " ORDER BY FECHA_CREACION DESC", rowMapper)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionFitosanitaria> buscarPorEstado(EstadoInspeccion estado) {
        return jdbcTemplate.query(SELECT_BASE + " WHERE ESTADO = ? ORDER BY FECHA_INSPECCION DESC",
                rowMapper, estado.name())
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionFitosanitaria> buscarPorLote(Long idLote) {
        return jdbcTemplate.query(SELECT_BASE + " WHERE ID_LOTE = ? ORDER BY FECHA_INSPECCION DESC",
                rowMapper, idLote)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionFitosanitaria> buscarPorInspector(String cedulaInspector) {
        return jdbcTemplate.query(SELECT_BASE + " WHERE CEDULA_INSPECTOR = ? ORDER BY FECHA_INSPECCION DESC",
                rowMapper, cedulaInspector)
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionFitosanitaria> buscarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return jdbcTemplate.query(
                SELECT_BASE + " WHERE FECHA_INSPECCION BETWEEN ? AND ? ORDER BY FECHA_INSPECCION DESC",
                rowMapper, Date.valueOf(fechaInicio), Date.valueOf(fechaFin))
                .stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionFitosanitaria> buscarConAlertaCritica() {
        String sql = SELECT_BASE +
            " WHERE ID_INSPECCION IN (" +
            "  SELECT DISTINCT i.ID_INSPECCION FROM INSPECCION_FITOSANITARIA i" +
            "  JOIN DETALLE_INSPECCION di ON i.ID_INSPECCION = di.ID_INSPECCION" +
            "  JOIN DETALLE_PLAGA dp ON di.ID_DETALLE = dp.ID_DETALLE" +
            "  WHERE dp.NIVEL_INCIDENCIA >= 30" +
            ") ORDER BY FECHA_INSPECCION DESC";
        return jdbcTemplate.query(sql, rowMapper).stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existePorNumero(String numeroInspeccion) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(1) FROM INSPECCION_FITOSANITARIA WHERE NUMERO_INSPECCION = ?",
            Integer.class, numeroInspeccion);
        return count != null && count > 0;
    }

    @Override
    public void eliminar(Long idInspeccion) {
        jdbcTemplate.update("DELETE FROM INSPECCION_FITOSANITARIA WHERE ID_INSPECCION = ?", idInspeccion);
    }
}
