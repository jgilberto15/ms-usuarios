package pe.sa.springcloud.sed.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {

    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "El correo no tiene un formato válido")
    private String correo;

    @Pattern(regexp = "${usuario.password.regex}", message = "La contraseña no cumple con los requisitos")
    private String contrasenia;

    private List<TelefonoDTO> telefonos;

    private String token;
}
