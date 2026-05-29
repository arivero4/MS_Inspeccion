package inspeccion.domain.model;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionFitosanitaria {

    private Long idInspeccion;
    private String numeroInspeccion;
    private LocalDate fechaInspeccion;
    private TipoInspeccion tipoInspeccion;
    private EstadoInspeccion estado;
    private Long idLote;
    private String codigoLote;
    private String nombreInspector;
    private String cedulaInspector;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @Builder.Default
    private List<DetalleInspeccion> detalles = new ArrayList<>();

    public InspeccionFitosanitaria iniciar() {
        if (!estado.permiteIniciarse()) {
            throw new IllegalStateException(
                "La inspección en estado '" + estado + "' no puede iniciarse. Solo inspecciones PROGRAMADAS pueden iniciarse.");
        }
        return copiarConEstado(EstadoInspeccion.EN_PROCESO);
    }

    public InspeccionFitosanitaria completar() {
        if (!estado.permiteCompletarse()) {
            throw new IllegalStateException(
                "La inspección en estado '" + estado + "' no puede completarse. Debe estar EN_PROCESO.");
        }
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalStateException(
                "No se puede completar una inspección sin detalles registrados.");
        }
        return copiarConEstado(EstadoInspeccion.COMPLETADA);
    }

    public InspeccionFitosanitaria cancelar(String motivo) {
        if (!estado.permiteCancelacion()) {
            throw new IllegalStateException(
                "La inspección en estado '" + estado + "' no puede cancelarse.");
        }
        String obsActualizada = (this.observaciones != null)
            ? this.observaciones + " | CANCELADA: " + motivo
            : "CANCELADA: " + motivo;
        return InspeccionFitosanitaria.builder()
                .idInspeccion(this.idInspeccion)
                .numeroInspeccion(this.numeroInspeccion)
                .fechaInspeccion(this.fechaInspeccion)
                .tipoInspeccion(this.tipoInspeccion)
                .estado(EstadoInspeccion.CANCELADA)
                .idLote(this.idLote)
                .codigoLote(this.codigoLote)
                .nombreInspector(this.nombreInspector)
                .cedulaInspector(this.cedulaInspector)
                .observaciones(obsActualizada)
                .fechaCreacion(this.fechaCreacion)
                .fechaActualizacion(LocalDateTime.now())
                .detalles(this.detalles)
                .build();
    }

    public InspeccionFitosanitaria enviarARevision() {
        if (this.estado != EstadoInspeccion.COMPLETADA) {
            throw new IllegalStateException("Solo inspecciones COMPLETADAS pueden enviarse a revisión.");
        }
        return copiarConEstado(EstadoInspeccion.PENDIENTE_REVISION);
    }

    public boolean tieneDeteccionesAltas() {
        return detalles != null && detalles.stream()
                .flatMap(d -> d.getPlagas() != null ? d.getPlagas().stream() : java.util.stream.Stream.empty())
                .anyMatch(DetallePlaga::esIncidenciaAlta);
    }

    public boolean esUrgente() {
        return tipoInspeccion != null && tipoInspeccion.esUrgente();
    }

    private InspeccionFitosanitaria copiarConEstado(EstadoInspeccion nuevoEstado) {
        return InspeccionFitosanitaria.builder()
                .idInspeccion(this.idInspeccion)
                .numeroInspeccion(this.numeroInspeccion)
                .fechaInspeccion(this.fechaInspeccion)
                .tipoInspeccion(this.tipoInspeccion)
                .estado(nuevoEstado)
                .idLote(this.idLote)
                .codigoLote(this.codigoLote)
                .nombreInspector(this.nombreInspector)
                .cedulaInspector(this.cedulaInspector)
                .observaciones(this.observaciones)
                .fechaCreacion(this.fechaCreacion)
                .fechaActualizacion(LocalDateTime.now())
                .detalles(this.detalles)
                .build();
    }
}
