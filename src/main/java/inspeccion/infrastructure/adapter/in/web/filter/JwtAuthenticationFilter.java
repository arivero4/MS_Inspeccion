package inspeccion.infrastructure.adapter.in.web.filter;

import inspeccion.infrastructure.adapter.out.security.JwtAuthenticationAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Filtro de autenticacion JWT para ms-inspeccion.
 *
 * <p>Intercepta cada request HTTP, extrae el token Bearer del header
 * {@code Authorization}, lo valida con la clave secreta compartida
 * y puebla el {@code SecurityContext} de Spring con el rol del usuario
 * (claim {@code grupos} del JWT).</p>
 *
 * <p>La clave JWT debe ser identica a la configurada en ms-usuarios.</p>
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationAdapter jwtAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtAdapter.validarToken(token)) {
                String username = jwtAdapter.extraerUsername(token);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String r : jwtAdapter.extraerRoles(token)) {
                    String norm = r.replace(" ", "_").toUpperCase();
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + norm));
                    // Legacy aliases
                    if ("ADMINISTRADOR".equals(norm)) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    } else if ("ASISTENTE_TECNICO".equals(norm)) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_INSPECTOR"));
                    }
                }
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}
