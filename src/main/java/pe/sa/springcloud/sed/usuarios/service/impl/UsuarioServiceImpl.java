package pe.sa.springcloud.sed.usuarios.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sa.springcloud.sed.usuarios.dto.TelefonoDTO;
import pe.sa.springcloud.sed.usuarios.model.Telefono;
import pe.sa.springcloud.sed.usuarios.model.Usuario;
import pe.sa.springcloud.sed.usuarios.repository.UsuarioRepository;
import pe.sa.springcloud.sed.usuarios.service.UsuarioService;
import pe.sa.springcloud.sed.usuarios.util.JwtUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        Optional<Usuario> existente = repository.findByCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        //usuario.setId(UUID.randomUUID());

        String token = jwtUtil.generateToken(usuario.getCorreo());
        usuario.setToken(token);

        return repository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repository.findAll()
                .stream()
                .collect(Collectors.toList()); //listo para futuras modificaciones
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = obtenerUsuarioPorId(id);

        existente.setNombre(usuario.getNombre());
        existente.setCorreo(usuario.getCorreo());
        existente.setContrasenia(usuario.getContrasenia());

        //Procesar lista de telefonos
        if(usuario.getTelefonos() != null){
            List<Telefono> telefonosProcesados = usuario.getTelefonos().stream()
                    .filter(telefono -> telefono.getNumero() != null && telefono.getNumero().isEmpty()) //Filtra telefonos validos
                    .map(telefono -> {
                        telefono.setNumero(telefono.getNumero().trim());
                        return telefono;
                    })
                    .collect(Collectors.toList());
            existente.setTelefonos(telefonosProcesados); // Asignar lista procesada
        }

        return repository.save(existente);
    }

    @Override
    public Usuario actualizarParcialmente(Long id, Map<String, Object> campos) {
        Usuario existente = obtenerUsuarioPorId(id);

        // Actualizar solo los campos proporcionados
        campos.forEach((campo, valor) -> {
            switch (campo) {
                case "nombre":
                    existente.setNombre((String) valor);
                    break;
                case "correo":
                    String nuevoCorreo = (String) valor;
                    if (!nuevoCorreo.equals(existente.getCorreo())) {
                        Optional<Usuario> usuarioConCorreo = repository.findByCorreo(nuevoCorreo);
                        if (usuarioConCorreo.isPresent()) {
                            throw new IllegalArgumentException("El correo ya está registrado");
                        }
                    }
                    existente.setCorreo(nuevoCorreo);
                    break;
                case "contrasenia":
                    existente.setContrasenia((String) valor);
                    break;
                case "telefonos":
                    List<TelefonoDTO> telefonosDTO = (List<TelefonoDTO>) valor;
                    List<Telefono> telefonos = telefonosDTO.stream()
                            .map(dto -> {
                                Telefono t = new Telefono();
                                t.setNumero(dto.getNumero());
                                t.setCodigoCiudad(dto.getCodigoCiudad());
                                t.setCodigoPais(dto.getCodigoPais());
                                return t;
                            }).collect(Collectors.toList());
                    existente.setTelefonos(telefonos);
                    break;
            }
        });

        return repository.save(existente);
    }

    @Override
    public Usuario actualizarTelefonos(Long id, List<TelefonoDTO> telefonosDTO) {
        Usuario existente = obtenerUsuarioPorId(id);

        // Convertir DTO a entidad
        List<Telefono> telefonos = telefonosDTO.stream()
                .map(dto -> {
                    Telefono t = new Telefono();
                    t.setNumero(dto.getNumero());
                    t.setCodigoCiudad(dto.getCodigoCiudad());
                    t.setCodigoPais(dto.getCodigoPais());
                    return t;
                }).collect(Collectors.toList());

        existente.setTelefonos(telefonos);
        return repository.save(existente);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario existente = obtenerUsuarioPorId(id);
        repository.delete(existente);
    }
}
