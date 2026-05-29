package inspeccion.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class OracleDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        log.info("Inicializando DataSource Oracle con HikariCP");
        return DataSourceBuilder.create().build();
    }
}
