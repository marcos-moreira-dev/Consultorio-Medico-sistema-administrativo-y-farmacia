# Logging y trazabilidad

## Propósito

Definir cómo debe registrar información operativa y diagnóstica `backend-farmacia`, de modo que el sistema pueda explicar qué pasó, cuándo pasó y, en cierta medida, quién lo hizo, sin convertir el log en ruido inútil ni en fuga de información sensible o administrativa.

## Principio general

El backend de farmacia tiene dos superficies muy distintas:

- una pública de catálogo;
- una privada administrativa.

Por eso, el logging no puede tratar ambos mundos como si fueran idénticos. Debe equilibrar:

- diagnóstico técnico;
- reconstrucción operativa;
- separación entre público y admin;
- protección de datos y de infraestructura;
- utilidad real para soporte y estudio.

## Objetivos del logging

### 1. Diagnóstico técnico
Permitir identificar fallos, rutas ejecutadas, errores de validación, errores de seguridad y problemas inesperados.

### 2. Trazabilidad operativa
Permitir reconstruir acciones relevantes del sistema como:
- login admin;
- alta o edición de categoría;
- creación o actualización de producto;
- publicación o despublicación;
- cambios de disponibilidad;
- carga o reemplazo de imagen.

### 3. Correlación de eventos
Permitir seguir una solicitud a través de varias capas del backend.

### 4. Base para soporte y estudio
Servir como herramienta de depuración y también como referencia pedagógica de cómo debe registrar un backend comercial serio.

## Qué debe loguearse

## Eventos técnicos básicos

### Arranque del sistema
- perfil activo;
- puertos o configuración principal si aplica;
- estado general del arranque;
- errores críticos de inicialización.

### Seguridad admin
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

### `categorias`
- creación de categoría;
- actualización relevante;
- cambio de estado si el módulo lo contempla.

### `productos`
- creación de producto;
- edición relevante;
- cambio de estado del producto;
- asociación con categoría.

### `disponibilidad-publicacion`
- publicación;
- despublicación;
- cambio de disponibilidad;
- intentos inválidos de transición de estado.

### `media`
- carga de archivo;
- reemplazo de imagen;
- asociación con producto;
- fallo de validación de archivo.

### `catalogo-publico`
No conviene loguear con exceso cada consulta pública normal si solo añade ruido. Debe registrarse con criterio lo necesario para diagnóstico y no transformar el catálogo en una verborragia de logs.

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
Solo en desarrollo o diagnóstico controlado, nunca como verborragia permanente en demo formal o entorno serio.

## Qué no debe loguearse

- contraseñas;
- tokens completos;
- hashes sensibles completos;
- rutas internas crudas del filesystem si no hacen falta;
- payloads completos con información innecesaria;
- stack traces expuestos al cliente.

## Relación entre logging y superficies

### Superficie pública
Debe registrar lo suficiente para diagnóstico, pero sin sobreinstrumentar cada consulta trivial ni filtrar detalles internos del catálogo admin.

### Superficie admin
Debe registrar con más riqueza operativa porque ahí viven acciones de cambio real sobre el sistema.

## Logging por capas

### Controllers
Deben registrar recepción o cierre de operaciones importantes, no toda la lógica interna.

### Services / use cases
Deben registrar hitos del negocio y decisiones relevantes.

### Seguridad
Debe registrar login, acceso denegado, token inválido y contexto mínimo de identidad admin.

### Infraestructura
Debe registrar fallos de persistencia, almacenamiento de media y problemas técnicos concretos.

## Relación con trazabilidad de base de datos

El logging del backend no reemplaza toda la trazabilidad estructurada de base de datos.

La idea es que ambos se complementen:
- la BD conserva ciertos hechos del dominio;
- el backend registra la película técnica y operativa del request.

## Resultado esperado

La estrategia de logging y trazabilidad del backend-farmacia debe dejar un sistema diagnosticable, explicable y suficientemente profesional, sin perder control sobre seguridad ni convertir el log en basura o fuga de información.