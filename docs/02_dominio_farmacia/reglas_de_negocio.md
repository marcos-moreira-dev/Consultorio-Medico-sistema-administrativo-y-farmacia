# Reglas de negocio

## Propósito

Formalizar las reglas operativas que gobiernan el subdominio de farmacia para que la base de datos, el backend, la capa pública y la administración interna se mantengan consistentes.

## Reglas generales

### RNF-001. La farmacia expone solo información controlada
La superficie pública de farmacia solo puede mostrar datos aprobados para publicación, como nombre, presentación, categoría, precio visible si se decide, detalle básico y disponibilidad controlada.

### RNF-002. La farmacia y el consultorio son contextos separados
No debe exponerse información del consultorio a través del catálogo ni mezclarse datos clínicos con el dominio comercial de productos.

### RNF-003. La V1 prioriza catálogo, disponibilidad y operación básica
La primera etapa del subdominio no debe sobrediseñarse como un sistema de inventario empresarial completo.

## Reglas sobre productos

### RNF-010. Todo producto debe tener identidad comercial mínima
Cada producto debe contar, como mínimo, con nombre, presentación, categoría básica, estado y referencia de disponibilidad.

### RNF-011. Un producto puede existir sin estar publicado
La existencia interna del producto no obliga a mostrarlo en la superficie pública.

### RNF-012. Un producto inactivo no debe aparecer como vigente
Si un producto queda inactivo, no debe seguir presentándose como oferta actual al público.

### RNF-013. Debe evitarse la duplicación evidente de productos
Si ya existe un producto con identidad suficientemente similar, el sistema debe advertir la posible duplicidad antes de crear otro.

## Reglas sobre disponibilidad y stock

### RNF-020. La disponibilidad publicada debe ser coherente con la situación operativa
No se debe mostrar como disponible un producto que operativamente ya no puede sostenerse.

### RNF-021. El stock básico debe poder ajustarse
La administración interna debe permitir reflejar aumentos, reducciones o agotamiento sin procesos innecesariamente complejos.

### RNF-022. La disponibilidad visible no equivale siempre al stock exacto
El sistema puede publicar disponibilidad en forma simplificada sin exponer necesariamente el conteo exacto al público.

### RNF-023. El agotado es un estado operativo relevante
Cuando un producto no puede ofrecerse, el sistema debe reflejarlo de forma consistente en la capa interna y en la pública cuando corresponda.

## Reglas sobre publicación

### RNF-030. Un producto debe cumplir condiciones mínimas para publicarse
No debe mostrarse públicamente un producto incompleto, inactivo o marcado como no publicable.

### RNF-031. La búsqueda pública debe operar solo sobre el catálogo visible
Los productos internos no publicados no deben contaminar resultados públicos.

### RNF-032. La información pública debe ser sobria y entendible
La capa pública debe privilegiar claridad y disponibilidad operativa, no tecnicismo excesivo.

## Reglas sobre reservas y evolución

### RNF-040. La V1.0 no exige reservas formales
La V1 puede operar sin apartado o reserva estructurada.

### RNF-041. La V1.1 podrá incorporar reservas como evolución razonable
Cuando se agregue este comportamiento, deberá existir coherencia entre stock, reserva y disponibilidad publicada.

## Reglas de consistencia y trazabilidad

### RNF-050. Los cambios relevantes de producto deben dejar rastro suficiente
Alta, actualización, inactivación y cambios significativos de disponibilidad son hechos operativos que deben poder trazarse.

### RNF-051. Los estados deben representar hechos claros
No se deben usar estados ambiguos para producto o disponibilidad.

### RNF-052. La operación debe tolerar rapidez y simplicidad
El sistema no debe exigir procedimientos complejos para tareas básicas de mantenimiento del catálogo.

## Excepciones frecuentes contempladas

- producto cargado internamente pero no publicado;
- producto agotado;
- producto inactivo;
- cambios rápidos de disponibilidad;
- duplicidad probable de producto;
- futura necesidad de reservar unidades.

## Resultado esperado

Las reglas de negocio de farmacia deben servir como base directa para restricciones relacionales, validaciones de backend, contratos de publicación y comportamiento de la interfaz pública e interna.

