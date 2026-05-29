package inspeccion.infrastructure.adapter.out.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoteClientResponse {
    private Long id;
    private String codigo;
    private String propietario;
    private String cedulaPropietario;
    private Double area;
    private String ubicacion;
    private String cultivo;
    private String municipio;
    private String departamento;
    private String estado;
}
