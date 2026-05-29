package inspeccion.application.port.out;

import inspeccion.application.dto.LoteInfo;

import java.util.Optional;

/**
 * Puerto de salida (driven port) hacia el servicio territorial externo.
 * La aplicación depende de esta abstracción, nunca del cliente HTTP concreto.
 */
public interface TerritorialClientPort {
    Optional<LoteInfo> buscarLotePorId(Long idLote);
    boolean existeLote(Long idLote);
    Optional<LoteInfo> buscarLotePorCodigo(String codigoLote);
}
