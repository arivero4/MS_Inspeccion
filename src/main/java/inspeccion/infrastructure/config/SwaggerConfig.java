package inspeccion.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuracion de Swagger/OpenAPI 3 para ms-inspeccion.
 * Deshabilitado en perfil prod por seguridad.
 * Acceso en dev: {@code http://localhost:8083/swagger-ui/index.html}.
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Inspecciones Fitosanitarias")
                        .description("API para la gestión de inspecciones fitosanitarias de cultivos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ICA - Instituto Colombiano Agropecuario")
                                .email("soporte@ica.gov.co"))
                        .license(new License().name("Apache 2.0")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT obtenido del servicio de autenticación")));
    }
}
