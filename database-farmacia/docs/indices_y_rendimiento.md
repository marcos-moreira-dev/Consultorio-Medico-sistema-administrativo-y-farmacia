# Índices y rendimiento

## Propósito

Definir criterios razonables de indexación y rendimiento para la base de datos de farmacia dentro del alcance de la V1.

## Principio general

La V1 no necesita optimización obsesiva, pero sí debe evitar decisiones ingenuas que vuelvan torpe la búsqueda de productos, la revisión del catálogo o la consulta de disponibilidad. El rendimiento debe ser suficiente para una farmacia pequeña realista.

## Áreas donde más importa el rendimiento

### 1. Búsqueda de productos
Debe poder encontrarse rápidamente un producto por nombre o coincidencia razonable.

### 2. Catálogo público
Las consultas por productos visibles y publicables deben ser razonablemente eficientes.

### 3. Gestión administrativa
La revisión por estado, disponibilidad o inactividad debe mantenerse clara y ágil.

### 4. Reservas
Si V1.1 incorpora reservas, las búsquedas por producto y estado deberán responder bien.

## Criterios de indexación esperados

### IDX-001. Indexar claves foráneas relevantes
Particularmente en tablas como `producto` y `reserva` si las relaciones existen.

### IDX-002. Indexar campos de consulta frecuente
Especialmente nombre de producto, estado, publicación y disponibilidad cuando formen parte del modelo final.

### IDX-003. No indexar por costumbre todo indiscriminadamente
Demasiados índices también encarecen escritura y complejidad.

### IDX-004. Priorizar índices con valor real para el dominio
El índice debe responder a una consulta esperable de la farmacia.

## Consultas que conviene tener en mente

- buscar producto por nombre;
- listar productos visibles;
- listar productos por estado;
- revisar productos agotados o no publicables;
- recuperar reservas por producto si la evolución las incorpora.

## Rendimiento y normalización

La 3FN ayuda a mantener integridad y claridad, pero no elimina la necesidad de pensar en consultas frecuentes. Por eso, después de normalizar, conviene revisar si ciertos índices resultan necesarios para mantener buena respuesta operativa.

## Qué evitar

- optimización prematura sin consulta real detrás;
- falta total de índices en campos de búsqueda frecuente;
- confundir un problema de modelado con un problema de indexación;
- diseñar como si el volumen fuera empresarial cuando el proyecto es de escala pequeña.

## Resultado esperado

Este documento debe dejar una postura clara: la farmacia no necesita tuning extremo, pero sí una base razonablemente bien indexada para que el catálogo y la operación interna se sientan ágiles y estudiables.

