# Componentes de catálogo y cards

## Propósito

Definir las piezas visuales y funcionales centrales del storefront público de farmacia, especialmente las relacionadas con catálogo, cards, filtros, badges, CTAs y bloques de exploración.

## Principio general

El storefront no debe construirse como páginas enormes llenas de HTML repetido. La experiencia pública debe apoyarse en componentes reconocibles, reutilizables y visualmente consistentes.

La pieza reina del sitio es el catálogo, y por eso los componentes de catálogo deben quedar muy bien amarrados.

## Familias de componentes recomendadas

## 1. Componentes estructurales del storefront

### `PublicHeaderComponent`
Encabezado principal con logo, navegación pública, buscador y quizá CTA suave.

### `PublicFooterComponent`
Pie del sitio con identidad ligera, información básica y cierre visual sobrio.

### `SectionHeaderComponent`
Encabezado reutilizable para secciones como catálogo, destacados, categorías o beneficios.

## 2. Componentes hero y branding

### `HeroBannerComponent`
Bloque principal de la home con texto, imagen y CTAs.

### `PromoBannerComponent`
Banner secundario para destacar mensajes o productos visibles sin saturar la experiencia.

## 3. Componentes de catálogo

### `ProductGridComponent`
Contenedor principal de cards de producto.

### `ProductCardComponent`
Componente central del storefront. Debe presentar el producto de forma clara, ordenada y visualmente confiable.

### `ProductDetailSummaryComponent`
Bloque de lectura principal dentro del detalle del producto.

### `ProductImageBlockComponent`
Bloque responsable de mostrar imagen principal o fallback del producto.

## 4. Componentes de filtros y búsqueda

### `SearchBarComponent`
Barra de búsqueda simple y visible.

### `CategoryFilterComponent`
Selector o grupo de filtros por categoría.

### `CatalogToolbarComponent`
Bloque que agrupa búsqueda, filtros y ordenamiento.

### `PaginationComponent`
Componente de paginación pública del catálogo.

## 5. Componentes de estado y apoyo

### `AvailabilityBadgeComponent`
Badge de disponibilidad pública.

### `PriceInfoComponent`
Bloque para mostrar precio o información relevante si se expone públicamente.

### `EmptyStateComponent`
Estado vacío del catálogo o de una búsqueda sin resultados.

### `ErrorStateComponent`
Estado de error de carga o conectividad.

### `LoadingSkeletonComponent`
Skeleton o carga visual ligera para catálogo o detalle.

## Componente clave: `ProductCardComponent`

## Función
Es la unidad visual más repetida e importante del sitio.

## Contenido mínimo recomendado
- imagen principal del producto;
- nombre del producto;
- categoría o descriptor breve si aporta;
- disponibilidad pública;
- precio visible si aplica;
- CTA principal, por ejemplo `Ver detalle`.

## Regla compositiva obligatoria de la card

La `ProductCardComponent` se organiza así:

1. imagen;
2. nombre;
3. descriptor breve opcional;
4. disponibilidad;
5. precio si aplica;
6. CTA principal.

### Restricciones
- máximo un badge dominante;
- máximo un CTA dominante;
- máximo una línea de descriptor breve;
- no usar tres botones;
- no meter descripciones largas;
- no permitir cards con alturas caóticamente distintas si el grid puede mantenerse estable.

### Medidas visuales recomendadas
- padding interno: `16px`;
- separación entre elementos internos: `8px` o `12px`;
- sombra ligera;
- borde sutil o superficie ligeramente diferenciada;
- radio moderado.

## Jerarquía interna

### Parte superior
Imagen del producto.

### Parte media
Nombre y descriptor.

### Parte inferior
Badge de disponibilidad, precio si existe y CTA.

## Reglas visuales
- limpia;
- sin exceso de texto;
- suficiente aire interno;
- sombra suave;
- borde sutil;
- no demasiado alta si el grid va a tener varias filas.

## Qué evitar en las cards
- demasiados badges simultáneos;
- tres botones compitiendo;
- exceso de descripción;
- imagen minúscula o recortada de forma extraña;
- saturación de promociones.

## Componente clave: `AvailabilityBadgeComponent`

## Función
Mostrar de forma rápida si el producto está disponible, agotado o en un estado equivalente público.

## Reglas
- texto claro;
- color coherente con el estado;
- tamaño compacto;
- no depender solo del color para comunicar.

## Componente clave: `SearchBarComponent`

## Función
Permitir encontrar productos por texto simple.

## Contenido recomendado
- input principal;
- icono de búsqueda opcional;
- feedback visual claro al foco.

## Regla
Debe ser visible y fácil de usar, no escondido ni exageradamente grande.

## Componente clave: `CategoryFilterComponent`

## Función
Permitir explorar productos por categoría.

## Posibles representaciones
- chips;
- select;
- lista horizontal;
- bloque lateral si luego creciera.

## Regla
Debe seguir siendo claro y compacto.

## Componente clave: `HeroBannerComponent`

## Función
Presentar la marca, orientar al usuario y empujarlo a explorar el catálogo.

## Contenido recomendado
- headline principal;
- subtítulo;
- CTA principal;
- CTA secundario opcional;
- visual hero.

## Reglas
- no recargar de texto;
- no meter demasiadas ofertas;
- dejar respirar la composición.

## Relación entre componentes

### Home
- `PublicHeaderComponent`
- `HeroBannerComponent`
- secciones con `SectionHeaderComponent`
- `ProductGridComponent`
- `PromoBannerComponent` si aporta
- `PublicFooterComponent`

### Catálogo
- `PublicHeaderComponent`
- `CatalogToolbarComponent`
- `ProductGridComponent`
- `PaginationComponent`
- estados de loading, vacío o error

### Detalle
- `PublicHeaderComponent`
- `ProductImageBlockComponent`
- `ProductDetailSummaryComponent`
- `AvailabilityBadgeComponent`
- `PublicFooterComponent`

## Resultado esperado

La biblioteca de componentes de catálogo y cards del storefront debe permitir construir una farmacia web clara, respirada y comercialmente confiable, donde hero, filtros, cards, badges y grids hablen el mismo lenguaje visual y funcional.
