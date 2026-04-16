# Onboarding de backend-consultorio

## Objetivo del onboarding

Este documento explica cómo levantar, validar y entender `backend-consultorio` sin depender de memoria externa del chat.

## Stack base

- Java: **Eclipse Temurin 21**
- Maven: instalación local + Toolchains
- Spring Boot
- PostgreSQL
- Flyway
- JWT
- OpenAPI / Swagger

## Ruta mínima de lectura

1. `README.md`
2. `docs/vision_backend.md`
3. `docs/arquitectura_backend.md`
4. `docs/seguridad_y_autorizacion.md`
5. `docs/paginacion_filtros_y_ordenamiento.md`
6. `docs/reportes_backend.md`
7. `TODO_BACKEND_CONSULTORIO.md`

## Variables de entorno relevantes

Ver `.env.example`.

Las más importantes para levantar localmente son:

- `SPRING_PROFILES_ACTIVE`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `APP_JWT_SECRET`
- `APP_JWT_EXPIRATION_SECONDS`
- `APP_CORS_ALLOWED_ORIGINS`

## Convenciones operativas ya cerradas

- todos los endpoints del backend viven bajo `/api/v1/...`;
- OpenAPI se expone en `/api/v1/api-docs`;
- Swagger UI se expone en `/swagger-ui.html`;
- login público: `POST /api/v1/auth/login`;
- el resto de endpoints requiere autenticación;
- la autorización fina se resuelve por rol mediante `@PreAuthorize`.

## Flyway

El backend ya trae migraciones operativas en:

- `src/main/resources/db/migration/V1__schema_consultorio.sql`
- `src/main/resources/db/migration/V2__seed_demo_consultorio.sql`

La idea es que el backend ya pueda arrancar con una historia de esquema reproducible, sin depender exclusivamente de ejecutar a mano los scripts del componente `database-consultorio`.

## Verificación sugerida en máquina local

### Verificación rápida

```bat
scripts\verify-backend-consultorio.bat
```

### Arranque en desarrollo

```bat
scripts\dev-backend-consultorio.bat
```

## Usuario demo esperado

La migración demo siembra usuarios para pruebas. La contraseña de demo debe revisarse y cambiarse al usar el proyecto fuera de un entorno controlado.

## Observaciones didácticas

- la BD documental y la BD operativa están alineadas, pero no son exactamente el mismo artefacto;
- el backend ya está en fase de consolidación, no de simple scaffolding;
- si una siguiente IA toca este componente, debe priorizar **validación local y cierre de inconsistencias**, no inventar más módulos.
