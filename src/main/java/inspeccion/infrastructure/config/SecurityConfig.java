package inspeccion.infrastructure.config;

import inspeccion.infrastructure.adapter.in.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/health").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/inspecciones/**", "/detalles/**", "/plagas/**", "/reportes/**")
                    .hasAnyRole("ASISTENTE_TECNICO", "INSPECTOR", "ADMIN", "SUPERVISOR", "ADMINISTRADOR", "PROPIETARIO", "PRODUCTOR")
                .requestMatchers(HttpMethod.POST, "/inspecciones/**", "/detalles/**", "/plagas/**")
                    .hasAnyRole("ASISTENTE_TECNICO", "INSPECTOR", "ADMIN", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/inspecciones/**", "/detalles/**", "/plagas/**")
                    .hasAnyRole("ASISTENTE_TECNICO", "INSPECTOR", "ADMIN", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/inspecciones/**")
                    .hasAnyRole("ASISTENTE_TECNICO", "INSPECTOR", "ADMIN", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/inspecciones/**", "/detalles/**", "/plagas/**")
                    .hasAnyRole("ADMIN", "ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
