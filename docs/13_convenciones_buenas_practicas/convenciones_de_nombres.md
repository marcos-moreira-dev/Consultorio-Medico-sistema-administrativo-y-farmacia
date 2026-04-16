# Convenciones de nombres

## Propósito

Definir reglas de nombrado para que el proyecto mantenga coherencia entre documentación, base de datos, backend, frontend y estructura de carpetas.

## Principio general

Nombrar bien reduce fricción mental. Los nombres deben reflejar intención, contexto y responsabilidad. Deben ser predecibles antes que ingeniosos.

## Reglas generales

### NOM-001. Usar lenguaje del dominio cuando corresponda
Si el concepto es paciente, cita, atención o producto, el nombre técnico debe respetar ese vocabulario y no inventar sinónimos innecesarios.

### NOM-002. Mantener consistencia entre capas
Un mismo concepto no debería llamarse de tres formas distintas en documentación, DTOs, tablas y UI salvo que haya una razón clara.

### NOM-003. Preferir nombres explícitos
Es mejor un nombre algo más largo pero claro que uno corto y ambiguo.

## Convenciones por tipo de elemento

### Carpetas y archivos documentales
Usar `snake_case` descriptivo.

**Ejemplos:**
- `reglas_de_negocio.md`
- `datos_minimos_requeridos.md`
- `manejo_de_errores.md`

### Tablas, columnas y objetos de base de datos
Usar `snake_case` consistente y vocabulario de negocio.

**Ejemplos:**
- `paciente`
- `fecha_creacion`
- `estado_cita`

### Clases Java
Usar `PascalCase`.

**Ejemplos:**
- `Paciente`
- `RegistrarAtencionUseCase`
- `ProductoController`

### Métodos y variables Java
Usar `camelCase`.

**Ejemplos:**
- `registrarPaciente`
- `buscarProductosVisibles`
- `estadoCobro`

### DTOs
Nombrarlos según intención y dirección del contrato.

**Ejemplos:**
- `CrearPacienteRequest`
- `PacienteResponse`
- `ProductoPublicoResponse`
- `ActualizarDisponibilidadRequest`

### Endpoints
Usar rutas claras, en plural cuando represente colecciones, y separadas por contexto.

**Ejemplos lógicos:**
- `/api/consultorio/pacientes`
- `/api/consultorio/citas`
- `/api/farmacia/productos`
- `/api/publico/farmacia/catalogo`

### Enum o catálogos
Nombrar con términos cerrados y consistentes.

**Ejemplos:**
- `PROGRAMADA`
- `CANCELADA`
- `PAGADO`
- `PENDIENTE`

## Reglas semánticas recomendadas

### Para acciones
Preferir verbos claros:
- crear;
- registrar;
- actualizar;
- buscar;
- publicar;
- inactivar;
- reprogramar.

### Para estados
Usar términos de negocio claros y mutuamente distinguibles.

### Para objetos públicos vs internos
Cuando el contrato público difiera del administrativo, el nombre debe reflejarlo.

**Ejemplos:**
- `ProductoPublicoResponse`
- `ProductoAdminResponse`

## Qué evitar

- abreviaturas poco obvias;
- nombres genéricos como `data`, `info`, `manager`, `helper` sin contexto;
- mezclar español e inglés sin criterio;
- renombrar el mismo concepto según el componente por simple costumbre.

## Resultado esperado

Las convenciones de nombres deben hacer que el proyecto se lea como un sistema coherente: mismas ideas, mismos términos, menos ambigüedad entre dominio, código y documentación.