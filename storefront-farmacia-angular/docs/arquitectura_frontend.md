# Arquitectura frontend

## Propósito

Definir la arquitectura interna oficial de `storefront-farmacia-angular`, de modo que el storefront crezca con orden y no como una mezcla accidental de páginas, componentes y servicios.

## Estilo arquitectónico adoptado

Se adopta una arquitectura clásica de frontend Angular orientada a:

- páginas públicas bien definidas;
- componentes reutilizables;
- servicios de acceso a API;
- estado UI razonable;
- separación clara entre layout, catálogo y detalle.

## Principio general

La aplicación debe construirse como un frontend público de catálogo, no como una sola página gigante ni como un laboratorio visual desordenado.

## Capas o zonas recomendadas

## 1. Shell y layout público
Responsable de la estructura común de la app.

### Incluye
- header público;
- footer público;
- contenedor principal;
- composición base de páginas.

## 2. Páginas
Responsables del flujo principal del usuario.

### Páginas núcleo
- home;
- catálogo;
- detalle de producto;
- not-found si se implementa.

## 3. Componentes reutilizables
Piezas visuales que se repiten entre páginas.

### Ejemplos
- cards de producto;
- toolbar de catálogo;
- buscador;
- filtros;
- badges de disponibilidad;
- estados vacíos o de error.

## 4. Integración con API
Servicios responsables de hablar con `backend-farmacia`.

### Responsabilidades
- llamadas HTTP;
- tipado de responses;
- composición de filtros y paginación;
- adaptación mínima de datos para la UI.

## 5. Estado UI
Zona donde se sostiene el estado visible de catálogo, búsqueda, filtros, carga y error.

### Regla
Debe existir el estado suficiente para una UX coherente, sin sobrediseñar una capa compleja antes de necesitarla.

## Frontera técnica con backend

El storefront consume solo la **superficie pública** del backend-farmacia. No debe asumir contratos administrativos ni depender de internals del dominio.

## Patrones UI clave

### Home de entrada
Orienta, presenta y conduce al catálogo.

### Catálogo con toolbar
Búsqueda, filtros, grid y paginación.

### Detalle de producto
Amplía lo que la card apenas resume.

### Componentes consistentes
Cards, badges, loaders y estados deben hablar el mismo idioma visual.

## Qué no se busca en esta V1

- una arquitectura obsesivamente abstracta;
- múltiples sistemas de estado compitiendo entre sí;
- componentes duplicados por cada página;
- acoplamiento fuerte entre templates y respuestas crudas de API.

## Resultado esperado

La arquitectura del frontend debe dejar una aplicación Angular pública, clara y escalable, donde layout, páginas, componentes y servicios estén lo bastante separados como para trabajar con criterio y lo bastante cerca como para no perder simplicidad.
