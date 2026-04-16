# Manejo de estado

## Propósito

Definir cómo debe sostenerse el estado visible de `storefront-farmacia-angular`, distinguiendo claramente entre estado de navegación, filtros, resultados, loading, error y detalle del producto.

## Principio general

El storefront no necesita una complejidad exagerada de estado global, pero sí necesita una estrategia clara para que el usuario no sienta que el sitio “se reinicia” mentalmente cada vez que busca, filtra o cambia de página.

La idea central es esta:

- el estado debe servir a la experiencia pública del catálogo;
- no debe complicarse como si fuera un panel administrativo enorme;
- sí debe ser suficientemente claro para sostener filtros, búsqueda, paginación y detalle.

## Tipos de estado relevantes

## 1. Estado de navegación pública

Representa dónde está el usuario dentro del sitio.

### Ejemplos
- home;
- catálogo;
- detalle de producto;
- página no encontrada si existe.

## 2. Estado de catálogo

Representa la información visible del listado de productos.

### Ejemplos
- productos cargados;
- página actual;
- total de resultados;
- resultados vacíos;
- loading del catálogo.

## 3. Estado de filtros

Representa los criterios activos aplicados al catálogo.

### Ejemplos
- término de búsqueda;
- categoría seleccionada;
- disponibilidad visible si se expone como filtro;
- ordenamiento actual.

## 4. Estado de detalle de producto

Representa la carga y visualización del producto seleccionado.

### Ejemplos
- producto cargado;
- loading del detalle;
- error de detalle;
- fallback de imagen.

## 5. Estado de UI transversal

Representa cosas como:
- loading global suave si hace falta;
- banners o mensajes temporales;
- error de conectividad;
- skeletons visibles.

## Principio de diseño del estado

El estado debe ser:

- explícito;
- fácil de entender;
- útil para la experiencia;
- no exageradamente complejo.

No se busca una maquinaria de estado pesada solo por costumbre. Se busca claridad.

## Estado mínimo que conviene modelar

## Para catálogo
- `query`;
- `categoriaSeleccionada`;
- `sort`;
- `page`;
- `size`;
- `items`;
- `totalElements`;
- `loading`;
- `error`;
- `empty`.

## Para detalle
- `productoActual`;
- `loadingDetalle`;
- `errorDetalle`.

## Para layout público
- estado mínimo del header y navegación;
- quizá término de búsqueda global si decides que el header tenga buscador conectado.

## Estrategia recomendada

### Estado local por página cuando sea suficiente
Muy útil para páginas simples como home.

### Estado más centralizado para catálogo
Conviene cuando búsqueda, filtros y paginación conviven y deben mantenerse consistentes.

### Estado derivado claro
No duplicar innecesariamente datos que ya pueden derivarse del response de la API o de filtros activos.

## Casos importantes a sostener bien

## Caso 1. Búsqueda
El usuario escribe o envía un término y el catálogo debe reflejarlo sin perder claridad de contexto.

## Caso 2. Filtros por categoría
La categoría activa debe verse clara en UI y sostener el resultado que se muestra.

## Caso 3. Paginación
La página actual debe ser coherente con los filtros activos.

## Caso 4. Navegación a detalle
El usuario abre un producto y el frontend debe cargar ese detalle sin romper la experiencia general del sitio.

## Caso 5. Volver a catálogo
Idealmente, el usuario debería poder volver a explorar sin sentir que todo desapareció arbitrariamente.

## Qué no debe pasar

- filtros activos invisibles;
- resultados que no corresponden a los controles visibles;
- paginación que se desincroniza con la búsqueda;
- estado duplicado en varios componentes sin necesidad;
- lógica de estado escondida dentro de componentes puramente visuales.

## Resultado esperado

El manejo de estado del storefront debe permitir que búsqueda, filtros, catálogo y detalle funcionen como una experiencia pública coherente, clara y fácil de mantener, sin caer en sobreingeniería innecesaria.

