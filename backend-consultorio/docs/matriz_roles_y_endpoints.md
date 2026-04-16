# Matriz de roles y endpoints - backend-consultorio

## Objetivo

Dejar explícito qué roles deben poder entrar a qué superficies del backend.

## Roles V1

- `ADMIN_CONSULTORIO`
- `OPERADOR_CONSULTORIO`
- `PROFESIONAL_CONSULTORIO`

## Política adoptada

### Auth

- `POST /api/v1/auth/login` -> público
- `GET /api/v1/auth/me` -> cualquier usuario autenticado interno

### Usuarios y roles

- `GET /api/v1/roles/**` -> solo `ADMIN_CONSULTORIO`
- `POST /api/v1/usuarios` -> solo `ADMIN_CONSULTORIO`
- `GET /api/v1/usuarios/**` -> solo `ADMIN_CONSULTORIO`
- `PATCH /api/v1/usuarios/**` -> solo `ADMIN_CONSULTORIO`

### Profesionales

- lectura/listado -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`, `PROFESIONAL_CONSULTORIO`
- creación/actualización/cambio de estado -> mismos roles en esta V1, pero revisar si luego se endurece a admin+operador

### Pacientes

- lectura/listado/creación/actualización -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`, `PROFESIONAL_CONSULTORIO`

### Citas

- agenda/listados/consulta/reprogramación/cancelación -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`, `PROFESIONAL_CONSULTORIO`

### Atenciones

- registro y consulta -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`, `PROFESIONAL_CONSULTORIO`

### Cobros

- lectura y registro -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`
- `PROFESIONAL_CONSULTORIO` queda fuera en V1

### Auditoría

- `GET /api/v1/auditoria/**` -> solo `ADMIN_CONSULTORIO`

### Reportes

- generación -> `ADMIN_CONSULTORIO`, `OPERADOR_CONSULTORIO`

## Nota didáctica

Esta matriz todavía puede refinarse por endpoint fino, pero ya deja una política **mucho más explícita** que "si está autenticado, pasa".
