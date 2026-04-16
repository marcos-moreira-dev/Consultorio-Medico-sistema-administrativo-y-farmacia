# Formularios y validaciones

## Propósito

Definir cómo deben construirse los formularios e interacciones de entrada de `storefront-farmacia-angular`, especialmente búsqueda, filtros y cualquier control público visible que requiera validación o feedback claro.

## Principio general

En este storefront no hay un gran formulario administrativo ni una cuenta de cliente en V1.0. Aun así, sí existen puntos de interacción que deben estar bien diseñados:

- búsqueda;
- filtros;
- controles de exploración;
- formularios ligeros si luego aparece alguno de apoyo público.

## Regla principal

Los formularios del storefront deben ser:

- simples;
- claros;
- rápidos de usar;
- visualmente limpios;
- coherentes con el tono público de la farmacia.

## Interacciones principales que se comportan como formularios

## 1. Barra de búsqueda

### Función
Permitir al usuario encontrar productos por nombre o término simple.

### Requisitos
- input claro;
- foco visible;
- acción de búsqueda entendible;
- feedback cuando no haya resultados.

## 2. Filtros de catálogo

### Función
Permitir reducir el conjunto visible de productos.

### Ejemplos
- categoría;
- disponibilidad pública si se expone;
- ordenamiento.

### Requisitos
- controles compactos;
- estado visible del filtro activo;
- posibilidad clara de limpiar o ajustar.

## 3. Controles de paginación

Aunque no son formularios tradicionales, sí son interacción estructurada con el estado del catálogo.

### Requisitos
- página actual visible;
- navegación entendible;
- coherencia con filtros y búsqueda.

## Controles recomendados

- `input` para búsqueda;
- `select` o chips para categoría;
- `select` o control simple para ordenamiento;
- botones ligeros o acciones suaves para limpiar filtros.

## Reglas visuales de formularios ligeros

### Labels y ayudas
Si un control necesita explicación, debe ser breve y clara.

### Inputs
- altura consistente;
- borde suave;
- radio moderado;
- contraste suficiente;
- placeholder útil.

### Selects o filtros
- visibles;
- fáciles de escanear;
- no recargados con demasiadas opciones visuales.

## Validación en el storefront

## Tipos de validación relevantes

### 1. Validación de forma ligera
Por ejemplo:
- entrada vacía en búsqueda cuando no quieras enviar una solicitud trivial;
- parámetros inválidos en filtros si existiera tal caso.

### 2. Validación indirecta de resultado
La mayoría de la validación visible no será sobre “campos malos”, sino sobre:
- no hubo resultados;
- el backend no respondió;
- el detalle no se encontró.

### 3. Validación proveniente del backend
El frontend debe representar con claridad errores públicos devueltos por la API.

## Regla de oro
El frontend puede guiar la interacción, pero no debe inventar reglas de negocio que pertenecen al backend.

## Qué debe pasar cuando la búsqueda no encuentra nada

Eso no es un error técnico. Debe mostrarse como:
- estado vacío;
- mensaje claro;
- opción de limpiar búsqueda o filtros.

## Qué debe pasar cuando el detalle de producto no existe

Debe mostrarse un estado de error público o not found claro, no una card rota o una pantalla vacía sin explicación.

## Qué no debe pasar

- formularios pesados donde no hacen falta;
- filtros ocultos o difíciles de resetear;
- mensajes de error técnicos para el público;
- confundir “sin resultados” con “falló la red”;
- controles con estilos distintos entre sí sin sistema.

## Resultado esperado

Los formularios y validaciones del storefront deben sostener una interacción pública rápida, limpia y entendible, especialmente en búsqueda y filtros, sin volver el sitio más complejo de lo que necesita ser en V1.0.

