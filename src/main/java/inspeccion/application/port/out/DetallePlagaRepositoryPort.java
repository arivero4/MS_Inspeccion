package inspeccion.application.port.out;

import inspeccion.domain.model.DetallePlaga;

import java.util.List;
import java.util.Optional;

public interface DetallePlagaRepositoryPort {
    DetallePlaga guardar(DetallePlaga plaga);
    DetallePlaga actualizar(DetallePlaga plaga);
    Optional<DetallePlaga> buscarPorId(Long idDetallePlaga);
    List<DetallePlaga> buscarPorDetalle(Long idDetalle);
    List<DetallePlaga> buscarConIncidenciaAlta();
    void eliminar(Long idDetallePlaga);
    void eliminarPorDetalle(Long idDetalle);
}
