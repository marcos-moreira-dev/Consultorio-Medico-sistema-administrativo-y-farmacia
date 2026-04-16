# Reglas de persistencia

## Propósito

Definir las reglas prácticas que determinan qué se persiste en la base de farmacia, en qué entidad debe persistirse y bajo qué condiciones generales.

## Principio general

Persistir no es guardar todo. Persistir bien significa conservar los hechos correctos, en la entidad correcta y con el nivel de detalle adecuado para la V1.

## Reglas principales

### RP-001. El producto se persiste como entidad propia
No se debe modelar el producto como un bloque difuso repartido entre múltiples tablas sin responsabilidad clara.

### RP-002. La existencia interna del producto no implica publicación
Un producto puede persistirse internamente sin quedar visible en la capa pública.

### RP-003. La disponibilidad debe persistirse de forma controlada
La farmacia debe poder reflejar si un producto está disponible, agotado o fuera de publicación sin depender solo del frontend.

### RP-004. El estado del producto debe persistirse de forma controlada
Activo e inactivo no deben quedar como texto arbitrario ni como semántica implícita.

### RP-005. La base no debe guardar estructuras del consultorio
Todo dato de paciente, cita, atención o cobro pertenece a otra base de datos.

## Reglas sobre minimización

### RP-010. No se persiste complejidad empresarial innecesaria en la V1
No conviene inflar el modelo con bodegas múltiples, proveedores complejos o trazabilidad logística empresarial si aún no aportan valor real.

### RP-011. Solo se persisten datos útiles para operación y estudio
No conviene llenar la base con campos sin propósito funcional claro.

## Reglas sobre trazabilidad

### RP-020. Los cambios operativos importantes deben dejar rastro suficiente
Especialmente alta de producto, actualización relevante, cambio de publicación, cambio de disponibilidad e inactivación.

### RP-021. La trazabilidad no debe depender solo de logs de aplicación
La base debe poder sostener, al menos parcialmente, un nivel útil de rastro estructurado cuando haga sentido.

## Reglas sobre evolución

### RP-030. El diseño debe poder pasar de 1FN a 3FN sin contradecir el dominio
Eso implica que el esquema inicial no debe inventar mezclas que luego rompan el aprendizaje de normalización.

### RP-031. Las migraciones futuras deben respetar responsabilidad por subdominio
Los cambios de farmacia pertenecen a `database-farmacia` y no deben asumir evolución conjunta con consultorio.

### RP-032. La reserva es una expansión razonable, no una obligación inicial
El modelo debe poder absorberla sin rehacerse, pero no debe forzarla si la V1.0 aún no la necesita.

## Resultado esperado

Estas reglas deben servir como criterio estable al definir tablas, columnas, relaciones, seeds y migraciones del componente, evitando persistencia arbitraria o mezclas confusas de conceptos.

