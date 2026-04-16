# backend-consultorio

Backend privado del subdominio consultorio.

## Rol dentro del sistema

Este componente concentra la lógica administrativa interna del consultorio. No está pensado como API pública ni como backend mixto con la farmacia. Su responsabilidad es servir al ecosistema interno del consultorio con seguridad, contratos claros y suficiente rigor de negocio.

## Responsabilidades principales

- autenticación de usuarios internos;
- autorización por rol;
- gestión de pacientes, profesionales, citas, atenciones y cobros;
- exposición de endpoints privados, versionados y homogéneos;
- aplicación de validaciones y reglas de negocio cruzadas;
- generación de reportes operativos sobrios;
- logging y trazabilidad suficientes.

## Dependencias conceptuales del componente

- Java 21;
- Spring Boot;
- Spring Security;
- JWT;
- JPA / Hibernate;
- PostgreSQL;
- Flyway;
- OpenAPI / Swagger.

## Relación con otros componentes

- conversa conceptualmente con `database-consultorio`;
- sirve principalmente a `desktop-consultorio-javafx`;
- no debe mezclar lógica pública de farmacia.

## Estado actual

Este backend ya no es solo scaffolding. A la fecha del snapshot ya existe una base funcional amplia, con módulos reales, seguridad JWT, contratos homogéneos tipo `ApiResponse<T>`, paginación en listados principales y migraciones Flyway para el esquema inicial del consultorio.

Aun así, debe entenderse como **backend fuerte en consolidación**, no como producto final perfecto. La prioridad actual ya no es inventar más módulos, sino endurecer lo existente, validar localmente y cerrar decisiones de diseño abiertas.

## Decisiones técnicas ya cerradas

- Java objetivo: **Eclipse Temurin 21**;
- construcción con **Maven + Toolchains**;
- seguridad con JWT bajo namespace `app.security.jwt.*`;
- autorización por rol usando method security;
- listados principales paginados;
- contrato HTTP homogéneo con `ApiResponse<T>` y `PageResponse<T>`;
- Flyway como base operativa del esquema del backend;
- reportes sobrios pero estructuralmente reales.

## Documentos clave que deben leerse primero

- `docs/vision_backend.md`
- `docs/arquitectura_backend.md`
- `docs/modulos_y_responsabilidades.md`
- `docs/estructura_de_paquetes.md`
- `docs/contratos_api.md`
- `docs/seguridad_y_autorizacion.md`
- `docs/paginacion_filtros_y_ordenamiento.md`
- `docs/reportes_backend.md`
- `docs/onboarding_backend.md`
- `TODO_BACKEND_CONSULTORIO.md`

## Arranque operativo mínimo

### 1. Preparar JDK y Maven

Usar **Eclipse Temurin 21** y tener `mvn` disponible. El proyecto incluye un wrapper ligero (`mvnw`, `mvnw.cmd`) que delega a tu instalación local de Maven.

### 2. Variables de entorno

Partir de `.env.example` y ajustar al entorno local.

### 3. Verificación recomendada

```bat
scripts\verify-backend-consultorio.bat
```

### 4. Arranque en desarrollo

```bat
scripts\dev-backend-consultorio.bat
```

## Observación didáctica importante

La base de datos documental (`database-consultorio`) sigue siendo útil como material de estudio y referencia. Sin embargo, para el backend operativo la fuente de evolución del esquema ya se empuja mediante **Flyway** en `src/main/resources/db/migration/`.

## Regla práctica

Antes de crear endpoints o mover estructura, revisar primero la documentación del componente y el archivo `TODO_BACKEND_CONSULTORIO.md`. Este backend está pensado para que la implementación siga a la arquitectura y no al revés.


## Documentos nuevos o reforzados en esta etapa

- `docs/flyway_y_migraciones.md`
- `docs/matriz_roles_y_endpoints.md`
- `TODO_BACKEND_CONSULTORIO.md`
