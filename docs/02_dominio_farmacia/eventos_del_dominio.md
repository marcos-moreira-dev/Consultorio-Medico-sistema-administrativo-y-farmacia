# Eventos del dominio

## Propósito

Identificar hechos relevantes del subdominio de farmacia que expresan cambios significativos en la operación y que luego pueden influir en trazabilidad, auditoría, backend, publicación o evolución del modelo.

## Criterio

Un evento de dominio representa algo que ya ocurrió y que tiene significado para el negocio. No es simplemente un detalle técnico de interfaz.

## Eventos principales

### EDF-001 Producto creado
Se produce cuando un producto nuevo queda registrado en el sistema.

### EDF-002 Producto actualizado
Se produce cuando cambian datos relevantes del producto.

### EDF-003 Posible duplicado de producto detectado
Se produce cuando el sistema encuentra coincidencias que sugieren revisar antes de confirmar un alta.

### EDF-010 Producto publicado
Se produce cuando un producto pasa a formar parte de la superficie pública o visible.

### EDF-011 Producto despublicado
Se produce cuando un producto deja de estar visible sin ser eliminado del sistema.

### EDF-012 Producto inactivado
Se produce cuando un producto deja de formar parte de la oferta vigente.

### EDF-020 Disponibilidad actualizada
Se produce cuando cambia la situación operativa del producto respecto a ofrecimiento o stock básico.

### EDF-021 Producto marcado como agotado
Se produce cuando el producto ya no puede sostenerse como disponible.

### EDF-022 Producto restituido a disponible
Se produce cuando el producto vuelve a poder ofrecerse.

### EDF-030 Catálogo público consultado
Se produce cuando una persona externa realiza búsqueda o navegación del catálogo, si se decide registrar este hecho con fines analíticos o operativos.

### EDF-040 Reserva creada (V1.1)
Se produce cuando una cantidad del producto queda apartada formalmente.

### EDF-041 Reserva cancelada (V1.1)
Se produce cuando una reserva deja de ser válida.

### EDF-042 Reserva concretada (V1.1)
Se produce cuando la reserva termina en una salida operativa o cierre del proceso definido.

## Uso esperado de estos eventos

Estos eventos sirven para:

- estructurar trazabilidad y auditoría;
- alinear backend y dominio;
- diseñar reportes operativos;
- pensar estados coherentes de catálogo y disponibilidad;
- preparar una evolución ordenada hacia reservas en V1.1.

## Eventos especialmente relevantes para V1.1

### Reserva creada
Es el evento más importante de la evolución porque obliga a pensar en cómo se relacionan disponibilidad publicada, stock básico y unidades apartadas.

### Despublicación e inactivación
Son relevantes porque ayudan a distinguir entre sacar un producto del catálogo visible y retirarlo de la operación vigente.

## Eventos que no deben exagerarse en V1

No conviene registrar demasiados eventos finos sin valor operativo. Esta etapa debe centrarse en hechos que realmente cambian estado o afectan al negocio.

## Resultado esperado

El catálogo de eventos de farmacia debe ayudar a pensar el módulo como una operación comercial viva y trazable, evitando tanto la pobreza conceptual como la sobreingeniería.

