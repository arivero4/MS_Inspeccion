package inspeccion.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePlagaResponse {
    private Long idDetallePlaga;
    private Long idDetalle;
    private Long idPlaga;
    private String nombrePlaga;
    private String nombreCientifico;
    private Integer plantasAfectadas;
    private Double nivelIncidencia;
    private String nivelSeveridad;
    private Double areaAfectada;
    private String accionRecomendada;
    private LocalDate fechaDeteccion;
    private boolean requiereAccionInmediata;
}
