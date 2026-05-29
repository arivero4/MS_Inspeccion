package inspeccion.application.service;

import inspeccion.application.port.in.RegistrarInspeccionUseCase;
import inspeccion.application.port.out.InspeccionRepositoryPort;
import inspeccion.application.dto.LoteInfo;
import inspeccion.application.port.out.TerritorialClientPort;
import inspeccion.domain.exception.InspeccionNoEncontradaException;
import inspeccion.domain.model.InspeccionFitosanitaria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class InspeccionService implements RegistrarInspeccionUseCase {

    private final InspeccionRepositoryPort inspeccionRepository;
    private final TerritorialClientPort territorialClient;

    @Override
    public InspeccionFitosanitaria registrar(InspeccionFitosanitaria inspeccion) {
        log.info("Registrando inspección para lote ID: {}", inspeccion.getIdLote());

        LoteInfo lote = territorialClient.buscarLotePorId(inspeccion.getIdLote())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El lote con ID " + inspeccion.getIdLote() + " no existe en el sistema territorial."));

        LocalDateTime ahora = LocalDateTime.now();
        InspeccionFitosanitaria aGuardar = InspeccionFitosanitaria.builder()
                .numeroInspeccion(generarNumeroInspeccion())
                .fechaInspeccion(inspeccion.getFechaInspeccion())
                .tipoInspeccion(inspeccion.getTipoInspeccion())
                .estado(inspeccion.getEstado())
                .idLote(lote.getId())
                .codigoLote(lote.getCodigo())
                .nombreInspector(inspeccion.getNombreInspector())
                .cedulaInspector(inspeccion.getCedulaInspector())
                .observaciones(inspeccion.getObservaciones())
                .fechaCreacion(ahora)
                .fechaActualizacion(ahora)
                .build();

        InspeccionFitosanitaria guardada = inspeccionRepository.guardar(aGuardar);
        log.info("Inspección registrada con ID: {} y número: {}", guardada.getIdInspeccion(), guardada.getNumeroInspeccion());
        return guardada;
    }

    @Override
    public InspeccionFitosanitaria actualizar(Long idInspeccion, InspeccionFitosanitaria inspeccionActualizada) {
        log.info("Actualizando inspección ID: {}", idInspeccion);

        InspeccionFitosanitaria existente = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));

        InspeccionFitosanitaria actualizada = InspeccionFitosanitaria.builder()
                .idInspeccion(existente.getIdInspeccion())
                .numeroInspeccion(existente.getNumeroInspeccion())
                .fechaInspeccion(inspeccionActualizada.getFechaInspeccion() != null
                        ? inspeccionActualizada.getFechaInspeccion() : existente.getFechaInspeccion())
                .tipoInspeccion(inspeccionActualizada.getTipoInspeccion() != null
                        ? inspeccionActualizada.getTipoInspeccion() : existente.getTipoInspeccion())
                .estado(existente.getEstado())
                .idLote(existente.getIdLote())
                .codigoLote(existente.getCodigoLote())
                .nombreInspector(inspeccionActualizada.getNombreInspector() != null
                        ? inspeccionActualizada.getNombreInspector() : existente.getNombreInspector())
                .cedulaInspector(inspeccionActualizada.getCedulaInspector() != null
                        ? inspeccionActualizada.getCedulaInspector() : existente.getCedulaInspector())
                .observaciones(inspeccionActualizada.getObservaciones() != null
                        ? inspeccionActualizada.getObservaciones() : existente.getObservaciones())
                .fechaCreacion(existente.getFechaCreacion())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        return inspeccionRepository.actualizar(actualizada);
    }

    @Override
    public InspeccionFitosanitaria iniciar(Long idInspeccion) {
        log.info("Iniciando inspección ID: {}", idInspeccion);
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        InspeccionFitosanitaria iniciada = inspeccion.iniciar();
        return inspeccionRepository.actualizar(iniciada);
    }

    @Override
    public InspeccionFitosanitaria completar(Long idInspeccion) {
        log.info("Completando inspección ID: {}", idInspeccion);
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        InspeccionFitosanitaria completada = inspeccion.completar();
        return inspeccionRepository.actualizar(completada);
    }

    @Override
    public InspeccionFitosanitaria cancelar(Long idInspeccion, String motivo) {
        log.info("Cancelando inspección ID: {} - Motivo: {}", idInspeccion, motivo);
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        InspeccionFitosanitaria cancelada = inspeccion.cancelar(motivo);
        return inspeccionRepository.actualizar(cancelada);
    }

    @Override
    public InspeccionFitosanitaria enviarARevision(Long idInspeccion) {
        log.info("Enviando a revisión inspección ID: {}", idInspeccion);
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        InspeccionFitosanitaria enRevision = inspeccion.enviarARevision();
        return inspeccionRepository.actualizar(enRevision);
    }

    private String generarNumeroInspeccion() {
        String anio = String.valueOf(LocalDateTime.now().getYear());
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        return "INSP-" + anio + "-" + timestamp;
    }
}
