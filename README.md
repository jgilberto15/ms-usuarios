# Microservicio de Usuarios (ms-usuarios)

Este proyecto es un microservicio desarrollado con **Spring Boot** que expone una API RESTful para la gestión de usuarios. Incluye operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y utiliza **JWT** para generar tokens de autenticación. Los datos de los usuarios se almacenan en una base de datos en memoria (H2).

## **Características**

- **CRUD de Usuarios**: Soporte completo para las operaciones de creación, lectura, actualización y eliminación.
- **Validación de Datos**: Validación de correo electrónico y contraseña con expresiones regulares.
- **Persistencia**: Uso de JPA con H2 como base de datos en memoria.
- **Seguridad**: Generación de tokens JWT para cada usuario creado.
- **Documentación**: API documentada con Swagger/OpenAPI.
- **Formato JSON**: Todos los endpoints aceptan y devuelven datos en formato JSON.

---

## **Requisitos Previos**

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- **Java 17** o superior.
- **Maven** (para compilar y ejecutar el proyecto).
- **Postman** o cualquier cliente HTTP para probar los endpoints.

---

## **Configuración del Proyecto**

### **1. Clonar el Repositorio**

```bash
git clone <URL_DEL_REPOSITORIO>
cd ms-usuarios

2. Configuración de la Base de Datos
El proyecto utiliza H2, una base de datos en memoria. No necesitas configurarla manualmente. Los datos se inicializan automáticamente al iniciar la aplicación.

3. Configuración del Puerto
Por defecto, el microservicio se ejecuta en el puerto 8081. Si necesitas cambiarlo, edita el archivo application.properties:

server.port=8081
Ejecución del Proyecto
1. Compilar el Proyecto
Ejecuta el siguiente comando para compilar el proyecto:

mvn clean install
2. Iniciar la Aplicación
Ejecuta el siguiente comando para iniciar la aplicación:

mvn spring-boot:run
La aplicación estará disponible en: http://localhost:8081

Probar la API
1. Documentación Swagger
Puedes acceder a la documentación interactiva de la API en:

http://localhost:8081/swagger-ui.html

2. Endpoints Disponibles
Método	Endpoint	Descripción
POST	/api/v1/usuarios	Crear un nuevo usuario
GET	/api/v1/usuarios	Listar todos los usuarios
GET	/api/v1/usuarios/{id}	Obtener un usuario por su ID
PUT	/api/v1/usuarios/{id}	Actualizar un usuario existente
DELETE	/api/v1/usuarios/{id}	Eliminar un usuario
3. Ejemplo de Petición
Crear Usuario (POST)
POST /api/v1/usuarios
Content-Type: application/json

{
  "nombre": "Juan Perez",
  "correo": "juan.perez@example.com",
  "contraseña": "Password123",
  "telefonos": [
    {
      "numero": "123456789",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
Respuesta Exitosa