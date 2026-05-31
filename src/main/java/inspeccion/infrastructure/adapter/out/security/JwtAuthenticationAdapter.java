package inspeccion.infrastructure.adapter.out.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
/**
 * Adaptador de seguridad que valida tokens JWT en ms-inspeccion.
 * Comparte la misma clave secreta con ms-usuarios para verificar firmas HMAC-SHA256.
 */

@Slf4j
@Component
public class JwtAuthenticationAdapter {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    public boolean validarToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token JWT expirado: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token JWT malformado: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("Error validando token JWT: {}", e.getMessage());
        }
        return false;
    }

    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    public List<String> extraerRoles(String token) {
        Claims claims = extraerClaims(token);
        // El JWT de ms-usuarios almacena los grupos en el claim "grupos"
        // Intentamos "grupos" primero, luego "roles" como fallback
        Object roles = claims.get("grupos");
        if (roles == null) {
            roles = claims.get("roles");
        }
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }
        return List.of();
    }

    public boolean tokenEsExpirado(String token) {
        try {
            Date expiration = extraerClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
