# Contratos API

## Propósito

Definir cómo deben estructurarse los contratos HTTP de `backend-consultorio`, qué convenciones siguen y cómo se exponen los recursos del sistema de forma clara, versionada y coherente con una API REST privada.

## Principio general

El backend del consultorio expone una API REST **privada**, **versionada** y **uniforme**. Eso significa que no basta con “tener endpoints”; esos endpoints deben:

- seguir una convención estable;
- separar request y response con DTOs;
- responder con estructura uniforme;
- devolver errores consistentes;
- exponer solo lo necesario según rol y caso de uso.

## Estilo de contrato adoptado

### REST clásico
Se trabajará con recursos y operaciones HTTP tradicionales.

### Versionado desde el inicio
Se recomienda usar prefijo como:

`/api/v1/...`

Esto deja espacio para evolución futura sin romper la semántica del sistema.

### API privada
No existen aquí endpoints públicos para visitantes externos.

## Estructura general recomendada de rutas

Los recursos deben agruparse por módulo funcional.

Ejemplos conceptuales:

- `/api/v1/auth/...`
- `/api/v1/usuarios/...`
- `/api/v1/roles/...`
- `/api/v1/profesionales/...`
- `/api/v1/pacientes/...`
- `/api/v1/citas/...`
- `/api/v1/atenciones/...`
- `/api/v1/cobros/...`
- `/api/v1/reportes/...`

## Convención de respuesta uniforme

Las respuestas exitosas deben seguir una estructura estándar del proyecto.

Se recomienda sostener formalmente un contenedor como:

`ApiResponse<T>`

con campos del estilo:

- `ok`
- `data`
- `meta`

### Intención de cada campo

**`ok`**
Indica si la operación fue exitosa.

**`data`**
Contiene el payload útil del recurso o resultado.

**`meta`**
Contiene metadatos útiles como paginación, mensajes, timestamps, correlation_id o equivalentes según el caso.

## Convención de error uniforme

Los errores también deben seguir una estructura estable.

Se recomienda una forma del estilo:

- `ok = false`
- `error.code`
- `error.message`
- `error.details` cuando aplique
- `meta` con información de trazabilidad útil

No conviene mezclar errores improvisados según el controller.

## Contratos por módulo

## `auth`

### Endpoints esperados
- login;
- quizá endpoint de contexto actual si luego se justifica.

### Responses esperadas
- token;
- tipo de token;
- usuario autenticado resumido;
- rol;
- profesional_id si aplica.

## `usuarios`

### Endpoints esperados
- crear usuario;
- consultar usuario;
- listar usuarios;
- cambiar estado;
- asociar o desasociar profesional.

### Consideración contractual
No exponer password ni hashes en responses bajo ninguna circunstancia.

## `roles`

### Endpoints esperados
- listar roles;
- consultar rol si tiene sentido exponerlo.

### Consideración contractual
Es un módulo pequeño, pero su contrato debe seguir la misma disciplina que el resto.

## `profesionales`

### Endpoints esperados
- crear profesional;
- actualizar profesional;
- consultar profesional;
- listar profesionales;
- inactivar profesional.

## `pacientes`

### Endpoints esperados
- crear paciente;
- actualizar paciente;
- consultar paciente;
- listar pacientes;
- buscar pacientes.

### Consideración contractual
Debe soportar paginación y búsqueda razonable.

## `citas`

### Endpoints esperados
- crear cita;
- cancelar cita;
- consultar cita;
- listar citas;
- agenda por profesional;
- agenda por rango.

### V1.1
- reprogramar cita.

## `atenciones`

### Endpoints esperados
- registrar atención;
- consultar atención;
- listar atenciones;
- filtrar por profesional;
- filtrar por paciente;
- filtrar por fechas.

## `cobros`

### Endpoints esperados
- registrar cobro;
- consultar cobro;
- listar cobros;
- filtrar por estado;
- filtrar por usuario registrador;
- filtrar por fechas.

## `reportes`

### Endpoints esperados
- generar reporte PDF;
- generar reporte DOCX;
- generar reporte XLSX.

### Consideración contractual
Aquí puede haber dos enfoques:
- descarga directa del archivo;
- o respuesta con metadatos previos y luego descarga.

Para esta V1, la descarga directa suele ser suficiente si el reporte no es masivo.

## Verbos HTTP recomendados

### `POST`
Para creación o acciones que producen algo nuevo o disparan generación.

### `GET`
Para consulta o listado.

### `PUT` o `PATCH`
Para actualización según el grado de reemplazo parcial o total que quieras estandarizar.

### `DELETE`
Solo si realmente aplica. En este sistema conviene ser prudente, porque muchas operaciones deberían resolverse por estado y trazabilidad antes que por borrado físico.

## Consideraciones sobre identificadores

Los contratos deben exponer identificadores estables y previsibles.

No conviene que el frontend tenga que inferir relaciones complejas ni construir estructuras a partir de entidades internas mal expuestas.

## Contratos y roles

El contrato no es solo forma del JSON. También incluye quién puede invocarlo.

Por eso la documentación de cada endpoint, directa o indirectamente, debe dejar claro:

- rol mínimo requerido;
- si es administrativo, clínico o mixto;
- si tiene restricciones adicionales por contexto del usuario.

## Qué debe evitarse

- endpoints sin versionado;
- responses heterogéneas por controller;
- exponer entidades JPA directamente;
- rutas sin convención clara;
- contratos que mezclen demasiadas responsabilidades en un mismo endpoint;
- endpoints que filtren información sensible por exceso de datos.

## Relación con Swagger / OpenAPI

Cada contrato debe poder reflejarse bien en Swagger. Eso implica:

- DTOs claros;
- status codes consistentes;
- tags por módulo;
- descripciones entendibles;
- documentación de auth donde corresponda.

## Resultado esperado

Los contratos API del backend-consultorio deben dejar una superficie HTTP privada, uniforme, limpia y profesional, lo bastante explícita para que la implementación y el frontend sepan exactamente qué esperar de cada recurso.

