package inspeccion.application.service;

import inspeccion.application.port.in.ConsultarInspeccionUseCase;
import inspeccion.application.port.out.InspeccionRepositoryPort;
import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.exception.InspeccionNoEncontradaException;
import inspeccion.domain.model.InspeccionFitosanitaria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InspeccionQueryService implements ConsultarInspeccionUseCase {
    private final InspeccionRepositoryPort inspeccionRepository;

    @Override
    public Optional<InspeccionFitosanitaria> buscarPorId(Long idInspeccion) {
        log.debug("Buscando inspeccion por ID: {}", idInspeccion);
        return inspeccionRepository.buscarPorId(idInspeccion);
    }

    @Override
    public Optional<InspeccionFitosanitaria> buscarPorNumero(String numeroInspeccion) {
        return inspeccionRepository.buscarPorNumero(numeroInspeccion);
    }

    @Override
    public List<InspeccionFitosanitaria> listarTodas() {
        return inspeccionRepository.buscarTodas();
    }

    @Override
    public List<InspeccionFitosanitaria> listarPorEstado(EstadoInspeccion estado) {
        return inspeccionRepository.buscarPorEstado(estado);
    }

    @Override
    public List<InspeccionFitosanitaria> listarPorLote(Long idLote) {
        return inspeccionRepository.buscarPorLote(idLote);
    }

    @Override
    public List<InspeccionFitosanitaria> listarPorInspector(String cedulaInspector) {
        return inspeccionRepository.buscarPorInspector(cedulaInspector);
    }

    @Override
    public List<InspeccionFitosanitaria> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin.");
        }
        return inspeccionRepository.buscarPorPeriodo(fechaInicio, fechaFin);
    }
}
