package inspeccion.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/** Configuracion de persistencia JDBC (JdbcTemplate, NamedParameterJdbcTemplate) para ms-inspeccion. */

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
}
