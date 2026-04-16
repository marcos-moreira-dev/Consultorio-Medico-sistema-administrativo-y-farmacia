# Seguridad y autorización - backend-consultorio

## Objetivo

Definir la política de autenticación y autorización del backend privado del consultorio.

## Autenticación

La autenticación se basa en:

- `POST /api/v1/auth/login`
- usuario y contraseña internos
- emisión de JWT firmado
- backend stateless

## Configuración central

El namespace oficial del proyecto es:

- `app.security.jwt.secret`
- `app.security.jwt.expiration-seconds`

No debe mantenerse una segunda convención paralela si no existe una necesidad técnica real.

## Endpoints públicos permitidos

- `POST /api/v1/auth/login`
- `/api/v1/api-docs/**`
- `/swagger-ui/**`
- `/swagger-ui.html`
- `/actuator/health`
- `/actuator/info`

## Política de roles adoptada

### `ADMIN_CONSULTORIO`

Puede administrar:

- roles
- usuarios
- profesionales
- pacientes
- citas
- atenciones
- cobros
- auditoría
- reportes

### `OPERADOR_CONSULTORIO`

Puede operar sobre:

- pacientes
- profesionales
- citas
- atenciones
- cobros
- reportes

No debe administrar:

- roles
- usuarios
- auditoría administrativa profunda

### `PROFESIONAL_CONSULTORIO`

Puede acceder, en esta V1, a:

- su autenticación y contexto de sesión
- consulta de agenda
- consulta/registro operativo de atenciones según endpoint autorizado

No debe administrar:

- roles
- usuarios
- cobros administrativos globales
- auditoría administrativa

## Criterio de implementación

La autenticación global se resuelve en la cadena de seguridad. La autorización fina se resuelve principalmente con `@PreAuthorize` en controladores o servicios donde la intención quede más clara.

## Respuestas de error esperadas

- `401` cuando la request requiere autenticación y no existe contexto válido;
- `403` cuando existe autenticación, pero el rol no tiene permisos suficientes.

## Principio práctico

Este backend no debe quedarse en “JWT bonito”. La meta es que la política documental de roles tenga un reflejo real en runtime.
