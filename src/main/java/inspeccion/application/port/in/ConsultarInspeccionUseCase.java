package inspeccion.application.port.in;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultarInspeccionUseCase {
    Optional<InspeccionFitosanitaria> buscarPorId(Long idInspeccion);
    Optional<InspeccionFitosanitaria> buscarPorNumero(String numeroInspeccion);
    List<InspeccionFitosanitaria> listarTodas();
    List<InspeccionFitosanitaria> listarPorEstado(EstadoInspeccion estado);
    List<InspeccionFitosanitaria> listarPorLote(Long idLote);
    List<InspeccionFitosanitaria> listarPorInspector(String cedulaInspector);
    List<InspeccionFitosanitaria> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}
