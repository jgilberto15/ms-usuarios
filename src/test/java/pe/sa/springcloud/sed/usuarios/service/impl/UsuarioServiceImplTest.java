package pe.sa.springcloud.sed.usuarios.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.sa.springcloud.sed.usuarios.model.Telefono;
import pe.sa.springcloud.sed.usuarios.model.Usuario;
import pe.sa.springcloud.sed.usuarios.repository.UsuarioRepository;
import pe.sa.springcloud.sed.usuarios.util.JwtUtil;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_exito() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setNombre("Juan Pérez");

        // Mock del repositorio
        when(usuarioRepository.findByCorreo("test@example.com")).thenReturn(Optional.empty());
        when(jwtUtil.generateToken("test@example.com")).thenReturn("mockedToken");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llamada al método
        Usuario resultado = usuarioService.crearUsuario(usuario);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("mockedToken", resultado.getToken());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void crearUsuario_errorCorreoExistente() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");

        // Mock del repositorio
        when(usuarioRepository.findByCorreo("test@example.com")).thenReturn(Optional.of(usuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.crearUsuario(usuario));
        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    void obtenerUsuarioPorId_exito() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Mock del repositorio
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerUsuarioPorId_errorUsuarioNoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al método y verificación de excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.obtenerUsuarioPorId(1L));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void actualizarUsuario_exito() {
         Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNombre("Juan Pérez");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Actualizado");

        // Mock del repositorio
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.actualizarUsuario(1L, usuarioActualizado);

        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuarioExistente);
    }

    @Test
    void eliminarUsuario_exito() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Mock del repositorio
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }
}
