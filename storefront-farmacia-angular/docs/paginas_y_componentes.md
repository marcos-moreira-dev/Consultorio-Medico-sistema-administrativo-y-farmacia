# Páginas y componentes

## Propósito

Definir las páginas principales del storefront público de farmacia y los componentes que debe contener cada una, de modo que la implementación no termine siendo una mezcla caótica de secciones sin jerarquía clara.

## Principio general

El storefront debe tener pocas páginas, pero bien defendidas. No se busca un sitio gigantesco. Se busca una experiencia pública clara, útil y visualmente consistente.

## Páginas principales esperadas en V1.0

## 1. Home

### Objetivo
Presentar la marca, orientar al usuario y conducirlo hacia la exploración del catálogo.

### Componentes principales
- `PublicHeaderComponent`
- `HeroBannerComponent`
- bloque de búsqueda rápida
- bloque de categorías o accesos rápidos
- sección de productos visibles o destacados
- `PromoBannerComponent` opcional
- `PublicFooterComponent`

### Función
No debe intentar reemplazar todo el catálogo. Debe servir como puerta de entrada clara y comercialmente confiable.

## 2. Página de catálogo

### Objetivo
Permitir explorar productos públicos con filtros, búsqueda, grid y paginación.

### Componentes principales
- `PublicHeaderComponent`
- `SectionHeaderComponent`
- `CatalogToolbarComponent`
- `SearchBarComponent`
- `CategoryFilterComponent`
- `ProductGridComponent`
- `PaginationComponent`
- `EmptyStateComponent`
- `ErrorStateComponent`
- `LoadingSkeletonComponent`
- `PublicFooterComponent`

### Función
Debe ser la pantalla operativa principal del storefront.

## 3. Página de detalle de producto

### Objetivo
Mostrar el producto público con más claridad y contexto.

### Componentes principales
- `PublicHeaderComponent`
- bloque de imagen principal
- bloque de detalle resumido
- `AvailabilityBadgeComponent`
- CTA principal
- descripción o información útil visible
- `PublicFooterComponent`

### Función
Permitir al usuario entender el producto sin tener que deducirlo desde una card pequeña.

## Páginas auxiliares posibles

### Página de error genérica
Puede existir para errores de navegación o rutas no encontradas.

### Página de disponibilidad especial o ayuda
Solo si luego se justifica, no como obligación de V1.0.

## Jerarquía funcional entre páginas

### Home
Presenta.

### Catálogo
Explora.

### Detalle
Aclara y profundiza.

Esa secuencia debe ser muy clara.

## Composición recomendada de cada página

## Home

### Bloque 1
Header visible con marca y navegación mínima.

### Bloque 2
Hero principal con headline, texto breve y CTA.

### Bloque 3
Búsqueda rápida o acceso a categorías.

### Bloque 4
Sección de productos visibles o destacados.

### Bloque 5
Footer limpio.

## Catálogo

### Bloque 1
Header.

### Bloque 2
Encabezado contextual del catálogo.

### Bloque 3
Barra de búsqueda y filtros.

### Bloque 4
Grid de productos.

### Bloque 5
Paginación o navegación de resultados.

### Bloque 6
Footer.

## Detalle de producto

### Bloque 1
Header.

### Bloque 2
Zona principal de dos columnas:
- imagen o bloque visual;
- resumen del producto.

### Bloque 3
Descripción o datos visibles.

### Bloque 4
Footer.

## Relación entre páginas y componentes reutilizables

Los componentes reutilizables deben permitir que:
- la home no reinvente cards;
- el catálogo no reinvente filtros;
- el detalle no reinvente badges;
- el header y footer sean consistentes en todo el sitio.

## Qué no debe pasar

- home con demasiadas secciones sin foco;
- catálogo que se ve distinto a la home en tono visual;
- detalle sin suficiente jerarquía visual;
- componentes duplicados porque cada página decidió hacer su propia versión.

## Resultado esperado

La definición de páginas y componentes debe dejar un storefront donde home, catálogo y detalle se entienden como partes de un mismo producto, con roles claros y una composición coherente para explorar medicamentos y productos visibles de la farmacia.

