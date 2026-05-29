package inspeccion.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInspeccionRequest {

    @NotBlank(message = "El nombre del cultivo es obligatorio")
    private String nombreCultivo;

    @NotNull(message = "El area inspeccionada es obligatoria")
    @Positive(message = "El area debe ser positiva")
    private Double areaInspeccionada;

    @NotNull(message = "El total de plantas es obligatorio")
    @Positive(message = "El total de plantas debe ser positivo")
    private Integer totalPlantas;

    @Positive(message = "Las plantas muestreadas deben ser positivas")
    private Integer plantasMuestreadas;

    private String resultado;
    private String observaciones;
}
