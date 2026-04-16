# Recursos públicos del storefront

Esta carpeta contiene los archivos estáticos que pueden ser servidos directamente por Angular CLI.

## Objetivo de esta estructura

Preparar branding, placeholders, íconos y recursos visuales antes de poblar `src/` con componentes reales.

## Zonas principales

### `branding/`
- logos principales;
- marcas reducidas;
- favicons.

### `images/`
- hero;
- banners;
- categorías;
- mock-products cuando hagan falta para demo controlada.

### `icons/`
- íconos de UI;
- estados;
- redes si luego se usan.

### `placeholders/`
- fallback de producto;
- empty state;
- avatar u otros recursos suaves si se necesitan.

### `seo/`
- Open Graph;
- social share.

## Regla de naming

Usar nombres consistentes, en minúsculas y con guiones medios, por ejemplo:

- `logo-principal.svg`
- `logo-secundario.svg`
- `hero-home-farmacia.webp`
- `placeholder-producto.png`
- `empty-state-catalogo.svg`

## Regla de diseño

No meter todavía assets decorativos porque sí. Esta carpeta existe para preparar el camino del storefront, no para llenarla de ruido visual.
