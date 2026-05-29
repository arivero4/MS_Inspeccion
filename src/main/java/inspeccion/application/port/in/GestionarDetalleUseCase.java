package inspeccion.application.port.in;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;

import java.util.List;
import java.util.Optional;

public interface GestionarDetalleUseCase {
    DetalleInspeccion agregarDetalle(Long idInspeccion, DetalleInspeccion detalle);
    DetalleInspeccion actualizarDetalle(Long idDetalle, DetalleInspeccion detalle);
    void eliminarDetalle(Long idDetalle);
    Optional<DetalleInspeccion> buscarDetallePorId(Long idDetalle);
    List<DetalleInspeccion> listarDetallesPorInspeccion(Long idInspeccion);
    DetallePlaga agregarDetallePlaga(Long idDetalle, DetallePlaga plaga);
    DetallePlaga actualizarDetallePlaga(Long idDetallePlaga, DetallePlaga plaga);
    void eliminarDetallePlaga(Long idDetallePlaga);
    List<DetallePlaga> listarPlagasPorDetalle(Long idDetalle);
}
