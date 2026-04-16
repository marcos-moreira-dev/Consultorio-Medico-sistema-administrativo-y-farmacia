# Convenciones de nombres BD

## Propósito

Definir reglas de nombrado para que la base de datos de farmacia sea consistente, legible y fácil de estudiar en PostgreSQL.

## Principio general

Los nombres de objetos de base de datos deben ser predecibles, descriptivos y estables. Deben reflejar el dominio de farmacia y no depender de abreviaturas crípticas.

## Reglas generales

### CNB-001. Usar `snake_case`
Aplica a tablas, columnas, constraints, índices y vistas.

### CNB-002. Usar vocabulario del dominio
Si el concepto es producto, categoria, disponibilidad o reserva, el nombre debe respetar ese lenguaje.

### CNB-003. Preferir nombres explícitos
Es mejor `estado_disponibilidad` que `disp_estado` si el segundo vuelve opaco el modelo.

## Convenciones por tipo de objeto

### Tablas
Usar sustantivo singular y claro.

**Ejemplos:**
- `categoria`
- `producto`
- `reserva`

### Columnas FK
Usar el patrón `<tabla>_id`.

**Ejemplos:**
- `categoria_id`
- `producto_id`

### Campos de estado
Usar nombres explícitos ligados al concepto.

**Ejemplos:**
- `estado_producto`
- `estado_reserva`
- `estado_disponibilidad`

### Campos temporales
Usar nombres claros según significado.

**Ejemplos:**
- `fecha_creacion`
- `fecha_actualizacion`
- `fecha_hora_registro`

### Constraints
Usar prefijos consistentes.

**Ejemplos lógicos:**
- `pk_producto`
- `fk_producto_categoria`
- `ck_producto_estado`
- `uq_producto_*` si corresponde

### Índices
Usar prefijos que permitan identificar tabla y finalidad.

**Ejemplos lógicos:**
- `idx_producto_nombre`
- `idx_producto_estado_producto`
- `idx_reserva_producto_id`

## Qué evitar

- mezclar español e inglés sin razón clara;
- abreviaturas opacas;
- prefijos artificiales en tablas como `tbl_` o `t_`;
- nombres genéricos como `dato`, `info`, `valor` sin contexto.

## Resultado esperado

Estas convenciones deben hacer que el SQL de farmacia se lea como un modelo serio y consistente, tanto para implementación como para estudio posterior.

