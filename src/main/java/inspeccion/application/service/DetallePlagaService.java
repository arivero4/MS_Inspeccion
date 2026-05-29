package inspeccion.application.service;

import inspeccion.application.port.in.ConsultarPlagaUseCase;
import inspeccion.application.port.out.DetallePlagaRepositoryPort;
import inspeccion.application.port.out.DetalleInspeccionRepositoryPort;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.domain.service.IncidenciaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa el puerto de entrada ConsultarPlagaUseCase.
 * Orquesta consultas relacionadas con plagas fitosanitarias críticas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DetallePlagaService implements ConsultarPlagaUseCase {

    private final DetallePlagaRepositoryPort plagaRepository;
    private final DetalleInspeccionRepositoryPort detalleRepository;
    private final IncidenciaCalculator incidenciaCalculator;

    @Override
    public List<DetallePlaga> listarPlagasCriticas() {
        log.debug("Listando plagas con incidencia alta (>= 30%)");
        return plagaRepository.buscarConIncidenciaAlta();
    }

    @Override
    public String calcularRiesgoGlobal(Long idInspeccion) {
        List<inspeccion.domain.model.DetalleInspeccion> detalles =
                detalleRepository.buscarPorInspeccion(idInspeccion);
        return incidenciaCalculator.calcularSeveridadGlobal(detalles);
    }

    @Override
    public long contarPlagasUrgentesPorDetalle(Long idDetalle) {
        return plagaRepository.buscarPorDetalle(idDetalle).stream()
                .filter(DetallePlaga::requiereAccionInmediata)
                .count();
    }
}
