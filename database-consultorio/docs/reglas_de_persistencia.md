# Reglas de persistencia

## Propósito

Definir las reglas prácticas que determinan qué se persiste en la base del consultorio multidoctor, en qué entidad debe persistirse y bajo qué condiciones generales.

## Principio general

Persistir no es guardar todo. Persistir bien significa conservar los hechos correctos, en la entidad correcta y con el nivel de detalle adecuado para la V1.

## Reglas principales

### RP-001. El paciente se persiste como entidad propia
No se debe modelar al paciente como un campo embebido dentro de cita o atención.

### RP-002. El profesional se persiste como entidad propia
No debe quedar implícito ni duplicado de forma textual en los eventos principales.

### RP-003. El usuario interno se persiste como entidad propia
La autenticación y autorización no deben depender de datos informales mezclados con profesional, paciente o cobro.

### RP-004. La cita se persiste aunque no llegue a concretarse
La cancelación o no realización de una cita no implica borrar su existencia operativa.

### RP-005. La atención se persiste como hecho independiente
Una atención representa una consulta realizada y debe existir como entidad propia, incluso si no hubo cita previa.

### RP-006. El cobro se persiste solo si nace de una atención
No se debe persistir un cobro aislado colgado directamente de una cita.

### RP-007. La atención puede referenciar opcionalmente una cita
Esto permite representar tanto atenciones agendadas como atenciones espontáneas.

### RP-008. Cita y atención deben persistir explícitamente el profesional responsable
El sistema es multidoctor, así que el profesional no puede quedar implícito.

### RP-009. Los estados operativos deben persistirse de forma controlada
Estados clave del dominio, como estado de cita y estado de cobro, no deben quedar a puro texto libre ambiguo.

### RP-010. La base no debe guardar estructuras de farmacia
Todo dato de catálogo, producto, stock o reserva pertenece a otra base de datos.

## Reglas sobre minimización

### RP-020. No se persiste historia clínica extensa en la V1
La base del consultorio debe guardar atención simple y suficiente, no una estructura hospitalaria compleja.

### RP-021. Solo se persisten datos útiles para operación y estudio
No conviene llenar la base con campos sin propósito funcional claro.

## Reglas sobre trazabilidad

### RP-030. Los cambios operativos importantes deben dejar rastro suficiente
Especialmente creación o actualización de paciente, cambios de cita, registro de atención y cobro.

### RP-031. Debe poder saberse quién registró ciertos hechos administrativos sensibles
En particular, conviene poder relacionar cobros con usuario interno registrador cuando el diseño final lo permita.

## Reglas sobre evolución

### RP-040. El diseño debe poder pasar de 1FN a 3FN sin contradecir el dominio
Eso implica que el esquema inicial no debe inventar mezclas que luego rompan el aprendizaje de normalización.

### RP-041. Las migraciones futuras deben respetar responsabilidad por subdominio
Los cambios de consultorio pertenecen a `database-consultorio` y no deben asumir evolución conjunta con farmacia.

### RP-042. La reprogramación formal de cita pertenece a V1.1
La base debe quedar lista para absorberla sin rehacer el diseño principal.

## Resultado esperado

Estas reglas deben servir como criterio estable al definir tablas, columnas, relaciones, seeds y migraciones del componente, evitando persistencia arbitraria o mezclas confusas de conceptos, especialmente ahora que el sistema deja de ser mono-doctor implícito.

