package inspeccion.application.port.in;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.model.InspeccionFitosanitaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 * Puerto de entrada para consultas de solo lectura sobre inspecciones.
 *
 * <p>Provee busqueda por ID, numero ICA, estado, lote, inspector y periodo.
 * Implementado por {@link inspeccion.application.service.InspeccionQueryService}.</p>
 */

public interface ConsultarInspeccionUseCase {
    Optional<InspeccionFitosanitaria> buscarPorId(Long idInspeccion);
    Optional<InspeccionFitosanitaria> buscarPorNumero(String numeroInspeccion);
    List<InspeccionFitosanitaria> listarTodas();
    List<InspeccionFitosanitaria> listarPorEstado(EstadoInspeccion estado);
    List<InspeccionFitosanitaria> listarPorLote(Long idLote);
    List<InspeccionFitosanitaria> listarPorInspector(String cedulaInspector);
    List<InspeccionFitosanitaria> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}
