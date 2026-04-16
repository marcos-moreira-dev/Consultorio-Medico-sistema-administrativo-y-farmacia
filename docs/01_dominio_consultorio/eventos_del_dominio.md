# Eventos del dominio

## Propósito

Identificar hechos relevantes del subdominio del consultorio que expresan cambios significativos en la operación y que luego pueden influir en trazabilidad, auditoría, backend, notificaciones internas o evolución del modelo.

## Criterio

Un evento de dominio representa algo que ya ocurrió y que tiene significado para el negocio. No es lo mismo que un clic de interfaz ni que una simple acción técnica.

## Eventos principales

### ED-001 Paciente registrado
Se produce cuando se crea por primera vez un paciente en el sistema.

### ED-002 Paciente actualizado
Se produce cuando se modifican datos relevantes del paciente.

### ED-003 Posible duplicado detectado
Se produce cuando el sistema encuentra coincidencias que sugieren revisar antes de crear un nuevo paciente.

### ED-010 Cita programada
Se produce cuando una cita queda registrada correctamente en la agenda.

### ED-011 Cita cancelada
Se produce cuando una cita deja de estar vigente pero debe mantenerse en el historial operativo.

### ED-012 Cita reprogramada
Se produce cuando una cita cambia de horario conservando trazabilidad mínima.

### ED-013 Llegada de paciente confirmada
Se produce cuando el paciente se presenta y la operación avanza desde la agenda hacia la atención.

### ED-020 Atención iniciada
Se produce cuando el profesional abre formalmente el proceso de atención del paciente.

### ED-021 Atención registrada
Se produce cuando la atención simple queda guardada con éxito.

### ED-022 Indicaciones emitidas
Se produce cuando se registran indicaciones o receta breve asociadas a la atención.

### ED-030 Cobro registrado
Se produce cuando se crea un cobro vinculado a una atención.

### ED-031 Cobro marcado como pagado
Se produce cuando el estado del cobro queda efectivamente en pagado.

### ED-032 Cobro marcado como pendiente
Se produce cuando la atención queda cerrada operativamente pero el pago no se completó.

## Uso esperado de estos eventos

Estos eventos sirven para:

- diseñar trazabilidad y auditoría;
- pensar reglas de consistencia entre estados;
- estructurar pruebas;
- alinear backend y dominio;
- preparar futuras notificaciones o reportes operativos.

## Eventos especialmente relevantes para V1.1

Hay dos líneas que conviene dejar preparadas desde ya:

### Reprogramación de cita
Es un evento importante porque obliga a pensar mejor en trazabilidad de agenda y evolución del modelo.

### Cambio de estado de cobro
Es importante para reportes, operación diaria y consistencia administrativa.

## Eventos que no deben exagerarse en V1

No conviene llenar el proyecto de eventos demasiado finos o artificiales. En esta etapa deben priorizarse solo hechos con valor operativo claro.

## Resultado esperado

El catálogo de eventos del consultorio debe ayudar a pensar el sistema como una operación viva y trazable, sin convertir la V1 en una arquitectura excesivamente compleja.

