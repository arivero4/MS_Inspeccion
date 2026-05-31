package inspeccion.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Entidad de dominio que representa un muestreo de campo dentro de una inspeccion.
 *
 * <p>Cada {@link InspeccionFitosanitaria} puede tener uno o varios muestreos.
 * Cada muestreo registra el total de plantas inspeccionadas en un {@link #idLote}
 * especifico y las plagas detectadas ({@link DetallePlaga}).</p>
 *
 * <p>Tabla Oracle: {@code DETALLE_INSPECCION} con FK {@code ID_INSPECCION}
 * e {@code ID_LOTE}.</p>
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInspeccion {

    private Long idDetalle;
    private Long idInspeccion;
    private Integer totalPlantas;
    private Long idLote;               // new field from schema

    // Fields kept for backward compat (not in new schema)
    private String nombreCultivo;
    private Double areaInspeccionada;
    private Integer plantasMuestreadas;
    private String resultado;
    private String observaciones;
    private LocalDateTime fechaCreacion;

    @Builder.Default
    private List<DetallePlaga> plagas = new ArrayList<>();

    public double calcularPorcentajeMuestreado() {
        if (totalPlantas == null || totalPlantas == 0) return 0.0;
        if (plantasMuestreadas == null) return 100.0; // Sin muestreo parcial → 100%
        return (plantasMuestreadas * 100.0) / totalPlantas;
    }

    public boolean tienePlagas() {
        return plagas != null && !plagas.isEmpty();
    }

    public long contarPlagasCriticas() {
        if (plagas == null) return 0;
        return plagas.stream().filter(DetallePlaga::requiereAccionInmediata).count();
    }

    public double calcularIncidenciaPromedio() {
        if (plagas == null || plagas.isEmpty()) return 0.0;
        return plagas.stream()
                .mapToDouble(p -> p.getNivelIncidencia() != null ? p.getNivelIncidencia() : 0.0)
                .average()
                .orElse(0.0);
    }
}
