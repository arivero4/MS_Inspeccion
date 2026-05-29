package inspeccion.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia que representa la información de un lote
 * proveniente del servicio territorial externo.
 * Pertenece a la capa de aplicación para evitar dependencia directa
 * hacia el DTO de infraestructura (LoteClientResponse).
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoteInfo {
    private Long id;
    private String codigo;
    private String propietario;
    private Double area;
    private String ubicacion;
    private String cultivo;
    private String municipio;
    private String estado;
}