package inspeccion.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OracleConnectionFactory {

    private final DataSource dataSource;

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.debug("Conexion Oracle obtenida: {}", connection.getMetaData().getURL());
        return connection;
    }

    public boolean verificarConexion() {
        try (Connection conn = getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            log.error("Error verificando conexion Oracle: {}", e.getMessage());
            return false;
        }
    }
}
