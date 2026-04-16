# Estructura de carpetas

## Propósito

Definir una estructura de carpetas razonable para `storefront-farmacia-angular`, alineada con Angular y con la arquitectura documental del componente.

## Principio general

La estructura debe ayudar a encontrar rápido páginas, componentes, servicios, modelos y recursos visuales. No conviene ni una jerarquía plana caótica ni una profundidad innecesaria.

## Estructura sugerida de alto nivel

```text
src/
  app/
    core/
    layout/
    pages/
    features/
    shared/
  assets/
  environments/
```

## `app/core`
Contiene piezas transversales de la aplicación.

### Ejemplos razonables
- configuración global;
- servicios base de API;
- interceptores;
- utilidades transversales;
- modelos compartidos de respuesta.

## `app/layout`
Contiene la estructura pública estable del sitio.

### Ejemplos
- `public-header/`
- `public-footer/`
- `public-shell/`

## `app/pages`
Contiene las páginas principales de navegación.

### Páginas esperadas
- `home/`
- `catalogo/`
- `producto-detalle/`
- `not-found/` si aplica.

## `app/features`
Contiene componentes o grupos funcionales del dominio visible.

### Ejemplos razonables
- `catalogo/`
- `busqueda/`
- `filtros/`
- `producto/`

## `app/shared`
Contiene piezas reutilizables de UI y soporte.

### Ejemplos
- `components/`
- `ui/`
- `pipes/`
- `directives/`
- `utils/`
- `types/`

## `assets`
Contiene branding, imágenes, placeholders, íconos o recursos estáticos controlados.

## `environments`
Contiene archivos de entorno propios de Angular si la versión y el setup elegido los utilizan.

## Regla de organización

- páginas para navegación;
- features para lógica o UI de dominio;
- shared para reutilización transversal;
- core para base técnica común.

## Qué conviene evitar

- meter todo en `components/`;
- duplicar componentes similares por cada página;
- esconder servicios de API dentro de carpetas arbitrarias;
- mezclar assets con código fuente.

## Resultado esperado

La estructura de carpetas del storefront debe dejar una base Angular fácil de leer, fácil de poblar y suficientemente ordenada como para trabajar luego con IA o manualmente sin degradar la coherencia del proyecto.
