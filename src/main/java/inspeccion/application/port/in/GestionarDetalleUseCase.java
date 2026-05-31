package inspeccion.application.port.in;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;

import java.util.List;
import java.util.Optional;
/**
 * Puerto de entrada para la gestion de muestreos y plagas detectadas.
 *
 * <p>Maneja el CRUD de {@link inspeccion.domain.model.DetalleInspeccion}
 * y {@link inspeccion.domain.model.DetallePlaga}.
 * Implementado por {@link inspeccion.application.service.DetalleInspeccionService}.</p>
 */

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
