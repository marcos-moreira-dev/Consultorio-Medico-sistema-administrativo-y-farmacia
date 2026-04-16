# Assets y recursos

## Propósito

Definir qué recursos visuales y técnicos necesita `storefront-farmacia-angular`, cómo deben clasificarse y qué papel cumplen dentro de la experiencia pública general del producto.

## Principio general

El storefront no debe depender de decenas de assets caóticos ni de imágenes decorativas puestas por llenar espacio. Debe apoyarse en recursos visuales claros, consistentes y directamente útiles para comunicar marca, producto y confianza.

## Tipos de assets esperados

## 1. Logo principal de la farmacia

### Uso
- header;
- footer;
- hero;
- favicon o variantes reducidas si luego se implementan.

### Requisito
Debe ser limpio, legible y adaptable a fondos claros.

## 2. Hero visual principal

### Uso
Home.

### Función
Transmitir identidad, confianza y orientación hacia el catálogo.

## 3. Banners promocionales controlados

### Uso
Bloques secundarios de home o secciones puntuales del catálogo.

### Función
Destacar mensajes relevantes sin saturar la experiencia.

## 4. Imágenes de producto

### Uso
Cards y detalle de producto.

### Función
Ser uno de los elementos principales del catálogo.

### Requisito
Deben llegar desde backend por URL controlada y visualizarse con fallback razonable.

## 5. Placeholder de producto

### Uso
Cuando el producto no tenga imagen disponible.

### Función
Evitar huecos visuales y mantener consistencia del grid.

## 6. Imágenes de categoría

### Uso
Bloques rápidos de categorías o navegación visual si luego se implementa.

### Función
Apoyar la exploración del catálogo.

## 7. Íconos y pictogramas

### Uso
- búsqueda;
- badges;
- navegación ligera;
- estados suaves;
- componentes de apoyo.

### Requisito
Deben ser sobrios, limpios y coherentes con la identidad del sitio.

## 8. Recursos de estilo

### Incluye
- hojas de estilo;
- tokens visuales;
- tipografías si se usan de forma controlada;
- definiciones compartidas de espaciado, sombras y radios.

## Clasificación recomendada de assets

### Branding
- logo principal;
- variantes del logo;
- favicon o equivalente.

### Hero y banners
- visual principal de home;
- banners secundarios;
- imágenes comerciales de apoyo.

### Catálogo
- imágenes de producto;
- placeholders;
- imágenes de categoría.

### UI
- íconos;
- loaders;
- ilustraciones de empty state o error si se usan.

## Reglas de uso

### Branding visible pero controlado
La marca debe sentirse presente, pero no invadir cada rincón del sitio.

### Hero con propósito
Las imágenes grandes deben usarse sobre todo en home o bloques justificados.

### Producto como protagonista
En catálogo y detalle, la imagen del producto debe importar más que el adorno general del sitio.

### Fallbacks consistentes
Cuando falte un asset, la UI no debe romperse ni verse descuidada.

## Qué no debe pasar

- banners decorativos sin función clara;
- imágenes de mala calidad saturando cards;
- assets incoherentes entre sí;
- branding gigante en todas las secciones;
- recursos visuales sin clasificación reconocible.

## Relación con prompts visuales

Los recursos deben apoyarse en `prompts_visuales_y_branding.md`, para reducir improvisación al generarlos con IA y mantener coherencia entre logo, hero, banners y placeholders.

## Resultado esperado

El sistema de assets y recursos del storefront debe quedar claro, comercialmente útil y visualmente controlado: los recursos correctos en los lugares correctos, sin ruido innecesario y con una identidad pública consistente para `La Alameda Farma`.