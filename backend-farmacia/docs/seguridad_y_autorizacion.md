# Seguridad y autorización

## Propósito

Definir el modelo de seguridad de `backend-farmacia`, dejando claro qué se protege, qué parte es pública, qué parte es privada y cómo se distribuyen los permisos dentro del sistema.

## Principio general

El backend de farmacia no es completamente público ni completamente privado. Tiene una frontera doble:

- una **superficie pública** de catálogo sin login;
- una **superficie administrativa** protegida con autenticación.

Esa diferencia debe reflejarse de forma explícita en rutas, guards, contratos, DTOs y logging.

## Objetivos de seguridad

### 1. Permitir consulta pública controlada
El catálogo visible debe poder consumirse sin login, pero solo con la información que realmente corresponde exponer.

### 2. Proteger administración interna
La gestión de categorías, productos, publicación, disponibilidad e imágenes no debe quedar abierta.

### 3. Proteger información no pública
El backend no debe exponer datos administrativos irrelevantes en la superficie pública.

### 4. Mantener trazabilidad suficiente
Las acciones administrativas relevantes deben poder relacionarse con una identidad interna.

## Modelo de acceso oficial para V1

### Superficie pública
No requiere autenticación.

### Superficie administrativa
Requiere autenticación y autorización con el rol:
- `ADMIN_FARMACIA`

## Principio de autorización

La autorización debe seguir una política de **mínimo privilegio razonable**.

En V1.0 esto es simple porque el backend solo asume un rol interno administrativo, pero aun así la frontera pública/admin debe quedar muy clara para no abrir de más el sistema.

## Qué debe quedar público

### `catalogo-publico`
- listado de productos visibles;
- detalle público del producto;
- búsquedas simples;
- datos públicos de disponibilidad;
- URLs de imágenes públicas del catálogo cuando corresponda.

## Qué debe quedar privado

### `auth-admin`
- login;
- validación de sesión admin.

### `categorias`
- alta;
- edición;
- cambios administrativos.

### `productos`
- creación;
- edición;
- cambios internos.

### `disponibilidad-publicacion`
- publicar;
- despublicar;
- ajustar disponibilidad administrativa.

### `media`
- subida;
- asociación;
- reemplazo;
- eliminación lógica o técnica según el diseño.

## Seguridad por módulo

### `auth-admin`
Debe permitir acceso solo al flujo de autenticación administrativa.

### `categorias`
Debe estar protegido como superficie admin.

### `productos`
Debe estar protegido como superficie admin, salvo lo que expresamente se expone vía catálogo público.

### `media`
Debe estar protegido para operaciones de carga o asociación; la lectura pública de archivos visibles debe resolverse de forma controlada.

### `catalogo-publico`
Debe ser consumible sin token, pero solo con DTOs públicos y filtros permitidos.

### `disponibilidad-publicacion`
Debe estar protegido como parte de la lógica admin.

## Qué no debe pasar

- exponer endpoints admin sin guard;
- mezclar en una misma respuesta pública información interna irrelevante;
- asumir que la existencia de un producto implica exposición pública;
- dejar rutas de media abiertas para modificación sin protección;
- confiar en que el frontend separará por sí solo lo público de lo privado.

## Respuestas esperadas de seguridad

### 401 Unauthorized
Cuando la identidad admin no existe, no es válida o el token expiró.

### 403 Forbidden
Cuando el usuario está autenticado pero no tiene permiso suficiente.

Las respuestas deben ser uniformes y no filtrar detalles internos innecesarios.

## Integración técnica recomendada

La implementación debe apoyarse en mecanismos típicos del ecosistema Nest:

- guards para la superficie admin;
- JWT para autenticación administrativa;
- decorators o utilidades para obtener contexto del usuario autenticado;
- protección por módulo o por endpoint según la necesidad.

## Resultado esperado

La seguridad y autorización del backend-farmacia deben dejar una frontera pública/admin muy clara, de forma que el catálogo siga siendo accesible y simple para el público, mientras la operación interna permanece protegida y trazable.

