# Consumo de API

## Propósito

Definir cómo debe consumir `storefront-farmacia-angular` la API pública de `backend-farmacia`, qué tipo de respuestas espera y cómo debe tratar filtros, paginación, errores y disponibilidad pública.

## Principio general

El frontend no debe adivinar la API ni acoplarse a detalles accidentales del backend. Debe consumirla de forma clara, estable y tipada, apoyándose en contratos públicos bien definidos.

## Frontera técnica

Este frontend consume **solo** la superficie pública de `backend-farmacia`.

No debe:
- conectarse a rutas administrativas;
- asumir datos internos no públicos;
- replicar reglas que pertenecen al backend.

## Contrato público actual del backend

A día de hoy, el backend expone estas rutas públicas como base de integración:

- `GET /api/v1/catalogo`
- `GET /api/v1/catalogo/buscar`
- `GET /api/v1/catalogo/categorias`
- `GET /api/v1/catalogo/:productoId`
- recursos públicos de imagen bajo `/media/...`

## Tipo de respuestas esperadas

Se asume una convención de respuesta uniforme del proyecto.

### Envoltorio base
Debe permitir leer:
- `ok`
- `data`
- `meta` cuando aplique
- `correlationId` cuando llegue
- `timestamp`

### Respuesta de error
Debe permitir leer:
- `ok = false`
- `error.code`
- `error.message`
- `error.details` cuando aplique
- `correlationId`
- `timestamp`

## Qué debe consumir el frontend

### 1. Catálogo público
Para:
- listado de productos visibles;
- búsqueda;
- filtros;
- paginación.

### 2. Detalle público de producto
Para:
- vista individual del producto;
- imagen principal;
- disponibilidad;
- información visible pública.

### 3. Categorías públicas
Para navegación o filtrado visible del catálogo.

## Parámetros públicos actualmente esperados

### Listado / búsqueda
- `page`
- `limit`
- `q`
- `categoriaId`
- `sortBy`
- `sortDirection`

## Modelos que el frontend debe representar

### Catálogo resumido
- `productoId`;
- `categoriaId`;
- `nombreCategoria` opcional;
- `nombreProducto`;
- `presentacion`;
- `descripcionBreve` opcional o nula;
- `precioVisible`;
- `estadoDisponibilidad`;
- `disponible`;
- `imagenUrl` opcional o nula.

### Detalle público
- `productoId`;
- `categoriaId`;
- `nombreCategoria` opcional;
- `nombreProducto`;
- `presentacion`;
- `descripcionBreve` opcional o nula;
- `precioVisible`;
- `estadoDisponibilidad`;
- `disponible`;
- `imagenUrl` opcional o nula;
- `fechaActualizacion`.

### Categorías públicas
- `categoriaId`;
- `nombreCategoria`;
- `cantidadProductosVisibles`.

### Metadatos de paginación
El frontend debe modelar `meta` para catálogo y búsqueda. No conviene ignorarlo ni derivarlo de forma casera en la UI.

## Reglas de consumo

### 1. No asumir datos internos
Si el backend no devuelve un campo públicamente, el frontend no debe inventarlo.

### 2. No acoplar la UI a entidades crudas
Conviene adaptar las respuestas a modelos frontend legibles y estables.

### 3. Tratar loading, vacío y error como estados distintos
No mezclar “sin resultados” con “falló la API”.

### 4. Respetar paginación y meta
No ignorar la metadata si el backend la envía para navegación de resultados.

### 5. Usar `productoId` como identificador operativo en V1
Mientras el backend público no exponga slug estable, el storefront debe trabajar con `productoId` como identificador principal de detalle.

## Casos importantes de consumo

### Caso 1. Cargar home
Puede requerir productos visibles, bloques destacados o categorías rápidas, según el diseño final.

### Caso 2. Cargar catálogo
Debe soportar:
- búsqueda;
- filtros;
- paginación;
- loading;
- vacío;
- error.

### Caso 3. Cargar detalle de producto
Debe recuperar un producto público por `productoId`.

### Caso 4. Mostrar disponibilidad
La UI no debe reinterpretar la disponibilidad. Debe representarla según la información pública entregada por backend.

## Qué no debe pasar

- llamadas HTTP dispersas en muchos componentes visuales;
- lógica de API mezclada con rendering de card;
- suposiciones sobre rutas admin;
- perder metadata de paginación por no modelarla bien;
- tratar todo error como “no hay productos”.

## Resultado esperado

El consumo de API del storefront debe dejar una integración limpia, tipada y coherente con la superficie pública de farmacia, permitiendo que catálogo, filtros y detalle funcionen con claridad sin acoplar el frontend a detalles innecesarios del backend.
