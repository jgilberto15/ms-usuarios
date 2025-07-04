package pe.sa.springcloud.sed.usuarios.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarToken {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Valida el token recibido en el encabezado de la solicitud.
     * El token debe tener el formato "Bearer <token>".     *
     * @param authorizationHeader El encabezado Authorization con el formato "Bearer <token>"
     */
    public void validarToken(String authorizationHeader) {
        // Verificar que el encabezado no sea nulo y comience con "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token no válido o ausente");
        }

        // Extraer el token real (la parte después de "Bearer ")
        String token = authorizationHeader.substring(7);

        // Validar el token
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token no válido o ausente");
        }
    }
}
