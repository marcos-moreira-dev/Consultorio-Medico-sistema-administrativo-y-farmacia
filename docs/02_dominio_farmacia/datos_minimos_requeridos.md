# Datos mínimos requeridos

## Propósito

Definir los datos mínimos que el sistema necesita para operar la farmacia de forma útil en la V1, diferenciando entre información obligatoria, recomendable, opcional y pública.

## Principio general

La farmacia debe modelarse con suficiente precisión para sostener catálogo, disponibilidad y administración básica, pero sin exigir de entrada una complejidad empresarial innecesaria.

## Producto

### Obligatorios
- nombre comercial o nombre principal;
- presentación básica;
- categoría básica;
- estado del producto;
- indicador de publicación o visibilidad;
- referencia de disponibilidad o stock básico.

### Recomendados
- descripción breve;
- precio visible si el modelo de negocio decide mostrarlo;
- código interno o SKU (Stock Keeping Unit) si se adopta.

### Opcionales
- marca;
- observación operativa breve;
- imagen del producto para la capa pública.

### Observaciones
- el producto puede existir internamente sin estar publicado;
- no todo dato interno debe exponerse en la capa pública;
- la identidad del producto debe ser suficiente para evitar duplicaciones evidentes.

## Disponibilidad

### Obligatorios
- referencia al producto;
- estado de disponibilidad o stock básico actualizado.

### Estados mínimos sugeridos
- disponible;
- agotado;
- no publicado.

### Observaciones
- según el diseño final, "no publicado" puede modelarse como estado o como combinación de estado y bandera de publicación;
- la disponibilidad pública puede simplificarse sin mostrar stock exacto.

## Publicación

### Obligatorios
- producto asociado;
- condición de visibilidad pública.

### Recomendados
- fecha de activación de publicación;
- responsable de cambio, si se quiere mayor trazabilidad.

## Reserva (V1.1)

### Obligatorios
- producto asociado;
- cantidad reservada;
- referencia operativa mínima;
- estado de la reserva.

### Observaciones
- este bloque no es obligatorio en V1.0;
- se deja preparado como evolución natural de V1.1.

## Usuario interno de farmacia

### Obligatorios
- identificador;
- usuario o equivalente;
- rol;
- activo.

### Observaciones
- aunque pertenezca más al bloque de seguridad, el subdominio reconoce la existencia de usuarios internos autorizados para operar catálogo y disponibilidad.

## Datos públicos sugeridos

La superficie pública puede mostrar:

- nombre del producto;
- presentación;
- categoría;
- descripción breve;
- disponibilidad controlada;
- precio visible, si se decide mostrarlo;
- imagen, si existe.

## Datos que no deben forzarse en la V1

- múltiples bodegas obligatorias;
- trazabilidad farmacéutica compleja por lote en la V1.0;
- proveedor detallado como núcleo obligatorio;
- reglas comerciales avanzadas de descuentos y convenios complejos;
- automatización logística pesada.

## Resultado esperado

Con estos datos mínimos, el sistema debe poder sostener un catálogo público útil, una administración básica de farmacia y una evolución razonable hacia reservas y mayor madurez operativa sin rehacer el modelo desde cero.

