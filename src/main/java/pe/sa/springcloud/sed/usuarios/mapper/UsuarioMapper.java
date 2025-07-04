package pe.sa.springcloud.sed.usuarios.mapper;

import org.springframework.stereotype.Component;
import pe.sa.springcloud.sed.usuarios.dto.TelefonoDTO;
import pe.sa.springcloud.sed.usuarios.dto.UsuarioDTO;
import pe.sa.springcloud.sed.usuarios.model.Telefono;
import pe.sa.springcloud.sed.usuarios.model.Usuario;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasenia(dto.getContrasenia());
        usuario.setTelefonos(dto.getTelefonos().stream()
                .map(telefonoDTO -> {
                    var telefono = new Telefono();
                    telefono.setNumero(telefonoDTO.getNumero());
                    telefono.setCodigoCiudad(telefonoDTO.getCodigoCiudad());
                    telefono.setCodigoPais(telefonoDTO.getCodigoPais());
                    return telefono;
                }).collect(Collectors.toList()));
        return usuario;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setContrasenia(usuario.getContrasenia());
        dto.setTelefonos(usuario.getTelefonos().stream()
                .map(telefono -> {
                    var telefonoDTO = new TelefonoDTO();
                    telefonoDTO.setNumero(telefono.getNumero());
                    telefonoDTO.setCodigoCiudad(telefono.getCodigoCiudad());
                    telefonoDTO.setCodigoPais(telefono.getCodigoPais());
                    return telefonoDTO;
                }).collect(Collectors.toList()));
        dto.setToken(usuario.getToken()); // Mapear el token
        return dto;
    }
}
