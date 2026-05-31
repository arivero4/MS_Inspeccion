package inspeccion.domain.model;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;

/**
 * Agregado raiz del dominio de inspecciones fitosanitarias.
 *
 * <p>Representa una inspeccion fitosanitaria sobre un lote de cultivo
 * hortifruticola. Encapsula el ciclo de vida completo y las transiciones
 * de estado del flujo del ICA.</p>
 *
 * <p><b>Flujo de estados:</b></p>
 * <pre>
 *   PROGRAMADA -[iniciar]->        EN_PROCESO
 *   EN_PROCESO -[completar]->      COMPLETADA
 *   COMPLETADA -[revision]->       PENDIENTE_REVISION
 *   PEND_REV   -[aprobar]->        APROBADA
 *   PEND_REV   -[devolver]->       EN_PROCESO
 *   PROGRAMADA -[cancelar]->       CANCELADA
 *   EN_PROCESO -[cancelar]->       CANCELADA
 * </pre>
 *
 * <p>Inmutable en transiciones: cada metodo devuelve una nueva instancia via Builder.</p>
 * <p>Tabla Oracle: {@code INSPECCION_FITOSANITARIA}.</p>
 */
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
        // Nota: la validación de detalles se hace en la capa de servicio usando el repositorio,
        // ya que el domain object no carga detalles para evitar N+1 queries.
        return copiarConEstado(EstadoInspeccion.COMPLETADA);
    }

    /**
     * Versión que valida que existan detalles (cantidad > 0 pasada por el servicio).
     */
    public InspeccionFitosanitaria completarConValidacion(int cantidadDetalles) {
        if (!estado.permiteCompletarse()) {
            throw new IllegalStateException(
                "La inspección en estado '" + estado + "' no puede completarse. Debe estar EN_PROCESO.");
        }
        if (cantidadDetalles <= 0) {
            throw new IllegalStateException(
                "No se puede completar una inspección sin detalles de muestreo registrados.");
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

    public InspeccionFitosanitaria aprobar() {
        if (this.estado != EstadoInspeccion.PENDIENTE_REVISION) {
            throw new IllegalStateException("Solo inspecciones PENDIENTE_REVISION pueden ser aprobadas.");
        }
        return copiarConEstado(EstadoInspeccion.APROBADA);
    }

    public InspeccionFitosanitaria devolverParaCorreccion() {
        if (this.estado != EstadoInspeccion.PENDIENTE_REVISION) {
            throw new IllegalStateException("Solo inspecciones PENDIENTE_REVISION pueden devolverse.");
        }
        return copiarConEstado(EstadoInspeccion.EN_PROCESO);
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
