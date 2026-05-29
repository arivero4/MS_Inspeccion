package inspeccion.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePlagaRequest {

    @NotBlank(message = "El nombre de la plaga es obligatorio")
    private String nombrePlaga;

    private String nombreCientifico;

    @Positive(message = "Las plantas afectadas deben ser positivas")
    private Integer plantasAfectadas;

    @Positive(message = "El area afectada debe ser positiva")
    private Double areaAfectada;

    private String accionRecomendada;
    private LocalDate fechaDeteccion;
}
