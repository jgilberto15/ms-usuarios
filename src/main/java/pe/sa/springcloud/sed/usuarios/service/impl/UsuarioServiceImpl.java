package pe.sa.springcloud.sed.usuarios.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sa.springcloud.sed.usuarios.model.Telefono;
import pe.sa.springcloud.sed.usuarios.model.Usuario;
import pe.sa.springcloud.sed.usuarios.repository.UsuarioRepository;
import pe.sa.springcloud.sed.usuarios.service.UsuarioService;
import pe.sa.springcloud.sed.usuarios.util.JwtUtil;

import java.util.List;
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
    public void eliminarUsuario(Long id) {
        Usuario existente = obtenerUsuarioPorId(id);
        repository.delete(existente);
    }
}
