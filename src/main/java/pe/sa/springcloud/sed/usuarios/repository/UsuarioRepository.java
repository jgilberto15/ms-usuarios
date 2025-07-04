package pe.sa.springcloud.sed.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sa.springcloud.sed.usuarios.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}
