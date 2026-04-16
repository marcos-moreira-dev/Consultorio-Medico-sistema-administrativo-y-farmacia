# TODO extremadamente específico - backend-consultorio

Este archivo está pensado como guía de cierre para la siguiente IA o para una persona que retome el backend sin depender del chat.

## Bloque 1. Verificación local obligatoria

### 1.1 Toolchains y Java
- [ ] copiar `scripts/toolchains-temurin-21.example.xml` a `%USERPROFILE%\.m2	oolchains.xml`;
- [ ] editar `jdkHome` y apuntarlo al Temurin 21 real de la máquina;
- [ ] ejecutar `scripts\doctor-backend-consultorio.bat`;
- [ ] confirmar que `java -version` reporta Java 21 o, como mínimo, que Maven Toolchains sí selecciona Temurin 21.

### 1.2 Maven y wrapper
- [ ] ejecutar `mvnw.cmd -v`;
- [ ] si el wrapper ligero no es suficiente para el flujo deseado, generar wrapper oficial completo con `mvn -N wrapper:wrapper` y commitearlo;
- [ ] ejecutar `scriptserify-backend-consultorio.bat`.

### 1.3 PostgreSQL y Flyway
- [ ] levantar PostgreSQL local;
- [ ] exportar `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` si hace falta;
- [ ] arrancar backend en perfil `dev`;
- [ ] revisar logs Flyway y confirmar que se aplican `V1__schema_consultorio.sql` y `V2__seed_demo_consultorio.sql`.

## Bloque 2. Validación funcional mínima

### 2.1 Health y OpenAPI
- [ ] probar `GET /actuator/health`;
- [ ] probar `GET /api/v1/api-docs`;
- [ ] abrir `/swagger-ui.html`.

### 2.2 Auth
- [ ] hacer login con usuario demo admin;
- [ ] hacer login con usuario demo operador;
- [ ] hacer login con usuario demo profesional;
- [ ] validar expiración y formato del JWT.

### 2.3 Autorización real
- [ ] confirmar que `ADMIN_CONSULTORIO` entra a usuarios/roles/auditoría;
- [ ] confirmar que `OPERADOR_CONSULTORIO` no entra a usuarios/roles/auditoría;
- [ ] confirmar que `PROFESIONAL_CONSULTORIO` no entra a cobros ni usuarios;
- [ ] confirmar que `PROFESIONAL_CONSULTORIO` sí puede consultar agenda/atenciones/pacientes en la política V1 adoptada.

## Bloque 3. Contratos HTTP y paginación

### 3.1 Respuesta uniforme
- [ ] revisar que todos los controladores devuelvan `ApiResponse<T>`;
- [ ] revisar que errores globales sigan usando el mismo contrato;
- [ ] revisar que no queden endpoints devolviendo shapes alternos.

### 3.2 PageResponse
- [ ] validar `page`, `size`, `totalElements`, `totalPages`, `hasNext`, `hasPrevious` en usuarios;
- [ ] validar lo mismo en pacientes;
- [ ] validar lo mismo en profesionales;
- [ ] validar lo mismo en citas;
- [ ] validar lo mismo en atenciones;
- [ ] validar lo mismo en cobros;
- [ ] validar lo mismo en auditoría.

### 3.3 Sort whitelist
- [ ] probar ordenamiento permitido y no permitido en usuarios;
- [ ] probar ordenamiento permitido y no permitido en pacientes;
- [ ] probar ordenamiento permitido y no permitido en profesionales;
- [ ] probar ordenamiento permitido y no permitido en citas;
- [ ] probar ordenamiento permitido y no permitido en atenciones;
- [ ] probar ordenamiento permitido y no permitido en cobros;
- [ ] probar ordenamiento permitido y no permitido en auditoría.

## Bloque 4. Decisiones de dominio ya cerradas que NO deben reabrirse sin razón fuerte

### 4.1 Nombre visible de usuario
- [x] V1 NO persiste `nombre_completo` propio en `usuario`.
- [x] El nombre visible se deriva desde `profesional` o, si no existe, desde `username`.
- [ ] reflejar esta decisión también en cualquier documentación que aún la trate como abierta.

### 4.2 Rol código vs rol nombre
- [x] `nombre_rol` = código estable.
- [x] `descripcion_breve` = nombre legible.
- [ ] revisar respuestas reales para confirmar que ya no salen `rolCodigo` y `rolNombre` duplicados.

## Bloque 5. Reportes

### 5.1 Flujo base
- [ ] generar reporte PDF real;
- [ ] generar reporte DOCX real;
- [ ] generar reporte XLSX real;
- [ ] revisar status code, headers y `Content-Type`;
- [ ] revisar nombre de archivo devuelto.

### 5.2 Alcance
- [x] mantener reportes sobrios, no sobreingenierizados.
- [ ] decidir si V1.1 agregará mejoras visuales o plantillas más ricas.

## Bloque 6. Tests

### 6.1 Suite existente
- [ ] ejecutar tests unitarios con Maven real;
- [ ] ejecutar tests web MVC si existen;
- [ ] corregir imports/wiring rotos si aparecen con dependencias reales.

### 6.2 Cobertura que falta
- [ ] añadir tests de seguridad por rol en endpoints clave;
- [ ] añadir tests de integración de paginación;
- [ ] añadir tests de migraciones si el costo lo justifica;
- [ ] añadir smoke test automatizado de login + `/me`.

## Bloque 7. Documentación que la siguiente IA debe dejar perfecta

- [ ] `README.md` coherente con el estado real del repo;
- [ ] `docs/onboarding_backend.md` actualizado si cambia el flujo real de arranque;
- [ ] `docs/seguridad_y_autorizacion.md` coherente con `@PreAuthorize` real;
- [ ] `docs/paginacion_filtros_y_ordenamiento.md` coherente con `PageableUtils` real;
- [ ] `docs/flyway_y_migraciones.md` coherente con lo que efectivamente corra en PostgreSQL;
- [ ] `backend_consultorio_handoff_general.md` actualizado después de la validación local.

## Bloque 8. Señales de cierre de verdad

Podrás considerar este backend cerca de 100% solo si:

- [ ] compila con Maven real;
- [ ] arranca con Temurin 21 + Toolchains;
- [ ] Flyway corre limpio;
- [ ] login y roles se validan de extremo a extremo;
- [ ] listados paginados funcionan de verdad;
- [ ] reportes generan archivos reales;
- [ ] docs y runtime ya no se contradicen.
