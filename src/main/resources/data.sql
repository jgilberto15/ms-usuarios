-- Creación de tabla USUARIO
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    telefonos CLOB
);

-- Insertar datos iniciales
INSERT INTO usuario (nombre, correo, contrasenia, token, telefonos)
VALUES (
    'Admin',
    'admin@example.com',
    'Admin123',
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTY5MDAwMDAwMCwiZXhwIjoxNjkwMDg2NDAwfQ.1q2w3e4r5t6y7u8i9o0p',
    '[{"numero":"123456789","codigoCiudad":"1","codigoPais":"51"}]'
);

INSERT INTO usuario (nombre, correo, contrasenia, token, telefonos)
VALUES (
    'Usuario Test',
    'test@example.com',
    'Test1234',
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNjkwMDAwMDAwLCJleHAiOjE2OTAwODY0MDB9.9o8i7u6y5t4r3e2w1q',
    '[{"numero":"987654321","codigoCiudad":"1","codigoPais":"51"},{"numero":"555555555","codigoCiudad":"2","codigoPais":"51"}]'
);