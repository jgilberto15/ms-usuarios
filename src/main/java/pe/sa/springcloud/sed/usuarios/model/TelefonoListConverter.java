package pe.sa.springcloud.sed.usuarios.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class TelefonoListConverter implements AttributeConverter<List<Telefono>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Telefono> telefonos) {
        try {
            return objectMapper.writeValueAsString(telefonos);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al convertir la lista de teléfonos a JSON", e);
        }
    }

    @Override
    public List<Telefono> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Telefono>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al convertir JSON a lista de teléfonos", e);
        }
    }
}
