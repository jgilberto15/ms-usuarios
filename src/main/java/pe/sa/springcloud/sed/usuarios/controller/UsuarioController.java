package pe.sa.springcloud.sed.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.sa.springcloud.sed.usuarios.dto.UsuarioDTO;
import pe.sa.springcloud.sed.usuarios.mapper.UsuarioMapper;
import pe.sa.springcloud.sed.usuarios.service.UsuarioService;
import pe.sa.springcloud.sed.usuarios.util.JwtUtil;
import pe.sa.springcloud.sed.usuarios.util.ValidarToken;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ValidarToken validarToken;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Convierte el DTO a entidad
        var usuario = usuarioMapper.toEntity(usuarioDTO);

        var nuevoUsuario = usuarioService.crearUsuario(usuario);

        // Convierte la entidad a DTO
        return ResponseEntity.status(201).body(usuarioMapper.toDTO(nuevoUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        // Validar el token
        validarToken.validarToken(authorizationHeader);

        var usuario = usuarioService.obtenerUsuarioPorId(id);

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(@RequestHeader("Authorization") String authorizationHeader) {
        // Validar el token
        validarToken.validarToken(authorizationHeader);

        var dtos = usuarioService.listarUsuarios().stream()
                .map(usuarioMapper::toDTO)  //Convierte usuario a UsuarioDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        // Validar el token
        validarToken.validarToken(authorizationHeader);

        // Convierte el DTO a entidad
        var usuario = usuarioMapper.toEntity(usuarioDTO);

        var actualizado = usuarioService.actualizarUsuario(id, usuario);

        return ResponseEntity.ok(usuarioMapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        // Validar el token
        validarToken.validarToken(authorizationHeader);

        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
