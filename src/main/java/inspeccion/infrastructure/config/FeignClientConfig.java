package inspeccion.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/** Configuracion de Feign Client (actualmente usa RestTemplate; placeholder para migracion futura). */

@Configuration
public class FeignClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
