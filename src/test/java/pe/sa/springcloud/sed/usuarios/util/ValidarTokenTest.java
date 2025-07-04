package pe.sa.springcloud.sed.usuarios.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidarTokenTest {

    @Mock
    private JwtUtil jwtUtil; // Simulamos la dependencia JwtUtil

    @InjectMocks
    private ValidarToken validarToken; // Inyectamos el mock en la clase ValidarToken

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
    }

    @Test
    void validarToken_exito() {
        // Configurar el mock para que el token sea válido
        String token = "mockedToken";
        when(jwtUtil.validateToken(token)).thenReturn(true);

        // Llamar al método y verificar que no lanza excepciones
        assertDoesNotThrow(() -> validarToken.validarToken("Bearer " + token));
    }

    @Test
    void validarToken_errorTokenInvalido() {
        // Configurar el mock para que el token sea inválido
        String tokenInvalido = "invalidToken";
        when(jwtUtil.validateToken(tokenInvalido)).thenReturn(false);

        // Llamar al método y verificar que lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validarToken.validarToken("Bearer " + tokenInvalido));
        assertEquals("Token no válido o ausente", exception.getMessage());
    }

    @Test
    void validarToken_errorTokenAusente() {
        // Llamar al método con un token nulo y verificar que lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validarToken.validarToken(null));
        assertEquals("Token no válido o ausente", exception.getMessage());
    }
}
