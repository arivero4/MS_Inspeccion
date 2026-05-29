package inspeccion.domain.service;

import inspeccion.domain.model.DetalleInspeccion;
import inspeccion.domain.model.DetallePlaga;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidenciaCalculator {

    public static final double UMBRAL_ALTO = 30.0;
    public static final double UMBRAL_MEDIO = 10.0;

    public String calcularNivelIncidencia(double porcentaje) {
        if (porcentaje >= UMBRAL_ALTO) return "ALTO";
        if (porcentaje >= UMBRAL_MEDIO) return "MEDIO";
        return "BAJO";
    }

    public double calcularPorcentajeIncidencia(int plantasAfectadas, int totalPlantas) {
        if (totalPlantas <= 0) {
            throw new IllegalArgumentException("El total de plantas debe ser mayor a cero.");
        }
        return ((double) plantasAfectadas / totalPlantas) * 100.0;
    }

    public double calcularIncidenciaPorArea(double areaAfectada, double areaTotal) {
        if (areaTotal <= 0) {
            throw new IllegalArgumentException("El área total debe ser mayor a cero.");
        }
        return (areaAfectada / areaTotal) * 100.0;
    }

    public String calcularSeveridadGlobal(List<DetalleInspeccion> detalles) {
        if (detalles == null || detalles.isEmpty()) return "SIN_DATOS";

        double promedioGlobal = detalles.stream()
                .mapToDouble(DetalleInspeccion::calcularIncidenciaPromedio)
                .average()
                .orElse(0.0);

        return calcularNivelIncidencia(promedioGlobal);
    }

    public boolean requiereIntervencionUrgente(DetallePlaga plaga) {
        return plaga.requiereAccionInmediata();
    }

    public int contarPlagasConIncidenciaAlta(List<DetallePlaga> plagas) {
        if (plagas == null) return 0;
        return (int) plagas.stream().filter(DetallePlaga::esIncidenciaAlta).count();
    }

    public double calcularIndicadorRiesgo(List<DetalleInspeccion> detalles) {
        if (detalles == null || detalles.isEmpty()) return 0.0;

        long plagasCriticas = detalles.stream()
                .mapToLong(DetalleInspeccion::contarPlagasCriticas)
                .sum();

        double incidenciaPromedio = detalles.stream()
                .mapToDouble(DetalleInspeccion::calcularIncidenciaPromedio)
                .average()
                .orElse(0.0);

        return (incidenciaPromedio * 0.7) + (plagasCriticas * 5.0 * 0.3);
    }
}
