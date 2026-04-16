# Modelo relacional

## Propósito

Describir la forma relacional esperada de la base de farmacia, conectando el dominio operativo con las futuras tablas, relaciones y restricciones del esquema SQL.

## Punto de partida

El modelo relacional de farmacia debe derivarse del dominio ya definido para el subdominio comercial del sistema:

- un producto puede existir sin estar publicado;
- un producto puede estar activo o inactivo;
- la visibilidad pública y la disponibilidad operativa no son exactamente lo mismo;
- en una evolución futura puede aparecer una reserva asociada a producto.

## Entidades relacionales núcleo esperadas

### Producto
Representa el elemento comercial administrado por la farmacia.

### Categoria
Representa una clasificación simple o funcional de los productos, si se decide separarla como entidad.

### Reserva
Representa el apartado formal de unidades de un producto. Se considera evolución natural de V1.1 y no obligación de V1.0.

## Entidades auxiliares probables

Según el nivel de detalle que adopte la V1, pueden aparecer además:

- catálogos cerrados de estado;
- tabla de auditoría o rastro estructurado;
- referencias de usuario interno si se decide persistir actor responsable.

## Relaciones relacionales esperadas

### Categoria 1:N Producto
Una categoría puede agrupar muchos productos.
Cada producto pertenece a una sola categoría si el modelo decide formalizarla así.

### Producto 1:N Reserva
Un producto puede tener varias reservas a lo largo del tiempo si V1.1 incorpora ese comportamiento.
Cada reserva pertenece a un solo producto.

## Decisiones relacionales importantes

### 1. Producto es la entidad eje
El catálogo gira alrededor del producto como objeto comercial principal.

### 2. Publicación no equivale a existencia interna
Un producto puede existir en la base sin formar parte del catálogo público.

### 3. Disponibilidad no equivale siempre a stock exacto publicado
El público no necesita conocer necesariamente el conteo preciso de unidades.

### 4. Reserva no debe forzarse en V1.0
Debe pensarse como una posible expansión, no como parte obligatoria del primer esquema si todavía no aporta suficiente valor.

## Nivel de normalización esperado

El componente se estudiará en varias capas:

- modelo conceptual;
- versión 1FN;
- informe de normalización;
- esquema final 3FN.

Por tanto, este documento no fija todavía todos los tipos ni columnas finales, pero sí la estructura relacional de alto nivel que guiará las decisiones posteriores.

## Qué debe evitar el modelo relacional

- mezclar datos del consultorio;
- convertir el producto en una entidad confusa que cargue demasiadas responsabilidades;
- exponer como relación relacional aquello que realmente pertenece a la lógica pública o del backend;
- sobrediseñar como si ya existieran múltiples bodegas, compras complejas o una cadena empresarial.

## Resultado esperado

El modelo relacional de farmacia debe ofrecer una estructura suficientemente clara como para pasar después a tablas concretas, claves, restricciones e índices sin perder fidelidad respecto al dominio comercial y operativo.

