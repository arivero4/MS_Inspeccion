package inspeccion.infrastructure.adapter.in.web.dto;

import inspeccion.domain.enums.EstadoInspeccion;
import inspeccion.domain.enums.TipoInspeccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionRequest {

    @NotNull(message = "La fecha de inspección es obligatoria")
    private LocalDate fechaInspeccion;

    @NotNull(message = "El tipo de inspección es obligatorio")
    private TipoInspeccion tipoInspeccion;

    @NotNull(message = "El estado de la inspección es obligatorio")
    private EstadoInspeccion estado;

    @NotNull(message = "El ID del lote es obligatorio")
    private Long idLote;

    @NotBlank(message = "El nombre del inspector es obligatorio")
    @Size(max = 200, message = "El nombre del inspector no puede superar 200 caracteres")
    private String nombreInspector;

    @NotBlank(message = "La cédula del inspector es obligatoria")
    @Size(max = 20, message = "La cédula no puede superar 20 caracteres")
    private String cedulaInspector;

    @Size(max = 1000, message = "Las observaciones no pueden superar 1000 caracteres")
    private String observaciones;
}
