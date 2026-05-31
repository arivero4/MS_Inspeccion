package inspeccion.application.port.out;

import inspeccion.domain.model.DetalleInspeccion;

import java.util.List;
import java.util.Optional;
/**
 * Puerto de salida para la persistencia de muestreos de campo.
 *
 * <p>Implementado por
 * {@link inspeccion.infrastructure.adapter.out.persistence.repository.DetalleInspeccionRepositoryAdapter}
 * usando JDBC puro sobre Oracle.</p>
 */

public interface DetalleInspeccionRepositoryPort {
    DetalleInspeccion guardar(DetalleInspeccion detalle);
    DetalleInspeccion actualizar(DetalleInspeccion detalle);
    Optional<DetalleInspeccion> buscarPorId(Long idDetalle);
    List<DetalleInspeccion> buscarPorInspeccion(Long idInspeccion);
    void eliminar(Long idDetalle);
    void eliminarPorInspeccion(Long idInspeccion);
}
