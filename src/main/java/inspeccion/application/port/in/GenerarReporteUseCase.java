package inspeccion.application.port.in;

import inspeccion.domain.model.InspeccionFitosanitaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
/**
 * Puerto de entrada para la generacion de reportes estadisticos.
 *
 * <p>Genera resumenes por inspeccion, por lote, por periodo y alertas criticas.
 * Implementado por {@link inspeccion.application.service.ReporteService}.</p>
 */

public interface GenerarReporteUseCase {
    Map<String, Object> generarReportePorInspeccion(Long idInspeccion);
    Map<String, Object> generarReportePorLote(Long idLote, LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> generarReportePorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> generarResumenEstadistico(LocalDate fechaInicio, LocalDate fechaFin);
    List<Map<String, Object>> listarInspeccionesConAlertaCritica();
}
