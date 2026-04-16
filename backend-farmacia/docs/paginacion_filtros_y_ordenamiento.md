# Paginación, filtros y ordenamiento

## Propósito

Definir cómo debe manejar `backend-farmacia` las búsquedas, listados, paginación, filtros y ordenamiento de forma uniforme, predecible y profesional.

## Principio general

Este backend no debe resolver listados públicos o administrativos con endpoints improvisados ni con respuestas masivas sin control. Como sistema comercial clásico, debe soportar consultas estructuradas que permitan:

- encontrar productos o categorías relevantes;
- recorrer listados paginados;
- filtrar por criterios útiles del negocio;
- ordenar por campos permitidos;
- evitar sobrecargar respuesta y cliente.

## Ámbitos donde más importa

La política de paginación, filtros y ordenamiento es especialmente importante en:

- productos admin;
- categorías admin;
- catálogo público;
- disponibilidad y publicación;
- media cuando se liste metadata asociada.

## Estrategia recomendada

Se recomienda adoptar una convención transversal del proyecto basada en:

- `page`
- `size`
- `sort`
- filtros explícitos por query params
- búsqueda textual controlada cuando tenga sentido

## Paginación

### Objetivo
Evitar respuestas masivas y mantener una forma estable de navegar resultados.

### Parámetros sugeridos
- `page`
- `size`

### Reglas sugeridas
- `page` inicia en un valor consistente y documentado;
- `size` debe tener límite máximo razonable;
- la respuesta paginada debe devolver metadatos claros.

## Respuesta paginada recomendada

La `ApiResponse<T>` debería poder envolver una estructura paginada con algo como:

- `items`
- `page`
- `size`
- `totalElements`
- `totalPages`
- `hasNext`
- `hasPrevious`

No hace falta imponer exactamente esos nombres si ya tienes otra convención, pero sí conviene que exista una estructura equivalente y estable.

## Ordenamiento

### Objetivo
Permitir ordenar listados sin dejar el backend expuesto a campos arbitrarios.

### Parámetro sugerido
- `sort`

### Forma sugerida
Algo del estilo:
- `sort=nombreProducto,asc`
- `sort=precioVisible,desc`
- `sort=fechaCreacion,desc`

### Regla importante
Los campos ordenables deben ser **whitelisted**, no libres.

No conviene aceptar cualquier nombre de propiedad que el cliente invente.

## Filtros

## Principio general
Los filtros deben responder al dominio, no a columnas aleatorias de la base.

## Filtros esperados por módulo

## `categorias`
- nombre;
- estado si el módulo lo maneja.

## `productos` admin
- categoria_id;
- estado_producto;
- es_publicable;
- estado_disponibilidad;
- nombre_producto o búsqueda simple.

## `catalogo-publico`
- categoría;
- búsqueda por nombre;
- disponibilidad pública;
- criterios públicos razonables.

## `disponibilidad-publicacion`
- estado de publicación;
- estado de disponibilidad;
- producto o categoría cuando haga sentido.

## `media`
- producto_id;
- existencia de imagen principal o equivalente si se llega a listar metadata.

## Búsqueda textual

### Cuándo conviene
Cuando el usuario necesita encontrar rápido un producto o categoría por un dato legible.

### Cuándo no conviene abusar
No todo endpoint necesita un `q` genérico si eso vuelve opaco el contrato.

### Recomendación
Usar búsqueda textual donde realmente tenga valor y documentarla con claridad.

## Criterios técnicos importantes

### 1. No mezclar paginación con respuestas no paginadas sin criterio
Si un endpoint es de listado grande, debería paginar.

### 2. No permitir filtros incoherentes
Ejemplo: rangos inválidos o combinaciones administrativas sin sentido.

### 3. No exponer el modelo interno de persistencia a través de filtros arbitrarios
Los filtros deben hablar el lenguaje del negocio.

### 4. Mantener consistencia transversal
No conviene que cada módulo invente su propia mini convención incompatible.

### 5. Respetar la diferencia público/admin
La superficie pública no necesita exponer toda la potencia de filtrado administrativo si eso vuelve más confuso o riesgoso el contrato.

## DTOs de soporte recomendados

Conviene considerar estructuras o modelos de soporte como:

- `PageResponseDto<T>`;
- filtros por módulo;
- objetos criterio para encapsular combinaciones de parámetros.

Ejemplos:
- `ProductoAdminFilter`
- `CatalogoPublicoFilter`
- `CategoriaFilter`

## Qué no debe pasar

- listados enormes sin paginación donde claramente hace falta;
- `sort` libre sobre cualquier campo;
- filtros improvisados distintos para recursos similares;
- uso indiscriminado de `q` para todo;
- paginación solo en unos módulos y abandono total en otros que también la necesitan.

## Relación con Swagger / OpenAPI

Estos parámetros deben quedar bien documentados en Swagger para que:

- el frontend sepa cómo consumirlos;
- otra IA no tenga que adivinar convenciones;
- el backend se mantenga consistente entre módulos.

## Resultado esperado

La política de paginación, filtros y ordenamiento debe hacer que el backend-farmacia se comporte como una API comercial madura: flexible, predecible y útil para operar datos reales sin convertir cada listado en una respuesta caótica o sobredimensionada.

