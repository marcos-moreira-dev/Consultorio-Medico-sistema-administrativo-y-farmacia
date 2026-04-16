# Wireframes farmacia

## Propósito

Describir la estructura conceptual de las pantallas principales de farmacia, distinguiendo claramente entre la capa pública de catálogo y la capa administrativa interna.

## Principio general

La farmacia tiene dos experiencias distintas dentro del proyecto:

- una experiencia pública o semipública para consulta de productos;
- una experiencia administrativa para mantenimiento del catálogo y la disponibilidad.

Ambas deben compartir coherencia visual, pero no confundirse en propósito ni densidad.

## A. Wireframes de farmacia pública

### Pantalla 1. Inicio o catálogo principal

#### Objetivo
Presentar el catálogo visible de forma clara y navegable.

#### Elementos sugeridos
- buscador principal;
- filtros simples o categorías básicas;
- listado o grilla de productos visibles;
- indicador sencillo de disponibilidad;
- acceso al detalle del producto.

#### Observaciones
Debe sentirse más abierta y aireada que el consultorio, priorizando exploración rápida.

### Pantalla 2. Resultado de búsqueda

#### Objetivo
Permitir encontrar productos por coincidencia simple de forma útil.

#### Elementos sugeridos
- campo de búsqueda persistente;
- lista de resultados;
- información mínima visible por producto;
- indicación de disponibilidad;
- posibilidad de abrir detalle.

#### Observaciones
No debe parecer un sistema interno. Debe mantenerse simple y legible.

### Pantalla 3. Detalle de producto

#### Objetivo
Mostrar la información pública autorizada de un producto concreto.

#### Elementos sugeridos
- nombre del producto;
- presentación;
- categoría;
- descripción breve;
- disponibilidad visible;
- precio visible si aplica;
- imagen si el diseño la contempla.

#### Observaciones
La pantalla debe explicar el producto sin exponer datos administrativos internos.

## B. Wireframes de farmacia administrativa

### Pantalla 4. Listado administrativo de productos

#### Objetivo
Permitir revisar el catálogo desde la lógica interna de operación.

#### Elementos sugeridos
- buscador interno;
- tabla o lista de productos;
- estado del producto;
- indicador de publicación;
- disponibilidad o stock básico;
- acciones rápidas de editar, publicar, despublicar o inactivar.

#### Observaciones
Debe sentirse más densa y operativa que la capa pública, pero siempre clara.

### Pantalla 5. Formulario de producto

#### Objetivo
Crear o editar un producto con la información mínima necesaria.

#### Elementos sugeridos
- nombre del producto;
- presentación;
- categoría;
- descripción breve;
- precio visible si aplica;
- estado;
- control de publicación;
- disponibilidad o stock básico.

#### Observaciones
El formulario debe ordenar lo comercial, lo visible y lo operativo sin mezclarlo confusamente.

### Pantalla 6. Gestión de disponibilidad

#### Objetivo
Ajustar agotado, disponible, publicación o stock básico de forma rápida.

#### Elementos sugeridos
- referencia clara del producto;
- estado actual;
- acción para cambiar disponibilidad;
- confirmaciones proporcionales cuando aplique.

#### Observaciones
Debe permitir mantenimiento rápido sin obligar a reabrir formularios pesados para cambios simples.

## Relación entre pantallas

Los recorridos naturales deberían ser:

- catálogo → búsqueda → detalle;
- listado administrativo → edición → publicación;
- listado administrativo → ajuste de disponibilidad;
- cambio administrativo → reflejo posterior en capa pública.

## Resultado esperado

Los wireframes de farmacia deben servir como guía para dos experiencias complementarias: una pública, clara y comercialmente sobria; y otra administrativa, directa y orientada al mantenimiento del catálogo sin confusión entre lo interno y lo visible.