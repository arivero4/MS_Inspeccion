package inspeccion.infrastructure.adapter.in.web;

import inspeccion.application.port.in.GenerarReporteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
/**
 * Controlador REST para la generacion de reportes fitosanitarios.
 *
 * <p>Base URL: {@code /api/v1/reportes}. Provee reportes por inspeccion,
 * por lote y por periodo con estadisticas de incidencia de plagas.</p>
 */

@Slf4j
@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Generación de reportes de inspecciones fitosanitarias")
public class ReporteController {

    private final GenerarReporteUseCase reporteUseCase;

    @GetMapping("/inspeccion/{idInspeccion}")
    @Operation(summary = "Generar reporte detallado de una inspección")
    public ResponseEntity<Map<String, Object>> reportePorInspeccion(@PathVariable Long idInspeccion) {
        return ResponseEntity.ok(reporteUseCase.generarReportePorInspeccion(idInspeccion));
    }

    @GetMapping("/lote/{idLote}")
    @Operation(summary = "Generar reporte de inspecciones por lote y periodo")
    public ResponseEntity<Map<String, Object>> reportePorLote(
            @PathVariable Long idLote,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteUseCase.generarReportePorLote(idLote, fechaInicio, fechaFin));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Generar reporte por periodo de tiempo")
    public ResponseEntity<Map<String, Object>> reportePorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteUseCase.generarReportePorPeriodo(fechaInicio, fechaFin));
    }

    @GetMapping("/estadistico")
    @Operation(summary = "Generar resumen estadístico de inspecciones")
    public ResponseEntity<Map<String, Object>> resumenEstadistico(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteUseCase.generarResumenEstadistico(fechaInicio, fechaFin));
    }

    @GetMapping("/alertas-criticas")
    @Operation(summary = "Listar inspecciones con alertas críticas de plagas")
    public ResponseEntity<List<Map<String, Object>>> alertasCriticas() {
        return ResponseEntity.ok(reporteUseCase.listarInspeccionesConAlertaCritica());
    }
}
