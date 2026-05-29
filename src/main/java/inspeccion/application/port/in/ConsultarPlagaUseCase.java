package inspeccion.application.port.in;

import inspeccion.domain.model.DetallePlaga;

import java.util.List;

/**
 * Puerto de entrada (driving port) para consultas sobre plagas detectadas.
 * Permite al exterior consultar estadísticas y alertas de plagas sin
 * conocer los detalles de implementación.
 */
public interface ConsultarPlagaUseCase {
    List<DetallePlaga> listarPlagasCriticas();
    String calcularRiesgoGlobal(Long idInspeccion);
    long contarPlagasUrgentesPorDetalle(Long idDetalle);
}