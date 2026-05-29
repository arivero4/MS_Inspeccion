package inspeccion.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponse {
    private String titulo;
    private LocalDate fechaGeneracion;
    private Map<String, Object> datos;
    private String severidadGlobal;
    private Double indicadorRiesgo;
    private boolean requiereIntervencion;
    private List<String> alertas;
}
