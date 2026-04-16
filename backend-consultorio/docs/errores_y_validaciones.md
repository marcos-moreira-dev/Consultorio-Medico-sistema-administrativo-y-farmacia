# Errores y validaciones

## Propósito

Definir cómo debe responder `backend-consultorio` cuando una operación falla, distinguiendo entre errores de validación, errores de negocio, errores de autorización y errores inesperados.

## Principio general

Un backend serio no solo debe funcionar cuando todo sale bien. También debe fallar con claridad, consistencia y suficiente información para que el cliente y el desarrollador entiendan qué ocurrió sin exponer más de lo necesario.

## Objetivos de la estrategia de errores

### 1. Uniformidad
No conviene que cada controller devuelva errores distintos según el humor del momento.

### 2. Claridad
El cliente debe poder distinguir si el problema fue de validación, permisos, negocio o fallo interno.

### 3. Trazabilidad
Los errores deben poder relacionarse con logs y con información diagnóstica útil.

### 4. Protección
No se deben exponer detalles internos delicados como stack traces, nombres de clases o secretos de infraestructura.

## Categorías de error recomendadas

## 1. Error de validación de request

Sucede cuando el request no cumple la forma esperada.

### Ejemplos
- campo obligatorio ausente;
- enum inválido;
- longitud fuera de rango;
- fecha mal formada.

### Respuesta HTTP típica
`400 Bad Request`

## 2. Error de negocio

Sucede cuando el request es formalmente correcto, pero viola una regla del dominio.

### Ejemplos
- profesional inexistente;
- paciente inexistente;
- cita solapada para el mismo profesional;
- cobro sin atención previa;
- intento de segunda atención para la misma cita cuando eso ya no corresponde;
- cobro duplicado sobre la misma atención.

### Respuesta HTTP típica
`422 Unprocessable Entity` o, según tu preferencia de estándar interno, algunos casos podrían resolverse con `400`. Pero conviene ser consistente.

## 3. Error de autenticación

Sucede cuando falta identidad válida o el token no sirve.

### Respuesta HTTP típica
`401 Unauthorized`

## 4. Error de autorización

Sucede cuando el usuario está autenticado pero no tiene permiso suficiente.

### Respuesta HTTP típica
`403 Forbidden`

## 5. Recurso no encontrado

Sucede cuando se solicita una entidad inexistente.

### Ejemplos
- paciente_id inexistente;
- cita_id inexistente;
- profesional_id inexistente.

### Respuesta HTTP típica
`404 Not Found`

## 6. Error interno no controlado

Sucede cuando aparece una falla inesperada o una condición no prevista.

### Respuesta HTTP típica
`500 Internal Server Error`

## Estructura de error recomendada

Las respuestas de error deben seguir una convención uniforme del proyecto.

Se recomienda una forma del estilo:

- `ok = false`
- `error.code`
- `error.message`
- `error.details` cuando haga falta
- `meta` con información útil como timestamp o correlation_id

## Intención de los campos

### `error.code`
Código estable y legible por máquina.

### `error.message`
Mensaje claro para entender el problema.

### `error.details`
Detalle adicional útil en errores de validación o negocio, sin volverse una fuga de información interna.

### `meta`
Puede incluir datos como correlation_id, timestamp o contexto técnico mínimo.

## Códigos de error recomendados

Conviene definir una convención de códigos del estilo:

- `AUTH_INVALID_CREDENTIALS`
- `AUTH_TOKEN_EXPIRED`
- `USER_FORBIDDEN`
- `PACIENTE_NOT_FOUND`
- `PROFESIONAL_NOT_FOUND`
- `CITA_SOLAPADA`
- `ATENCION_NOT_FOUND`
- `COBRO_DUPLICADO`
- `VALIDATION_ERROR`
- `INTERNAL_ERROR`

No hace falta una enciclopedia infinita, pero sí un catálogo razonable y estable.

## Manejo global recomendado

Conviene centralizar el tratamiento de errores mediante:

- handlers globales;
- excepciones controladas por categoría;
- traducción uniforme a respuestas HTTP.

En Spring Boot, esto suele encajar bien con mecanismos globales del framework para manejo de excepciones.

## Relación entre validación y errores

No toda validación que falla es un error de negocio.

### Si falla la forma del request
Debe devolverse error de validación estructural.

### Si falla una regla del dominio
Debe devolverse error de negocio.

### Si falla un permiso
Debe devolverse error de seguridad.

Esa distinción es importante para que el frontend y Swagger reflejen bien el comportamiento del sistema.

## Qué no debe pasar

- stack trace crudo en la respuesta;
- mensajes distintos para el mismo tipo de error según el módulo;
- `500` para problemas que en realidad eran validaciones previsibles;
- errores vacíos que solo dicen “algo salió mal”;
- confundir no encontrado con no autorizado o con conflicto de negocio.

## Resultado esperado

La estrategia de errores y validaciones debe hacer que el backend-consultorio sea predecible al fallar, claro para integrar y suficientemente trazable para depuración, sin sacrificar seguridad ni coherencia estructural.

