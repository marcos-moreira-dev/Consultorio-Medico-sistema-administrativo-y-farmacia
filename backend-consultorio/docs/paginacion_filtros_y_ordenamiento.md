# Paginación, filtros y ordenamiento - backend-consultorio

## Objetivo

Dejar una convención uniforme para listados administrativos del consultorio.

## Política adoptada

Los módulos principales de listado exponen paginación real mediante Spring `Pageable` y devuelven resultados homogéneos usando `PageResponse<T>` dentro de `ApiResponse<T>`.

## Módulos donde sí aplica paginación

- usuarios
- pacientes
- profesionales
- citas
- atenciones
- cobros
- auditoría

## Módulos donde puede mantenerse listado pequeño simple

- roles
- catálogos chicos futuros

## Contrato de respuesta

Ejemplo conceptual:

```json
{
  "ok": true,
  "data": {
    "content": [],
    "page": 0,
    "size": 20,
    "totalElements": 0,
    "totalPages": 0,
    "first": true,
    "last": true
  },
  "correlationId": "..."
}
```

## Ordenamiento

El backend sanitiza el `Pageable` recibido y solo permite propiedades whitelisteadas por módulo. Esto evita exponer ordenamientos arbitrarios o inconsistentes.

La utilidad central sembrada para esto es `PageableUtils`.

## Criterio de diseño

La documentación ya no debe prometer paginación si el runtime no la sostiene. En esta etapa se adopta la postura inversa: los listados principales ya se consideran paginados como contrato oficial.
