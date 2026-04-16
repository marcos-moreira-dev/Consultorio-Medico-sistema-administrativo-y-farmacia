# Criterios de aceptación

## Propósito

Definir condiciones observables que permitan considerar correcto el comportamiento esencial del subdominio de farmacia en la V1.

## Criterios para productos

### CAF-001 Creación mínima de producto
Dado que un usuario autorizado necesita registrar un producto,
cuando ingresa la información mínima requerida,
entonces el sistema debe permitir crear el producto para administración interna.

### CAF-002 Advertencia de posible duplicado
Dado que ya existe un producto similar,
cuando el usuario intenta registrar otro con coincidencias relevantes,
entonces el sistema debe advertir la posible duplicidad antes de confirmar.

### CAF-003 Actualización consistente
Dado que un producto existe,
cuando el usuario autorizado modifica datos válidos,
entonces el sistema debe guardar los cambios manteniendo coherencia operativa.

## Criterios para publicación y catálogo

### CAF-010 Publicación válida
Dado que un producto cumple condiciones mínimas y está activo,
cuando el usuario lo publica,
entonces el sistema debe hacerlo visible en el catálogo público.

### CAF-011 Bloqueo de publicación inválida
Dado que un producto está incompleto, inactivo o no publicable,
cuando el usuario intenta publicarlo,
entonces el sistema debe impedir la operación.

### CAF-012 Catálogo público filtrado
Dado que existe un catálogo visible,
cuando un visitante realiza una búsqueda,
entonces el sistema debe devolver únicamente productos publicados o visibles según la regla de exposición.

## Criterios para disponibilidad

### CAF-020 Ajuste de disponibilidad
Dado que un producto existe,
cuando el usuario autorizado cambia su disponibilidad o stock básico,
entonces el sistema debe reflejar el nuevo estado de forma consistente.

### CAF-021 Coherencia con agotado
Dado que un producto ya no puede ofrecerse,
cuando su disponibilidad cambia a agotado o equivalente,
entonces el sistema no debe seguir mostrándolo como claramente disponible.

### CAF-022 Producto inactivo fuera de oferta
Dado que un producto fue inactivado,
cuando se consulta el catálogo público,
entonces ese producto no debe aparecer como oferta vigente.

## Criterios de separación entre contextos

### CAF-030 Separación con consultorio
Dado que el sistema también incluye consultorio,
cuando se navega la farmacia pública o administrativa,
entonces no debe aparecer información privada del consultorio.

### CAF-031 Datos públicos controlados
Dado que la farmacia tiene superficie pública,
cuando se consulta un producto,
entonces solo debe mostrarse información autorizada para publicación.

## Criterios de usabilidad operativa

### CAF-040 Búsqueda simple y útil
Dado un visitante o personal operativo,
cuando busca un producto por nombre o coincidencia simple,
entonces debe obtener resultados comprensibles sin pasos innecesarios.

### CAF-041 Mantenimiento ágil
Dado un usuario interno autorizado,
cuando necesita crear, editar, publicar o inactivar productos,
entonces debe poder hacerlo sin recorridos burocráticos innecesarios.

## Criterios para evolución V1.1

### CAF-050 Reserva coherente
Dado que la funcionalidad de reservas fue habilitada en V1.1,
cuando el usuario intenta reservar unidades,
entonces el sistema debe validar coherencia con la disponibilidad reservable.

## Resultado esperado

Si estos criterios se cumplen, la farmacia ya puede considerarse funcionalmente sólida para catálogo, operación básica y evolución posterior dentro de la V1 amplia del proyecto.

