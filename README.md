# Servicio de Actualización de Estudiantes

Microservicio Spring Boot para actualizar la información de un estudiante existente.

## Estructura del Proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/studentsupdate/
│   │   │       ├── controller/    # Controladores REST
│   │   │       ├── model/         # Entidades
│   │   │       ├── repository/    # Repositorios JPA
│   │   │       ├── service/       # Lógica de negocio
│   │   │       └── StudentUpdateApplication.java
│   │   └── resources/
│   │       └── application.yml    # Configuración
│   └── test/                      # Pruebas unitarias
├── k8s/                          # Manifiestos de Kubernetes
│   ├── deployment.yaml
│   └── service.yaml
├── Dockerfile                    # Configuración de Docker
├── docker-compose.yml           # Configuración de Docker Compose
├── pom.xml                      # Dependencias Maven
└── README.md
```

## Endpoint

- **PUT** `/api/students/{id}`
- Puerto: 8084
- Content-Type: application/json

### Path Parameters
- `id`: ID del estudiante a actualizar (Long)

### Request Body
```json
{
    "name": "string",
    "email": "string",
    "age": integer
}
```

### Response

- **200 OK**
  ```json
  {
    "id": 1,
    "name": "string",
    "email": "string",
    "age": integer
  }
  ```
- **400 Bad Request**: Si los datos son inválidos o el email ya existe
- **404 Not Found**: Si el estudiante no existe
- **500 Internal Server Error**: Error del servidor

## Configuración

### Application Properties
```yaml
server:
  port: 8084

spring:
  application:
    name: students-update-service
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:mariadb://localhost:3306/studentsdb}
    username: root
    password: root
```

### Docker
```bash
# Construir imagen
docker build -t students-update-service .

# Ejecutar contenedor
docker run -p 8084:8084 students-update-service
```

### Docker Compose
```bash
docker compose up --build
```

### Kubernetes
```bash
kubectl apply -f k8s/
```

## Dependencias Principales

- Spring Boot 3.1.5
- Spring Data JPA
- MariaDB Driver
- Spring Web
- Spring Boot Test

## Desarrollo

### Requisitos
- Java 17
- Maven
- Docker (opcional)
- Kubernetes (opcional)

### Compilar
```bash
./mvnw clean package
```

### Ejecutar Tests
```bash
./mvnw test
```

### Ejecutar Localmente
```bash
./mvnw spring-boot:run
```

## Ejemplo de Uso

### Actualizar un Estudiante
```bash
curl -X PUT \
  http://localhost:8084/api/students/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Juan Pérez Actualizado",
    "email": "juan.perez.nuevo@ejemplo.com",
    "age": 21
}'
```

## Validaciones

- El ID debe existir en la base de datos
- El nombre no puede estar vacío
- El email debe ser único y válido
- La edad debe ser mayor a 0
- Se valida que el estudiante exista antes de actualizar

## Monitoreo y Logs

- Los logs de la aplicación se encuentran en la salida estándar
- Se utiliza el nivel de log INFO por defecto
- Se registran todas las operaciones de actualización
- Se registran los intentos fallidos de actualización

## Seguridad

- Validación de datos de entrada
- Sanitización de datos
- Manejo seguro de excepciones
- Transaccionalidad garantizada 