# Casos de uso

## Propósito

Traducir la operación de farmacia en interacciones ordenadas y reutilizables para que backend, base de datos, UI pública y administración interna partan de una misma visión funcional.

## UCF-001 Crear producto

### Objetivo
Registrar un producto nuevo en el sistema con la información mínima necesaria para su gestión interna y posible publicación.

### Actores
- administrador de farmacia;
- personal operativo autorizado.

### Precondiciones
- el producto no existe ya con suficiente claridad.

### Flujo principal
1. El usuario abre el formulario de producto.
2. Ingresa identidad comercial mínima y datos operativos básicos.
3. El sistema valida campos requeridos.
4. El sistema advierte posible duplicidad si detecta coincidencias.
5. El usuario confirma.
6. El sistema crea el producto en estado coherente.

### Alternos
- duplicidad probable: el usuario revisa antes de confirmar;
- producto incompleto: el sistema impide su publicación hasta completar lo mínimo necesario.

### Resultado
Producto creado y disponible para administración interna.

## UCF-002 Actualizar producto

### Objetivo
Modificar datos de un producto existente sin perder consistencia operativa.

### Actores
- administrador de farmacia;
- personal operativo autorizado.

### Flujo principal
1. El usuario busca el producto.
2. Abre su ficha de edición.
3. Actualiza campos permitidos.
4. El sistema valida consistencia mínima.
5. El usuario confirma.
6. El sistema guarda los cambios.

### Resultado
Producto actualizado con trazabilidad suficiente.

## UCF-003 Publicar producto en catálogo

### Objetivo
Hacer visible un producto en la superficie pública o semipública de farmacia.

### Actores
- administrador de farmacia.

### Precondiciones
- el producto existe;
- el producto cumple condiciones mínimas para publicación.

### Flujo principal
1. El usuario revisa la ficha del producto.
2. Activa la opción de publicación.
3. El sistema verifica completitud mínima, estado activo y coherencia básica.
4. El sistema confirma la publicación.
5. El producto pasa a formar parte del catálogo visible.

### Alternos
- producto incompleto o inactivo: el sistema rechaza la publicación.

### Resultado
Producto visible en la capa pública.

## UCF-004 Consultar catálogo público

### Objetivo
Permitir que un visitante explore productos visibles de forma simple.

### Actores
- visitante o cliente.

### Flujo principal
1. El visitante abre el catálogo.
2. Busca por nombre, coincidencia simple o categoría.
3. El sistema devuelve solo productos visibles.
4. El visitante abre el detalle de un producto.
5. El sistema muestra información pública controlada.

### Resultado
Consulta pública del catálogo sin exponer datos internos.

## UCF-005 Ajustar disponibilidad o stock básico

### Objetivo
Reflejar la situación operativa real de un producto respecto a existencia o publicación.

### Actores
- personal operativo autorizado;
- administrador de farmacia.

### Flujo principal
1. El usuario busca el producto.
2. Modifica stock básico o estado de disponibilidad.
3. El sistema valida consistencia.
4. El usuario confirma.
5. El sistema actualiza el producto y su disponibilidad visible cuando corresponda.

### Alternos
- el producto queda agotado: el sistema ajusta su presentación pública de forma coherente;
- el producto deja de publicarse: el catálogo deja de mostrarlo.

### Resultado
Disponibilidad y estado alineados con la operación real.

## UCF-006 Inactivar producto

### Objetivo
Retirar un producto de la oferta vigente sin borrar su existencia histórica.

### Actores
- administrador de farmacia.

### Flujo principal
1. El usuario selecciona el producto.
2. Ejecuta la acción de inactivación.
3. El sistema solicita confirmación.
4. El producto queda inactivo y deja de mostrarse como vigente.

### Resultado
Producto conservado en el sistema, pero fuera de la oferta actual.

## UCF-007 Reservar producto (V1.1)

### Objetivo
Apartar unidades de un producto cuando la evolución del sistema incorpore reservas.

### Actores
- personal operativo autorizado.

### Precondiciones
- el producto existe;
- hay reglas de reserva habilitadas en V1.1.

### Flujo principal
1. El usuario busca el producto.
2. Indica cantidad a reservar y referencia operativa.
3. El sistema valida disponibilidad reservable.
4. El usuario confirma.
5. El sistema registra la reserva y ajusta coherencia con la disponibilidad publicada.

### Resultado
Reserva formalizada dentro del comportamiento evolutivo V1.1.

## Resultado esperado

Los casos de uso de farmacia deben cubrir el núcleo útil de catálogo y disponibilidad en V1.0, dejando una transición natural hacia reservas y mayor madurez operativa en V1.1.

