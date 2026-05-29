package inspeccion.infrastructure.adapter.out.client;

import inspeccion.application.dto.LoteInfo;
import inspeccion.application.port.out.TerritorialClientPort;
import inspeccion.infrastructure.adapter.out.client.dto.LoteClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TerritorialClientAdapter implements TerritorialClientPort {

    private final RestTemplate restTemplate;

    @Value("${services.territorial.url}")
    private String territorialUrl;

    @Override
    public Optional<LoteInfo> buscarLotePorId(Long idLote) {
        try {
            String url = territorialUrl + "/api/territorial/lotes/" + idLote;
            LoteClientResponse response = restTemplate.getForObject(url, LoteClientResponse.class);
            return Optional.ofNullable(response).map(this::toLoteInfo);
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Lote con ID {} no encontrado en servicio territorial", idLote);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al consultar servicio territorial para lote ID {}: {}", idLote, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean existeLote(Long idLote) {
        return buscarLotePorId(idLote).isPresent();
    }

    @Override
    public Optional<LoteInfo> buscarLotePorCodigo(String codigoLote) {
        try {
            String url = territorialUrl + "/api/territorial/lotes/codigo/" + codigoLote;
            LoteClientResponse response = restTemplate.getForObject(url, LoteClientResponse.class);
            return Optional.ofNullable(response).map(this::toLoteInfo);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al consultar lote por codigo {}: {}", codigoLote, e.getMessage());
            return Optional.empty();
        }
    }

    private LoteInfo toLoteInfo(LoteClientResponse r) {
        return LoteInfo.builder()
                .id(r.getId())
                .codigo(r.getCodigo())
                .propietario(r.getPropietario())
                .area(r.getArea())
                .ubicacion(r.getUbicacion())
                .cultivo(r.getCultivo())
                .municipio(r.getMunicipio())
                .estado(r.getEstado())
                .build();
    }
}
