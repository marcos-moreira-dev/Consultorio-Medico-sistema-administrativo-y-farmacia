# Manejo de errores UI

## Propósito

Definir cómo debe reaccionar `storefront-farmacia-angular` ante errores públicos, estados vacíos, fallos de red o problemas de carga, diferenciando claramente entre error técnico, resultado vacío y recurso no encontrado.

## Principio general

La UI pública de farmacia no debe ocultar errores ni dramatizarlos. Debe explicarlos de forma clara, sobria y útil para una persona que solo quiere navegar el catálogo o revisar un producto.

## Tipos de error que la UI debe distinguir

## 1. Estado vacío
No es un error técnico.

### Ejemplos
- búsqueda sin resultados;
- categoría sin productos visibles;
- filtros demasiado restrictivos.

### Representación recomendada
- mensaje claro;
- ilustración o placeholder ligero opcional;
- opción de limpiar filtros o volver al catálogo completo.

## 2. Error de conectividad
Sucede cuando el frontend no puede hablar con el backend o la red falla.

### Representación recomendada
- mensaje comprensible;
- opción de reintentar;
- no confundir con “no hay productos”.

## 3. Error de carga de catálogo
Sucede cuando la respuesta del catálogo falla o llega inválida.

### Representación recomendada
- `ErrorStateComponent` claro;
- CTA de reintento;
- mantener el layout general del sitio.

## 4. Error de detalle de producto
Sucede cuando el producto no existe o no puede cargarse.

### Representación recomendada
- estado claro de producto no disponible o no encontrado;
- navegación para volver al catálogo.

## 5. Error técnico inesperado
Sucede cuando ocurre una falla no prevista en la UI o en la integración.

### Representación recomendada
- mensaje sobrio;
- fallback visual estable;
- logs técnicos internos si aplica, no expuestos al usuario.

## Dónde mostrar el error según contexto

## Home
Debe ser liviano y no alarmista.

## Catálogo
Debe existir un estado visual claro dentro del área de resultados.

## Detalle de producto
Debe existir una vista o bloque claro que explique el problema y ofrezca salida.

## Mensajes de error: tono recomendado

Los mensajes deben ser:

- claros;
- breves;
- no técnicos para el público general;
- coherentes con una farmacia cercana y confiable.

### Ejemplo bueno
"No pudimos cargar los productos en este momento. Intenta nuevamente."

### Ejemplo malo
"Error 500 al consumir endpoint público."

## Estados visuales de error

### Color
- usar color de error sobrio;
- no inundar toda la pantalla de rojo;
- mantener jerarquía visual limpia.

### Superficie
- panel contextual;
- bloque de error con borde y contenido claro;
- CTA de reintento cuando corresponda.

## Reintentos

Conviene ofrecer reintento en:
- carga inicial de catálogo;
- recarga de detalle;
- errores temporales de conectividad.

No conviene ofrecer un “reintentar” cuando simplemente no hubo resultados de una búsqueda bien ejecutada.

## Diferencia clave: vacío vs error

### Vacío
La búsqueda funcionó, pero no encontró coincidencias.

### Error
La búsqueda o carga falló técnicamente.

La UI debe representar esas dos situaciones de forma diferente.

## Qué no debe pasar

- una pantalla en blanco sin explicación;
- mostrar error técnico al usuario final con jerga innecesaria;
- tratar todo como si fuera “no hay productos”;
- un diseño de error tan agresivo que rompa la confianza del sitio;
- romper todo el layout por un error local del catálogo.

## Resultado esperado

El manejo de errores UI del storefront debe hacer que los problemas se entiendan rápido, se distingan correctamente de los estados vacíos y no destruyan la experiencia pública del sitio, manteniendo siempre una sensación de orden y confianza.