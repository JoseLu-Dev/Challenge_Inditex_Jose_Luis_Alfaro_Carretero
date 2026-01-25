# Prueba Técnica GFT/Inditex José Luis Alfaro

API REST para consultar precios de productos según marca y fecha.

## Requisitos

- Java 17
- Docker (opcional)

## Ejecutar el proyecto

### Con Maven

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

### Con Docker

```bash
docker build -t challenge-gft-inditex-joseluisalfaro .
docker run -p 8080:8080 challenge-gft-inditex-joseluisalfaro
```

La aplicación estará disponible en `http://localhost:8080`

## Ejecutar tests

### Todos los tests

```bash
./mvnw test
```

### Solo tests unitarios

```bash
./mvnw test -Dtest="**/unit/**/*Test"
```

### Solo tests de integración

```bash
./mvnw test -Dtest="**/integration/**/*IT"
```

### Solo tests E2E

```bash
./mvnw test -Dtest="**/e2e/**/*E2ETest"
```

### Documentación API

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

## Dependencias

| Dependencia | Descripción                                                                 |
|-------------|-----------------------------------------------------------------------------|
| **spring-boot-starter-web** | Proporciona soporte para crear aplicaciones web REST con Spring MVC.        |
| **spring-boot-starter-data-jpa** | Facilita el acceso a bases de datos relacionales mediante JPA e Hibernate.  |
| **spring-boot-starter-validation** | Permite validar datos de entrada usando anotaciones como @NotNull o @Valid. |
| **h2** | Base de datos en memoria.                                                   |
| **lombok** | Genera código repetitivo (getters, setters, builders) mediante anotaciones. |
| **mapstruct** | Genera automáticamente mapeadores entre objetos (DTOs, entidades, modelos). |
| **springdoc-openapi-starter-webmvc-ui** | Genera documentación OpenAPI/Swagger automáticamente desde los endpoints.   |
| **spring-boot-starter-test** | Incluye librerías de testing como JUnit 5 y Mockito.                        |
| **rest-assured** | Permite escribir tests de APIs REST de forma fluida y legible.              |
