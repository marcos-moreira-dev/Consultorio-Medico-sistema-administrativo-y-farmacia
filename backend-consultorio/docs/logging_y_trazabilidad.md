# Logging y trazabilidad

## Propósito

Definir cómo debe registrar información operativa y diagnóstica `backend-consultorio`, de modo que el sistema pueda explicar qué pasó, cuándo pasó y, en cierta medida, quién lo hizo, sin convertir el log en ruido inútil ni en fuga de información sensible.

## Principio general

El backend del consultorio es un sistema privado y sensible. Por eso, el logging no puede ser ni minimalista al punto de dejarte ciego, ni excesivo al punto de exponer datos clínicos o administrativos innecesarios.

La trazabilidad debe equilibrar:

- diagnóstico técnico;
- reconstrucción operativa;
- protección de datos sensibles;
- utilidad real para soporte y estudio.

## Objetivos del logging

### 1. Diagnóstico técnico
Permitir identificar fallos, rutas ejecutadas, errores de validación, errores de seguridad y problemas inesperados.

### 2. Trazabilidad operativa
Permitir reconstruir acciones relevantes del sistema como:
- login;
- alta o edición de paciente;
- creación o cancelación de cita;
- registro de atención;
- registro de cobro;
- generación de reporte.

### 3. Correlación de eventos
Permitir seguir una solicitud a través de varias capas del backend.

### 4. Base para soporte y estudio
Servir como herramienta de depuración y también como referencia pedagógica de cómo debe registrar un backend administrativo serio.

## Qué debe loguearse

## Eventos técnicos básicos

### Arranque del sistema
- perfil activo;
- puertos o configuración principal si aplica;
- estado general del arranque;
- errores críticos de inicialización.

### Seguridad
- intentos de login exitosos;
- intentos de login fallidos;
- accesos denegados;
- tokens inválidos o expirados cuando corresponda.

### Errores HTTP relevantes
- 400 significativos;
- 401;
- 403;
- 404 importantes;
- 500 o errores inesperados.

## Eventos operativos recomendados

### `usuarios`
- creación de usuario;
- cambio de estado de usuario;
- asociación o desasociación con profesional.

### `profesionales`
- alta de profesional;
- inactivación;
- actualización relevante.

### `pacientes`
- creación de paciente;
- actualización relevante de datos.

### `citas`
- creación de cita;
- cancelación;
- futura reprogramación en V1.1;
- conflictos de agenda detectados.

### `atenciones`
- registro de atención;
- atención con o sin cita previa.

### `cobros`
- registro de cobro;
- cambio de estado de cobro si se implementa;
- usuario registrador cuando corresponda.

### `reportes`
- tipo de reporte generado;
- filtros aplicados;
- formato solicitado;
- éxito o fallo de generación.

## Correlation ID

## Objetivo

Cada request relevante debería poder asociarse a un identificador de correlación.

### Qué permite
- seguir una operación a través de controller, service, repository y manejo de errores;
- relacionar logs técnicos con una misma solicitud;
- mejorar soporte y depuración.

### Recomendación
Agregar y propagar un `correlation_id` por request, y exponerlo también en respuestas o metadatos cuando haga sentido.

## Niveles de log recomendados

### `INFO`
Para eventos operativos normales y puntos importantes del flujo.

### `WARN`
Para situaciones anómalas no catastróficas, como intentos inválidos o inconsistencias controladas.

### `ERROR`
Para fallos reales, excepciones inesperadas o errores técnicos relevantes.

### `DEBUG`
Solo en desarrollo o diagnóstico controlado, nunca como verborragia permanente en producción o demo formal.

## Qué no debe loguearse

- contraseñas;
- tokens completos;
- hashes sensibles completos;
- datos clínicos innecesariamente detallados;
- payloads completos si contienen información sensible sin necesidad real;
- stack traces expuestos al cliente.

## Relación entre logging y privacidad

Como el consultorio maneja información sensible, el principio debe ser:

**loguear lo suficiente para entender y sostener la operación, pero no más de lo necesario para exponer el contenido del negocio.**

Ejemplo correcto:
- “Atención registrada para paciente_id X por profesional_id Y”

Ejemplo no deseable:
- volcar toda la nota clínica completa en un log normal.

## Logging por capas

### Controllers
Deben registrar recepción o cierre de operaciones importantes, no toda la lógica interna.

### Services / use cases
Deben registrar hitos del negocio y decisiones relevantes.

### Seguridad
Debe registrar login, acceso denegado, token inválido y contexto mínimo de identidad.

### Infraestructura
Debe registrar fallos de persistencia, generación de reportes y problemas técnicos concretos.

## Relación con auditoría de BD

El logging del backend no reemplaza toda la trazabilidad estructurada de base de datos.

La idea es que ambos se complementen:
- la BD conserva ciertos hechos del dominio;
- el backend registra la película técnica y operativa del request.

## Resultado esperado

La estrategia de logging y trazabilidad del backend-consultorio debe dejar un sistema diagnosticable, explicable y suficientemente profesional, sin perder control sobre privacidad ni convertir el log en basura o fuga de información.