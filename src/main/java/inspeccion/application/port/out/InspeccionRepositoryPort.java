package inspeccion.application.port.out;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InspeccionRepositoryPort {
    InspeccionFitosanitaria guardar(InspeccionFitosanitaria inspeccion);
    InspeccionFitosanitaria actualizar(InspeccionFitosanitaria inspeccion);
    Optional<InspeccionFitosanitaria> buscarPorId(Long idInspeccion);
    Optional<InspeccionFitosanitaria> buscarPorNumero(String numeroInspeccion);
    List<InspeccionFitosanitaria> buscarTodas();
    List<InspeccionFitosanitaria> buscarPorEstado(EstadoInspeccion estado);
    List<InspeccionFitosanitaria> buscarPorLote(Long idLote);
    List<InspeccionFitosanitaria> buscarPorInspector(String cedulaInspector);
    List<InspeccionFitosanitaria> buscarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    List<InspeccionFitosanitaria> buscarConAlertaCritica();
    boolean existePorNumero(String numeroInspeccion);
    void eliminar(Long idInspeccion);
}
