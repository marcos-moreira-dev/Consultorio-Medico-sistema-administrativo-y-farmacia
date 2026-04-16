# Contexto para el modelo conceptual de `database-farmacia`

## Propósito

Este documento sirve como contexto base para diseñar el **modelo conceptual** del componente `database-farmacia`. Está pensado para alimentar una IA o para servir como referencia previa al diagrama, de modo que el resultado no sea una colección arbitraria de entidades, sino una representación coherente del subdominio farmacia.

## Regla principal de alcance

Este modelo conceptual debe concentrarse **exclusivamente en la farmacia**.

Eso significa que aquí sí pertenecen conceptos como:

- producto;
- categoría simple, si se decide modelarla como entidad;
- estado del producto;
- publicación o visibilidad pública;
- disponibilidad operativa;
- reserva, si luego se decide incorporarla como parte de la evolución V1.1.

Y aquí no pertenecen conceptos como:

- paciente;
- cita;
- atención;
- cobro;
- cualquier entidad propia del consultorio.

La farmacia tiene una base de datos independiente del consultorio. Por tanto, este modelo conceptual no debe asumir tablas compartidas, claves foráneas cruzadas ni relaciones directas con el otro subdominio.

## Intención del modelo conceptual

El objetivo del modelo conceptual no es escribir SQL todavía. Su función es representar el dominio de farmacia a nivel de:

- entidades;
- atributos relevantes;
- relaciones;
- cardinalidades;
- límites del subdominio.

Debe ser lo bastante claro para que luego pueda transformarse en:

1. una versión tabular en 1FN;
2. un informe de normalización;
3. un esquema final en 3FN;
4. SQL estudiable en PostgreSQL.

## Enfoque de diseño

Este modelo debe representar una **farmacia pequeña y realista**, no una cadena de retail grande ni un ERP empresarial completo.

La meta es modelar con fidelidad operativa suficiente:

- catálogo interno de productos;
- clasificación básica;
- visibilidad pública controlada;
- disponibilidad operativa;
- posible reserva futura.

No debe inflarse con compras complejas, proveedores empresariales, bodegas múltiples, logística avanzada ni integración fuerte con consultorio en esta fase.

## Entidades núcleo recomendadas

### 1. Categoria

Representa una clasificación simple o funcional para agrupar productos.

**Atributos conceptuales sugeridos:**
- categoria_id
- nombre_categoria
- descripcion_breve
- fecha_creacion
- fecha_actualizacion

**Notas:**
- esta entidad es opcional a nivel conceptual, pero recomendable si quieres evitar repetir textos de clasificación en cada producto;
- ayuda a mantener orden en catálogo y luego facilita filtros.

### 2. Producto

Representa el objeto comercial principal de la farmacia.

**Atributos conceptuales sugeridos:**
- producto_id
- nombre_producto
- presentacion
- descripcion_breve
- precio_visible
- estado_producto
- es_publicable o bandera equivalente
- estado_disponibilidad
- fecha_creacion
- fecha_actualizacion

**Notas:**
- el producto es la entidad eje del subdominio farmacia;
- puede existir internamente sin estar publicado;
- estado interno y visibilidad pública no son exactamente lo mismo.

### 3. Reserva

Representa el apartado formal de unidades de un producto.

**Atributos conceptuales sugeridos:**
- reserva_id
- cantidad_reservada
- estado_reserva
- referencia_operativa
- fecha_creacion
- fecha_actualizacion

**Notas:**
- esta entidad pertenece más naturalmente a V1.1;
- no debe forzarse en la V1.0 si todavía no aporta valor suficiente;
- conceptualmente conviene dejarla prevista para que el modelo no nazca rígido.

## Relaciones conceptuales recomendadas

### Categoria 1:N Producto

Una categoría puede agrupar muchos productos.
Cada producto pertenece a una sola categoría, si el diseño decide modelarla aparte.

### Producto 1:N Reserva

Un producto puede tener muchas reservas a lo largo del tiempo.
Cada reserva pertenece a un solo producto.

## Decisiones conceptuales importantes

### 1. Producto es la entidad eje

Producto debe ocupar una posición central en el modelo.

### 2. Existencia interna no implica publicación

Un producto puede existir en la base sin ser visible en la capa pública.

### 3. Publicación y disponibilidad no son lo mismo

Un producto puede estar publicado pero agotado.
Un producto puede existir internamente pero no estar publicado.

### 4. Reserva es futura y opcional

El modelo conceptual debe permitirla, pero no obligarla desde la V1.0.

### 5. El modelo conceptual debe mantenerse limpio

No conviene meter desde ya proveedores, órdenes de compra, lotes, bodegas múltiples o entidades de farmacia demasiado empresariales si todavía no son necesarias para entender el núcleo.

## Lo que conviene dejar fuera por ahora

Para esta fase del modelo conceptual, conviene no meter todavía:

- usuario interno;
- rol;
- auditoría formal como entidad;
- múltiples catálogos de estados separados;
- proveedor;
- compra;
- lote;
- consultorio o cualquier dependencia externa.

## Sugerencia visual para el diagrama

Conviene ubicar visualmente las entidades así:

- `Producto` al centro;
- `Categoria` a un lado superior o izquierdo;
- `Reserva` a un lado inferior o derecho.

Esto transmite con claridad que:

- producto es el eje;
- categoría clasifica;
- reserva depende del producto.

## Nota corta sugerida para acompañar el diagrama

Se puede incluir una caja de observaciones con algo como:

- Modelo conceptual exclusivo del subdominio farmacia.
- Base de datos separada del consultorio.
- Producto puede existir sin estar publicado.
- Publicación y disponibilidad no son equivalentes.
- Reserva pertenece a evolución V1.1, no necesariamente a V1.0.
- Modelo enfocado en una farmacia pequeña y operativa, no en una cadena empresarial grande.

## Resultado esperado

Si el diagrama conceptual se construye a partir de este contexto, el resultado debería ser un modelo:

- centrado en farmacia;
- coherente con el dominio comercial y operativo;
- limpio para estudiar;
- apto para transformarse luego en 1FN, normalización y 3FN sin contradicciones gruesas.