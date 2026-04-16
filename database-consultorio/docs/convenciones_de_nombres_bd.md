# Convenciones de nombres BD

## Propósito

Definir reglas de nombrado para que la base de datos del consultorio sea consistente, legible y fácil de estudiar en PostgreSQL.

## Principio general

Los nombres de objetos de base de datos deben ser predecibles, descriptivos y estables. Deben reflejar el dominio del consultorio y no depender de abreviaturas crípticas.

## Reglas generales

### CNB-001. Usar `snake_case`
Aplica a tablas, columnas, constraints, índices y vistas.

### CNB-002. Usar vocabulario del dominio
Si el concepto es paciente, cita, atención o cobro, el nombre debe respetar ese lenguaje.

### CNB-003. Preferir nombres explícitos
Es mejor `fecha_hora_atencion` que `fh_at` si el segundo vuelve opaco el modelo.

## Convenciones por tipo de objeto

### Tablas
Usar sustantivo singular y claro.

**Ejemplos:**
- `paciente`
- `cita`
- `atencion`
- `cobro`

### Columnas FK
Usar el patrón `<tabla>_id`.

**Ejemplos:**
- `paciente_id`
- `cita_id`
- `atencion_id`

### Campos de estado
Usar nombres explícitos ligados al concepto.

**Ejemplos:**
- `estado_cita`
- `estado_cobro`

### Campos temporales
Usar nombres claros según significado.

**Ejemplos:**
- `fecha_hora_inicio`
- `fecha_hora_atencion`
- `fecha_hora_registro`
- `fecha_creacion`
- `fecha_actualizacion`

### Constraints
Usar prefijos consistentes.

**Ejemplos lógicos:**
- `pk_paciente`
- `fk_cita_paciente`
- `ck_cobro_estado`
- `uq_cita_*` si corresponde

### Índices
Usar prefijos que permitan identificar tabla y finalidad.

**Ejemplos lógicos:**
- `idx_paciente_apellidos`
- `idx_cita_fecha_hora_inicio`
- `idx_atencion_paciente_id`

## Qué evitar

- mezclar español e inglés sin razón clara;
- abreviaturas opacas;
- prefijos artificiales en tablas como `tbl_` o `t_`;
- nombres genéricos como `dato`, `info`, `valor` sin contexto.

## Resultado esperado

Estas convenciones deben hacer que el SQL del consultorio se lea como un modelo serio y consistente, tanto para implementación como para estudio posterior.