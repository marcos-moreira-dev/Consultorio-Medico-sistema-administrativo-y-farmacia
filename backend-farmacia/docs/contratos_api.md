# Contratos API

## Propósito

Definir cómo deben estructurarse los contratos HTTP de `backend-farmacia`, qué convenciones siguen y cómo se exponen los recursos del sistema de forma clara, versionada y coherente con una API REST que tiene superficie pública y superficie administrativa.

## Principio general

El backend de farmacia expone una API REST **versionada**, **uniforme** y con dos fronteras distintas:

- una pública sin login;
- una administrativa privada con login.

Eso significa que no basta con “tener endpoints”; esos endpoints deben:

- seguir una convención estable;
- separar request y response con DTOs;
- responder con estructura uniforme;
- devolver errores consistentes;
- exponer solo lo necesario según la superficie y el caso de uso.

## Estilo de contrato adoptado

### REST clásico
Se trabaja con recursos y operaciones HTTP tradicionales.

### Versionado desde el inicio
El prefijo operativo esperado es:

`/api/v1/...`

### Doble superficie
La API distingue con claridad:
- rutas públicas;
- rutas administrativas protegidas.

## Estructura general actual de rutas

Con el prefijo `api/v1`, la organización actual es esta:

### Pública
- `/api/v1/catalogo/categorias`
- `/api/v1/catalogo`
- `/api/v1/catalogo/buscar`
- `/api/v1/catalogo/:productoId`

### Administrativa
- `/api/v1/admin/auth/...`
- `/api/v1/admin/categorias/...`
- `/api/v1/admin/productos/...`
- `/api/v1/admin/media/...`

La publicación y disponibilidad viven dentro del recurso producto administrativo:

- `/api/v1/admin/productos/:productoId/publicacion/...`
- `/api/v1/admin/productos/:productoId/disponibilidad`

## Convención de respuesta uniforme

Las respuestas exitosas siguen una estructura estándar del proyecto.

### Contenedor base
`ApiResponse<T>`

### Campos principales
- `ok`
- `data`
- `meta`
- `timestamp`
- `correlationId` cuando aplique
- `message` solo cuando aporta contexto operativo adicional

## Convención de error uniforme

Los errores deben seguir una estructura estable.

Se espera, al menos:

- `ok = false`
- `statusCode`
- `error`
- `message`
- `code` cuando exista código de negocio estable
- `details` cuando aplique
- `path`
- `timestamp`
- `correlationId`

No conviene mezclar errores improvisados según el controller o según la superficie pública/admin.

## Contratos por módulo

## `auth-admin`

### Endpoints actuales
- `POST /api/v1/admin/auth/login`
- `GET /api/v1/admin/auth/me`

### Responses principales
- token de acceso;
- tipo de token;
- expiración efectiva en segundos;
- usuario admin autenticado resumido;
- roles.

## `categorias`

### Endpoints actuales
- crear categoría;
- consultar categoría;
- listar categorías;
- actualizar categoría.

### Consideración contractual
Es superficie admin y sigue la misma disciplina de respuestas que el resto.

## `productos`

### Endpoints actuales
- crear producto;
- actualizar producto;
- consultar producto;
- listar productos;
- cambiar estado del producto.

### Consideración contractual
No exponer campos internos innecesarios en DTOs públicos si el mismo producto también se proyecta al catálogo.

## `media`

### Endpoints actuales
- `POST /api/v1/admin/media/productos/imagen/upload`
- `PATCH /api/v1/admin/media/productos/:productoId/imagen/asociar`
- `PATCH /api/v1/admin/media/productos/:productoId/imagen/reemplazar`

### Consideración contractual
El JSON no carga el binario de imagen. El contrato expone metadata y URL pública.

## `catalogo-publico`

### Endpoints actuales
- listar catálogo visible;
- consultar detalle público del producto;
- buscar por nombre o categoría;
- listar categorías visibles.

### Consideración contractual
Solo expone la información pública del producto, incluyendo `imagenUrl` cuando corresponde.

## `disponibilidad-publicacion`

### Endpoints actuales
- publicar producto;
- despublicar producto;
- actualizar disponibilidad;
- consultar estado de publicación y disponibilidad.

### Consideración contractual
Es superficie admin y no debe confundirse con el catálogo público, aunque lo alimente.

## Evolución contractual a V1.1

### `reservas`
Se documenta como expansión prevista de V1.1.

No debe aparecer como si ya existiera en V1.0, pero sí puede mencionarse como evolución natural del backend y de la base de datos.

## Verbos HTTP recomendados

### `POST`
Para creación o acciones que producen algo nuevo o disparan un proceso.

### `GET`
Para consulta o listado.

### `PATCH`
Para actualización parcial o cambio de estado.

### `DELETE`
Solo si realmente aplica. En este sistema conviene ser prudente, porque varias operaciones deberían resolverse por estado y trazabilidad antes que por borrado físico.

## Contratos y superficies

El contrato no es solo forma del JSON. También incluye quién puede invocarlo y desde qué frontera.

Por eso la documentación de cada endpoint, directa o indirectamente, debe dejar claro:

- si es público o admin;
- si requiere JWT;
- qué información se expone;
- qué rol mínimo se necesita en admin.

## Qué debe evitarse

- endpoints sin versionado;
- responses heterogéneas por controller;
- exponer entidades TypeORM directamente;
- mezclar rutas públicas y admin sin frontera visible;
- contratos que filtren información administrativa a la superficie pública.

## Relación con Swagger / OpenAPI

Cada contrato debe poder reflejarse bien en Swagger. Eso implica:

- DTOs claros;
- status codes consistentes;
- tags por módulo;
- descripciones entendibles;
- documentación de auth donde corresponda;
- separación clara entre endpoints públicos y admin.

## Resultado esperado

Los contratos API del backend-farmacia deben dejar una superficie HTTP clara, profesional y sin ambigüedad entre lo público y lo administrativo, lo bastante explícita para que la implementación y el frontend sepan exactamente qué esperar de cada recurso.
