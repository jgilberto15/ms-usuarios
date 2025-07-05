package pe.sa.springcloud.sed.usuarios.service;

import pe.sa.springcloud.sed.usuarios.dto.TelefonoDTO;
import pe.sa.springcloud.sed.usuarios.model.Usuario;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorId(Long id);

    List<Usuario> listarUsuarios();

    Usuario actualizarUsuario(Long id, Usuario usuario);

    Usuario actualizarParcialmente(Long id, Map<String, Object> campos);

    Usuario actualizarTelefonos(Long id, List<TelefonoDTO> telefonosDTO);

    void eliminarUsuario(Long id);
}
