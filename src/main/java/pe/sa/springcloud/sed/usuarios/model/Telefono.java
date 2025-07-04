package pe.sa.springcloud.sed.usuarios.model;
import lombok.Data;

@Data
public class Telefono {

    private String numero;
    private String codigoCiudad;
    private String codigoPais;
}
