package inspeccion.application.service;

import inspeccion.application.port.in.GenerarReporteUseCase;
import inspeccion.application.port.out.DetalleInspeccionRepositoryPort;
import inspeccion.application.port.out.DetallePlagaRepositoryPort;
import inspeccion.application.port.out.InspeccionRepositoryPort;
import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.domain.service.IncidenciaCalculator;
import inspeccion.domain.exception.InspeccionNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Servicio de aplicacion que implementa {@link inspeccion.application.port.in.GenerarReporteUseCase}.
 *
 * <p>Genera reportes estadisticos sobre inspecciones: resumen por inspeccion,
 * por lote, por periodo y listado de alertas criticas (incidencia {@literal >=} 30%).</p>
 *
 * <p>Usa {@link inspeccion.domain.service.IncidenciaCalculator} para calcular
 * el indicador de riesgo global de cada inspeccion.</p>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteService implements GenerarReporteUseCase {
    private final InspeccionRepositoryPort inspeccionRepository;
    private final DetalleInspeccionRepositoryPort detalleRepository;
    private final DetallePlagaRepositoryPort plagaRepository;
    private final IncidenciaCalculator incidenciaCalculator;

    @Override
    public Map<String, Object> generarReportePorInspeccion(Long idInspeccion) {
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        List<DetalleInspeccion> detalles = detalleRepository.buscarPorInspeccion(idInspeccion);

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("idInspeccion", inspeccion.getIdInspeccion());
        reporte.put("numeroInspeccion", inspeccion.getNumeroInspeccion());
        reporte.put("fechaInspeccion", inspeccion.getFechaInspeccion());
        reporte.put("tipo", inspeccion.getTipoInspeccion());
        reporte.put("estado", inspeccion.getEstado());
        reporte.put("idLote", inspeccion.getIdLote());
        reporte.put("codigoLote", inspeccion.getCodigoLote());
        reporte.put("inspector", inspeccion.getNombreInspector());
        reporte.put("totalDetalles", detalles.size());
        reporte.put("totalPlagas", detalles.stream().mapToLong(d -> d.getPlagas() != null ? d.getPlagas().size() : 0).sum());
        reporte.put("severidadGlobal", incidenciaCalculator.calcularSeveridadGlobal(detalles));
        reporte.put("indicadorRiesgo", incidenciaCalculator.calcularIndicadorRiesgo(detalles));
        reporte.put("requiereIntervencion", inspeccion.tieneDeteccionesAltas());
        reporte.put("detalles", construirResumenDetalles(detalles));
        return reporte;
    }

    @Override
    public Map<String, Object> generarReportePorLote(Long idLote, LocalDate fechaInicio, LocalDate fechaFin) {
        List<InspeccionFitosanitaria> inspecciones = inspeccionRepository.buscarPorLote(idLote).stream()
                .filter(i -> !i.getFechaInspeccion().isBefore(fechaInicio) && !i.getFechaInspeccion().isAfter(fechaFin))
                .collect(Collectors.toList());

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("idLote", idLote);
        reporte.put("periodoInicio", fechaInicio);
        reporte.put("periodoFin", fechaFin);
        reporte.put("totalInspecciones", inspecciones.size());
        reporte.put("inspeccionesCompletadas", inspecciones.stream().filter(i -> "COMPLETADA".equals(i.getEstado().name())).count());
        reporte.put("inspecciones", construirResumenInspecciones(inspecciones));
        return reporte;
    }

    @Override
    public Map<String, Object> generarReportePorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<InspeccionFitosanitaria> inspecciones = inspeccionRepository.buscarPorPeriodo(fechaInicio, fechaFin);
        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("periodoInicio", fechaInicio);
        reporte.put("periodoFin", fechaFin);
        reporte.put("totalInspecciones", inspecciones.size());
        reporte.put("porEstado", agruparPorEstado(inspecciones));
        reporte.put("porTipo", agruparPorTipo(inspecciones));
        return reporte;
    }

    @Override
    public Map<String, Object> generarResumenEstadistico(LocalDate fechaInicio, LocalDate fechaFin) {
        List<InspeccionFitosanitaria> inspecciones = inspeccionRepository.buscarPorPeriodo(fechaInicio, fechaFin);
        List<DetallePlaga> plagasCriticas = plagaRepository.buscarConIncidenciaAlta();

        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("totalInspecciones", inspecciones.size());
        resumen.put("totalPlagasCriticas", plagasCriticas.size());
        resumen.put("porEstado", agruparPorEstado(inspecciones));
        resumen.put("porTipo", agruparPorTipo(inspecciones));
        return resumen;
    }

    @Override
    public List<Map<String, Object>> listarInspeccionesConAlertaCritica() {
        return inspeccionRepository.buscarConAlertaCritica().stream()
                .map(i -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("idInspeccion", i.getIdInspeccion());
                    item.put("numeroInspeccion", i.getNumeroInspeccion());
                    item.put("idLote", i.getIdLote());
                    item.put("fechaInspeccion", i.getFechaInspeccion());
                    item.put("estado", i.getEstado());
                    return item;
                }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> construirResumenDetalles(List<DetalleInspeccion> detalles) {
        return detalles.stream().map(d -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idDetalle", d.getIdDetalle());
            m.put("cultivo", d.getNombreCultivo());
            m.put("area", d.getAreaInspeccionada());
            m.put("incidenciaPromedio", d.calcularIncidenciaPromedio());
            m.put("plagasCriticas", d.contarPlagasCriticas());
            return m;
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> construirResumenInspecciones(List<InspeccionFitosanitaria> inspecciones) {
        return inspecciones.stream().map(i -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", i.getIdInspeccion());
            m.put("numero", i.getNumeroInspeccion());
            m.put("fecha", i.getFechaInspeccion());
            m.put("estado", i.getEstado());
            return m;
        }).collect(Collectors.toList());
    }

    private Map<String, Long> agruparPorEstado(List<InspeccionFitosanitaria> inspecciones) {
        return inspecciones.stream().collect(Collectors.groupingBy(i -> i.getEstado().name(), Collectors.counting()));
    }

    private Map<String, Long> agruparPorTipo(List<InspeccionFitosanitaria> inspecciones) {
        return inspecciones.stream().collect(Collectors.groupingBy(i -> i.getTipoInspeccion().name(), Collectors.counting()));
    }
}
