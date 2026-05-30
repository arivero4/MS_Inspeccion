package inspeccion.application.service;

import inspeccion.application.port.in.GestionarDetalleUseCase;
import inspeccion.application.port.out.DetalleInspeccionRepositoryPort;
import inspeccion.application.port.out.DetallePlagaRepositoryPort;
import inspeccion.application.port.out.InspeccionRepositoryPort;
import inspeccion.domain.exception.DetalleNoEncontradoException;
import inspeccion.domain.exception.InspeccionNoEncontradaException;
import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;
import inspeccion.domain.model.InspeccionFitosanitaria;
import inspeccion.domain.service.IncidenciaCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetalleInspeccionService implements GestionarDetalleUseCase {

    private final DetalleInspeccionRepositoryPort detalleRepository;
    private final DetallePlagaRepositoryPort plagaRepository;
    private final InspeccionRepositoryPort inspeccionRepository;
    private final IncidenciaCalculator incidenciaCalculator;

    @Override
    public DetalleInspeccion agregarDetalle(Long idInspeccion, DetalleInspeccion detalle) {
        log.info("Agregando detalle a inspeccion ID: {}", idInspeccion);
        InspeccionFitosanitaria inspeccion = inspeccionRepository.buscarPorId(idInspeccion)
                .orElseThrow(() -> new InspeccionNoEncontradaException(idInspeccion));
        if (!inspeccion.getEstado().permiteAgregarDetalles()) {
            throw new IllegalStateException("No se pueden agregar detalles en estado: " + inspeccion.getEstado());
        }
        DetalleInspeccion d = DetalleInspeccion.builder()
                .idInspeccion(idInspeccion)
                .idLote(detalle.getIdLote())           // CRÍTICO: campo requerido nuevo esquema
                .totalPlantas(detalle.getTotalPlantas())
                .nombreCultivo(detalle.getNombreCultivo())
                .areaInspeccionada(detalle.getAreaInspeccionada())
                .plantasMuestreadas(detalle.getPlantasMuestreadas())
                .resultado(detalle.getResultado())
                .observaciones(detalle.getObservaciones())
                .fechaCreacion(LocalDateTime.now())
                .build();
        return detalleRepository.guardar(d);
    }

    @Override
    public DetalleInspeccion actualizarDetalle(Long idDetalle, DetalleInspeccion upd) {
        DetalleInspeccion e = detalleRepository.buscarPorId(idDetalle)
                .orElseThrow(() -> new DetalleNoEncontradoException(idDetalle));
        DetalleInspeccion a = DetalleInspeccion.builder().idDetalle(e.getIdDetalle()).idInspeccion(e.getIdInspeccion())
                .nombreCultivo(upd.getNombreCultivo() != null ? upd.getNombreCultivo() : e.getNombreCultivo())
                .areaInspeccionada(upd.getAreaInspeccionada() != null ? upd.getAreaInspeccionada() : e.getAreaInspeccionada())
                .totalPlantas(upd.getTotalPlantas() != null ? upd.getTotalPlantas() : e.getTotalPlantas())
                .plantasMuestreadas(upd.getPlantasMuestreadas() != null ? upd.getPlantasMuestreadas() : e.getPlantasMuestreadas())
                .resultado(upd.getResultado() != null ? upd.getResultado() : e.getResultado())
                .observaciones(upd.getObservaciones() != null ? upd.getObservaciones() : e.getObservaciones())
                .fechaCreacion(e.getFechaCreacion()).build();
        return detalleRepository.actualizar(a);
    }

    @Override
    public void eliminarDetalle(Long idDetalle) {
        detalleRepository.buscarPorId(idDetalle).orElseThrow(() -> new DetalleNoEncontradoException(idDetalle));
        plagaRepository.eliminarPorDetalle(idDetalle);
        detalleRepository.eliminar(idDetalle);
    }

    @Override
    public Optional<DetalleInspeccion> buscarDetallePorId(Long idDetalle) {
        return detalleRepository.buscarPorId(idDetalle);
    }

    @Override
    public List<DetalleInspeccion> listarDetallesPorInspeccion(Long idInspeccion) {
        return detalleRepository.buscarPorInspeccion(idInspeccion);
    }

    @Override
    public DetallePlaga agregarDetallePlaga(Long idDetalle, DetallePlaga plaga) {
        DetalleInspeccion det = detalleRepository.buscarPorId(idDetalle)
                .orElseThrow(() -> new DetalleNoEncontradoException(idDetalle));
        double inc = 0.0;
        if (plaga.getPlantasAfectadas() != null && det.getTotalPlantas() != null && det.getTotalPlantas() > 0)
            inc = incidenciaCalculator.calcularPorcentajeIncidencia(plaga.getPlantasAfectadas(), det.getTotalPlantas());
        // Si el frontend envía incidencia calculada, usarla; sino calcular
        double incidenciaFinal = (plaga.getNivelIncidencia() != null && plaga.getNivelIncidencia() > 0)
                ? plaga.getNivelIncidencia() : inc;
        DetallePlaga p = DetallePlaga.builder()
                .idDetalle(idDetalle)
                .idPlaga(plaga.getIdPlaga())                   // FK nuevo esquema
                .plantasAfectadas(plaga.getPlantasAfectadas())
                .nivelIncidencia(incidenciaFinal)
                .nivelSeveridad(incidenciaCalculator.calcularNivelIncidencia(incidenciaFinal))
                .nombrePlaga(plaga.getNombrePlaga())
                .nombreCientifico(plaga.getNombreCientifico())
                .areaAfectada(plaga.getAreaAfectada())
                .accionRecomendada(plaga.getAccionRecomendada())
                .fechaDeteccion(plaga.getFechaDeteccion())
                .build();
        return plagaRepository.guardar(p);
    }

    @Override
    public DetallePlaga actualizarDetallePlaga(Long idDetallePlaga, DetallePlaga upd) {
        DetallePlaga e = plagaRepository.buscarPorId(idDetallePlaga)
                .orElseThrow(() -> new DetalleNoEncontradoException(idDetallePlaga, "DetallePlaga"));
        double inc = upd.getNivelIncidencia() != null ? upd.getNivelIncidencia() : e.getNivelIncidencia();
        DetallePlaga a = DetallePlaga.builder().idDetallePlaga(e.getIdDetallePlaga()).idDetalle(e.getIdDetalle())
                .nombrePlaga(upd.getNombrePlaga() != null ? upd.getNombrePlaga() : e.getNombrePlaga())
                .nombreCientifico(upd.getNombreCientifico() != null ? upd.getNombreCientifico() : e.getNombreCientifico())
                .plantasAfectadas(upd.getPlantasAfectadas() != null ? upd.getPlantasAfectadas() : e.getPlantasAfectadas())
                .nivelIncidencia(inc).nivelSeveridad(incidenciaCalculator.calcularNivelIncidencia(inc))
                .areaAfectada(upd.getAreaAfectada() != null ? upd.getAreaAfectada() : e.getAreaAfectada())
                .accionRecomendada(upd.getAccionRecomendada() != null ? upd.getAccionRecomendada() : e.getAccionRecomendada())
                .fechaDeteccion(upd.getFechaDeteccion() != null ? upd.getFechaDeteccion() : e.getFechaDeteccion()).build();
        return plagaRepository.actualizar(a);
    }

    @Override
    public void eliminarDetallePlaga(Long idDetallePlaga) {
        plagaRepository.buscarPorId(idDetallePlaga).orElseThrow(() -> new DetalleNoEncontradoException(idDetallePlaga, "DetallePlaga"));
        plagaRepository.eliminar(idDetallePlaga);
    }

    @Override
    public List<DetallePlaga> listarPlagasPorDetalle(Long idDetalle) {
        return plagaRepository.buscarPorDetalle(idDetalle);
    }
}
