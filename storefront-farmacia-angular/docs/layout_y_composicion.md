# Layout y composición

## Propósito

Definir cómo deben organizarse espacialmente las páginas de `storefront-farmacia-angular`, con énfasis en home, catálogo y detalle de producto.

## Principio general

La composición del sitio debe ser clara y repetible. El usuario debe sentir continuidad entre páginas, no un cambio completo de lenguaje cada vez que navega.

## Layout base adoptado

Se adopta un layout público clásico:

- header estable;
- contenido central cambiante por ruta;
- footer estable.

## Decisiones de composición obligatorias

### Home
- hero principal;
- acceso a catálogo o búsqueda;
- bloque de productos o categorías;
- footer.

No convertir la home en una página saturada de banners.

### Catálogo
- toolbar visible;
- grid ordenado;
- cards homogéneas;
- paginación o navegación de resultados;
- estados de loading, vacío y error.

### Detalle
- una zona visual dominante para imagen e información principal;
- CTA clara;
- navegación de regreso visible;
- bloques secundarios contenidos.

### Márgenes generales
- padding lateral de página: `24px` a `32px`;
- separación entre secciones grandes: `32px`;
- gap entre cards: `16px` o `24px`.

### Regla de continuidad
La home, el catálogo y el detalle deben sentirse parte del mismo sistema visual, no sitios distintos.

## Home

### Orden compositivo sugerido
1. header
2. hero principal
3. acceso rápido a catálogo o búsqueda
4. bloque de productos o categorías visibles
5. footer

### Regla
La home orienta y presenta. No debe intentar reemplazar por completo al catálogo.

## Catálogo

### Orden compositivo sugerido
1. header
2. encabezado de sección
3. toolbar con búsqueda y filtros
4. grid de productos
5. paginación o navegación de resultados
6. footer

### Regla
La experiencia de exploración debe sentirse dominante y clara.

## Detalle de producto

### Orden compositivo sugerido
1. header
2. bloque principal de dos columnas o equivalente responsivo
3. descripción o información visible
4. CTA o navegación de regreso
5. footer

### Regla
El detalle debe ampliar con claridad lo que la card solo resume.

## Reglas de composición transversal

- jerarquía visual clara;
- bloques con suficiente respiración;
- CTAs principales visibles;
- consistencia de márgenes y paddings;
- grids ordenados y fáciles de escanear.

## Responsive

El layout debe degradarse bien a móvil, especialmente en:
- header;
- toolbar de catálogo;
- grid de productos;
- detalle de producto.

## Qué no debe pasar

- home recargada;
- catálogo desalineado;
- detalle sin foco visual claro;
- saltos bruscos de espaciado o densidad entre páginas.

## Resultado esperado

El layout y la composición del storefront deben sostener una experiencia pública coherente, respirada y claramente orientada a descubrir y revisar productos.
