package pe.sa.springcloud.sed.usuarios.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pe.sa.springcloud.sed.usuarios.dto.UsuarioDTO;
import pe.sa.springcloud.sed.usuarios.mapper.UsuarioMapper;
import pe.sa.springcloud.sed.usuarios.model.Usuario;
import pe.sa.springcloud.sed.usuarios.service.UsuarioService;
import pe.sa.springcloud.sed.usuarios.util.ValidarToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private ValidarToken validarToken;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_exito() {
        // Datos de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreo("test@example.com");

        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");

        // Mock del mapper y servicio
        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(usuarioService.crearUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // Llamada al método
        ResponseEntity<UsuarioDTO> respuesta = usuarioController.crearUsuario(usuarioDTO);

        // Verificaciones
        assertNotNull(respuesta);
        assertEquals(201, respuesta.getStatusCodeValue());
        assertEquals("test@example.com", respuesta.getBody().getCorreo());
    }

    @Test
    void obtenerUsuario_exito() {
        // Datos de entrada
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);

        // Mock del servicio y mapper
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // Llamada al método
        ResponseEntity<UsuarioDTO> respuesta = usuarioController.obtenerUsuario("Bearer token", 1L);

        // Verificaciones
        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1L, respuesta.getBody().getId());
    }
}
