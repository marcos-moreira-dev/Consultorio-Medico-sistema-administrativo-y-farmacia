# Rutas y navegación

## Propósito

Definir cómo navega el usuario dentro de `storefront-farmacia-angular`, qué rutas públicas existen y cómo debe sentirse el recorrido entre home, catálogo y detalle de producto.

## Principio general

La navegación del storefront debe ser simple, clara y pública. El usuario no debe perderse ni sentir que el sitio cambia de identidad entre páginas.

La experiencia debe apoyarse en tres ideas:

- layout público estable;
- rutas cortas y comprensibles;
- transición clara entre descubrir, explorar y revisar detalle.

## Modelo de navegación adoptado

Se adopta una navegación pública clásica, basada en páginas visibles dentro de un layout común.

Eso significa:

- header y footer se mantienen consistentes;
- el contenido central cambia según la ruta;
- la búsqueda y los filtros deben sentirse parte del mismo sitio;
- no hay autenticación de cliente en V1.0.

## Rutas principales esperadas

## 1. Home

### Ruta sugerida
`/`

### Función
Ser el punto de entrada principal del sitio.

### Qué ofrece
- hero;
- branding;
- acceso al catálogo;
- búsqueda rápida;
- productos o categorías destacadas.

## 2. Catálogo

### Ruta sugerida
`/catalogo`

### Función
Explorar productos públicos.

### Qué ofrece
- búsqueda;
- filtros;
- grid de productos;
- paginación.

## 3. Detalle de producto

### Ruta adoptada para V1
`/producto/:productoId`

### Función
Mostrar el producto con más detalle.

### Qué ofrece
- imagen principal;
- nombre;
- disponibilidad;
- información visible;
- CTA relevante.

### Regla de diseño
Se adopta `productoId` porque hoy el backend público expone detalle con identificador numérico. Un slug público podría añadirse después, pero no conviene inventarlo desde el frontend antes de que exista contrato real.

## Rutas auxiliares posibles

### `not-found`
Para rutas inexistentes.

### alguna ruta informativa mínima
Solo si luego se justifica, no es obligatoria para V1.0.

## Flujo principal del usuario

## Flujo A. Entrada por home
Home → hero o búsqueda → catálogo → detalle de producto.

## Flujo B. Entrada directa a catálogo
Catálogo → filtros o búsqueda → detalle.

## Flujo C. Entrada directa a detalle
Ruta de producto → revisión → volver a catálogo si desea seguir explorando.

## Reglas de navegación

### 1. La home orienta
No debe intentar reemplazar todo el catálogo.

### 2. El catálogo concentra la exploración
Es la pantalla principal para descubrir productos.

### 3. El detalle aclara
La página de detalle debe resolver preguntas sobre ese producto visible.

### 4. Header estable
La navegación principal debe sentirse siempre disponible desde el header.

## Navegación desde header

### Elementos sugeridos
- logo enlazado a home;
- acceso a catálogo;
- buscador visible o fácil de localizar;
- alguna navegación mínima extra si luego la necesitas.

### Regla
El header no debe saturarse de opciones irrelevantes.

## Navegación entre catálogo y detalle

### Desde catálogo
Cada `ProductCard` debe poder llevar claramente al detalle.

### Desde detalle
Debe existir una forma clara de volver a seguir explorando.

### Regla
No esconder la navegación esencial ni depender de botones ambiguos.

## Estado visible asociado a navegación

La navegación debe convivir con:
- filtros activos;
- búsqueda actual;
- página actual;
- estado de carga;
- empty state o error.

## Qué no debe pasar

- rutas confusas o demasiado largas;
- home sin un camino claro hacia el catálogo;
- detalle desconectado del resto del sitio;
- navegación duplicada innecesariamente en varios lugares;
- header distinto en cada página sin razón.

## Resultado esperado

Las rutas y navegación del storefront deben dejar un recorrido público simple y coherente, donde el usuario pasa naturalmente de conocer la farmacia a explorar el catálogo y revisar detalles de producto sin fricción innecesaria.
