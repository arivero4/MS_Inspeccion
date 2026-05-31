package inspeccion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Punto de entrada del microservicio ms-inspeccion.
 *
 * <p>Gestiona el ciclo completo de inspecciones fitosanitarias hortifruticolas:</p>
 * <ul>
 *   <li>Programacion, inicio y completado de inspecciones.</li>
 *   <li>Registro de conteos de plantas y plagas detectadas en campo.</li>
 *   <li>Calculo automatico de incidencia fitosanitaria (porcentaje de plantas afectadas).</li>
 *   <li>Flujo de aprobacion por el Administrador ICA.</li>
 *   <li>Generacion de reportes estadisticos.</li>
 * </ul>
 *
 * <p><b>Flujo de estados:</b>
 * PROGRAMADA -> EN_PROCESO -> COMPLETADA -> PENDIENTE_REVISION -> APROBADA</p>
 *
 * <p>Tecnologias: Spring Boot 3.2, JDBC puro (JdbcTemplate), Oracle XE 10g (puerto 1522).</p>
 * <p>Se comunica con ms-territorial (puerto 8082) via RestTemplate para validar lotes.</p>
 * <p>Puerto por defecto: {@code 8083}. Perfil de desarrollo: {@code dev}.</p>
 */

@SpringBootApplication
public class InspeccionApplication {
    public static void main(String[] args) {
        SpringApplication.run(InspeccionApplication.class, args);
    }
}
