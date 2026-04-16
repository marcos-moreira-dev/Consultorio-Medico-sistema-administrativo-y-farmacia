# Trazabilidad y auditoría

## Propósito

Distinguir entre trazabilidad operativa y auditoría formal dentro del proyecto, definiendo qué debe registrarse para seguimiento útil del negocio y qué hechos merecen un tratamiento más cercano a auditoría.

## Principio general

Trazabilidad y auditoría se relacionan, pero no son idénticas.

- la trazabilidad ayuda a reconstruir qué pasó;
- la auditoría ayuda a dejar constancia más formal de acciones relevantes o sensibles.

En esta V1 no se busca una plataforma de compliance compleja, pero sí una base seria y profesional.

## Trazabilidad operativa

### Definición
Capacidad del sistema para seguir el recorrido de hechos importantes del negocio y la operación técnica.

### Qué debe cubrir
- creación y cambios de pacientes;
- citas programadas, canceladas o reprogramadas;
- atención registrada;
- cobro registrado y cambio de estado;
- productos creados, publicados, despublicados, inactivados o agotados;
- errores y accesos denegados relevantes.

### Propósito
- entender el comportamiento del sistema;
- apoyar soporte y depuración;
- revisar evolución de un caso;
- facilitar estudio del proyecto.

## Auditoría útil en la V1

### Definición
Registro más deliberado de ciertas acciones sensibles, administrativas o de cambio de estado con valor especial para control interno.

### Qué hechos merecen auditoría prioritaria
- acceso denegado relevante;
- publicación y despublicación de productos;
- inactivación de productos;
- cancelación y reprogramación de citas;
- cambios importantes en cobro;
- cambios relevantes sobre datos de paciente;
- migraciones aplicadas.

## Diferencia práctica entre ambos conceptos

### Ejemplo de trazabilidad
“Se registró una atención a las 10:42 con correlation_id X”.

### Ejemplo de auditoría
“El usuario Y reprogramó la cita Z a las 10:45 desde el componente consultorio”.

La auditoría necesita más intención de control. La trazabilidad puede ser más amplia y ligera.

## Datos sugeridos para registros de auditoría

Cuando aplique, un registro de auditoría debería capturar:

- fecha y hora;
- usuario o actor responsable;
- acción realizada;
- entidad afectada;
- identificador de la entidad;
- resultado principal;
- correlation_id si existe.

## Reglas de diseño derivadas

### TRA-001. No todo evento debe volverse auditoría formal
Solo los hechos con valor de control o sensibilidad especial merecen ese nivel.

### TRA-002. La auditoría no debe convertirse en fuga de datos sensibles
Debe registrar el hecho de la acción sin volcar contenido innecesario.

### TRA-003. La trazabilidad debe ser suficiente para reconstruir operaciones normales
No se debe depender solo de auditoría para entender un flujo.

### TRA-004. La V1 debe priorizar utilidad antes que completitud regulatoria
Se busca una base profesional, no un sistema corporativo de compliance pesada.

## Relación con V1.1

La V1.1 probablemente incrementará la necesidad de trazabilidad y auditoría en dos áreas:

- reprogramación y cambios de estado de agenda;
- reservas y cambios de disponibilidad en farmacia.

Por eso conviene dejar este marco listo desde la V1.0.

## Resultado esperado

El sistema debe distinguir bien entre registrar hechos para entender la operación y registrar acciones para control más formal, manteniendo una postura seria, útil y proporcional al tamaño y propósito del proyecto.