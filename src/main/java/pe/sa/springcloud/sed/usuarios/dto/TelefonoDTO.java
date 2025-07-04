package pe.sa.springcloud.sed.usuarios.dto;

import lombok.Data;

@Data
public class TelefonoDTO {
    private String numero;
    private String codigoCiudad;
    private String codigoPais;
}
