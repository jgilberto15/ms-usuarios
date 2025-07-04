package pe.sa.springcloud.sed.usuarios.service;

import pe.sa.springcloud.sed.usuarios.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorId(Long id);

    List<Usuario> listarUsuarios();

    Usuario actualizarUsuario(Long id, Usuario usuario);

    void eliminarUsuario(Long id);
}
