# Estrategia de claves y relaciones

## Propósito

Definir cómo se identificarán las entidades principales de farmacia y cómo se modelarán sus relaciones dentro de la base de datos.

## Principio general

Las claves deben dar estabilidad y las relaciones deben expresar el dominio con claridad. Una buena estrategia aquí reduce acoplamientos confusos y facilita tanto la normalización como la evolución futura del esquema.

## Estrategia de claves primarias

### ECR-001. Usar claves sustitutas como identidad principal
Las tablas núcleo de farmacia deben usar una PK artificial y estable.

### ECR-002. La PK no debe depender de datos de negocio cambiantes
No conviene usar nombre del producto o SKU como clave primaria.

### ECR-003. La elección exacta de tipo de clave debe ser consistente en todo el componente
Si se decide UUID o bigint autogenerado, debe sostenerse con criterio a lo largo del esquema.

## Estrategia de claves foráneas

### Categoria ← Producto
Si categoría se modela como tabla propia, cada producto debe tener una FK a una categoría válida.

### Producto ← Reserva
Cada reserva debe tener FK al producto reservado.

## Cardinalidades esperadas

### Categoria 1:N Producto
Una categoría puede clasificar múltiples productos.

### Producto 1:N Reserva
Un producto puede tener múltiples reservas a lo largo del tiempo si V1.1 incorpora ese comportamiento.

## Estrategia para relaciones opcionales

### Relación opcional entre producto y reserva
La reserva no debe ser obligatoria en V1.0. Por eso el diseño debe permitir que el producto exista sin depender de una estructura de reservas.

## Estrategia para estados y publicación

### ECR-010. Evaluar si un estado vive como CHECK o como tabla catálogo
En V1, varios estados pueden resolverse con restricciones simples. Si el dominio necesita más flexibilidad o trazabilidad, puede justificarse tabla de catálogo.

### ECR-011. No sobrediseñar catálogos innecesarios
No todo texto requiere una tabla aparte en esta etapa.

## Qué debe evitar esta estrategia

- claves naturales frágiles como eje del modelo;
- relaciones circulares innecesarias;
- claves cruzadas hacia consultorio;
- tablas puente artificiales sin necesidad real del dominio.

## Resultado esperado

La estrategia de claves y relaciones debe dejar preparada una base de farmacia clara, estable y apta para pasar a SQL relacional y luego a 3FN con el menor ruido conceptual posible.

