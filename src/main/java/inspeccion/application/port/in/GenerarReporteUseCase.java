package inspeccion.application.port.in;

import inspeccion.domain.model.InspeccionFitosanitaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GenerarReporteUseCase {
    Map<String, Object> generarReportePorInspeccion(Long idInspeccion);
    Map<String, Object> generarReportePorLote(Long idLote, LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> generarReportePorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> generarResumenEstadistico(LocalDate fechaInicio, LocalDate fechaFin);
    List<Map<String, Object>> listarInspeccionesConAlertaCritica();
}
