package inspeccion.application.port.out;

import inspeccion.domain.model.DetalleInspeccion;

import java.util.List;
import java.util.Optional;

public interface DetalleInspeccionRepositoryPort {
    DetalleInspeccion guardar(DetalleInspeccion detalle);
    DetalleInspeccion actualizar(DetalleInspeccion detalle);
    Optional<DetalleInspeccion> buscarPorId(Long idDetalle);
    List<DetalleInspeccion> buscarPorInspeccion(Long idInspeccion);
    void eliminar(Long idDetalle);
    void eliminarPorInspeccion(Long idInspeccion);
}
