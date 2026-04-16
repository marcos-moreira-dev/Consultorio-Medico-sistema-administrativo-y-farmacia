# Backend Consultorio: handoff general y criterios de cierre

## Qué representa este handoff

Este archivo no describe un backend vacío. Describe un backend que ya tiene bastante implementación real, pero que todavía necesita **validación local fuerte** y una última etapa de cierre para quedar realmente estudiable y confiable.

## Qué quedó claramente consolidado

### 1. Dependencias y build

- `pom.xml` ya refleja un backend Spring Boot real para Java 21.
- JWT, OpenAPI y Flyway están declarados explícitamente.
- Maven Toolchains queda configurado para `temurin` 21.
- existe un wrapper ligero homogéneo (`mvnw`, `mvnw.cmd`).

### 2. Configuración

- JWT vive bajo `app.security.jwt.*`.
- OpenAPI quedó alineado a `/api/v1/api-docs`.
- perfiles y `.env.example` ya no se contradicen tanto como antes.

### 3. Seguridad y autorización

- el backend ya no se apoya solo en autenticación;
- existe política de autorización por rol sembrada con `@PreAuthorize`;
- se dejó una matriz explícita de roles y superficies en `docs/matriz_roles_y_endpoints.md`.

### 4. Contrato HTTP

- `ApiResponse<T>` se mantiene como envoltorio oficial;
- `PageResponse<T>` ya se usa en listados principales;
- existe saneamiento de `Pageable` con whitelist de campos ordenables.

### 5. Persistencia operativa

- Flyway ya no quedó como decoración;
- existe `V1__schema_consultorio.sql`;
- existe `V2__seed_demo_consultorio.sql`;
- el backend y `database-consultorio` ya dialogan mucho mejor.

### 6. Decisión cerrada sobre nombre visible de usuario

Esta decisión **ya no debe tratarse como abierta en V1**:

- `usuario` **no** persiste un `nombre_completo` propio;
- el nombre visible se deriva desde `profesional` si existe relación;
- si no existe relación con `profesional`, el fallback es `username`.

Consecuencia práctica:

- crear usuario ya no exige `nombreCompleto` en el request;
- el backend deja de prometer persistencia de algo que la BD no guarda.

### 7. Rol legible vs rol código

También quedó corregida otra inconsistencia:

- `rol.codigo` se deriva de `nombre_rol`;
- `rol.nombre` se toma de `descripcion_breve`.

Así el backend deja de devolver dos campos distintos con exactamente el mismo valor cuando la BD sí tenía una columna mejor para nombre legible.

## Qué debe verificarse sí o sí en una máquina local

### Build y arranque

1. copiar `scripts/toolchains-temurin-21.example.xml` a `%USERPROFILE%\.m2	oolchains.xml`;
2. ajustar `jdkHome` a tu Temurin 21 real;
3. ejecutar `scripts\doctor-backend-consultorio.bat`;
4. ejecutar `scriptserify-backend-consultorio.bat`;
5. levantar PostgreSQL real;
6. ejecutar `scripts\dev-backend-consultorio.bat`.

### Flyway y BD

7. verificar que Flyway cree schema `consultorio`;
8. verificar que cree tablas y constraints esperadas;
9. verificar que cargue seeds demo sin colisión;
10. confirmar que Hibernate valida limpio contra el schema resultante.

### Seguridad

11. probar `POST /api/v1/auth/login`;
12. probar `GET /api/v1/auth/me` con token válido;
13. comprobar `401` sin token;
14. comprobar `403` con rol insuficiente;
15. confirmar que roles y endpoints coinciden con `docs/matriz_roles_y_endpoints.md`.

### Contratos y listados

16. probar paginación real en usuarios;
17. probar paginación real en pacientes;
18. probar paginación real en profesionales;
19. probar paginación real en citas;
20. probar paginación real en atenciones;
21. probar paginación real en cobros;
22. probar paginación real en auditoría.

### Observación importante

Sin esta validación local, el backend queda **mucho mejor sembrado**, pero todavía no puede declararse "cerrado" en sentido ejecutable fuerte.

## Qué debe hacer la siguiente IA o persona para dejarlo totalmente sólido

### Fase A - verificación real

- correr build real con Maven;
- corregir cualquier import o wiring roto que solo aparezca con dependencias descargadas;
- correr tests reales y registrar qué suites fallan.

### Fase B - endurecimiento técnico

- revisar si todos los controladores con listados aplican exactamente la whitelist de sort esperada;
- revisar si algún endpoint todavía debería afinar su política de rol;
- comprobar que OpenAPI documenta los contratos nuevos sin inconsistencias.

### Fase C - convergencia final con `database-consultorio`

- revisar si `database-consultorio` debe reflejar también de forma textual la decisión ya cerrada sobre nombre visible derivado de usuario;
- revisar si el diccionario de datos necesita una nota explícita sobre `descripcion_breve` de rol como nombre legible.

### Fase D - reportes

- correr reportes PDF / DOCX / XLSX de extremo a extremo;
- verificar headers, mime type y nombres de archivo;
- decidir si el acabado visual se mantiene sobrio o si habrá una V1.1 estética.

## Qué NO debería hacer la siguiente pasada

- no rediseñar arquitectura por gusto;
- no volver a abrir decisiones ya cerradas sin una razón fuerte;
- no meter nombres visibles persistidos en `usuario` solo por comodidad del frontend;
- no inflar reportes con complejidad gratuita;
- no mezclar consultorio con farmacia.

## Archivos clave que la siguiente IA debe leer primero

1. `README.md`
2. `docs/onboarding_backend.md`
3. `docs/seguridad_y_autorizacion.md`
4. `docs/paginacion_filtros_y_ordenamiento.md`
5. `docs/flyway_y_migraciones.md`
6. `docs/matriz_roles_y_endpoints.md`
7. `TODO_BACKEND_CONSULTORIO.md`
