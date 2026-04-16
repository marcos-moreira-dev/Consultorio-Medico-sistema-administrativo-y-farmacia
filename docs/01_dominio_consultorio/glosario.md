# Glosario

## Propósito

Fijar un vocabulario común para el subdominio del consultorio y reducir ambigüedades entre documentación, base de datos, backend, UI y conversación técnica.

## Términos principales

### Atención
Registro operativo de una consulta realizada a un paciente. En la V1 contiene información breve y suficiente para sostener el flujo del consultorio.

### Asistente
Persona que apoya tareas administrativas del consultorio, como registro de pacientes, agenda y apoyo operativo.

### Cita
Reserva de una franja horaria para una futura atención.

### Cobro
Registro administrativo del pago asociado a una atención.

### Consultorio
Contexto privado del sistema donde se gestionan pacientes, citas, atención e información sensible.

### Estado de cita
Condición operativa de una cita dentro del sistema. En la V1, como mínimo: programada, atendida o cancelada.

### Estado de cobro
Situación administrativa del pago. En la V1, como mínimo: pagado o pendiente.

### Indicaciones
Texto breve entregado o registrado tras la atención para orientar al paciente. No equivale a una historia clínica completa.

### Médico
Actor principal del proceso de atención. Usa el sistema para consultar agenda, registrar atenciones y, en algunos casos, completar el flujo de cobro.

### Motivo
Descripción breve asociada a una cita o atención. Debe ser útil operativamente y mantenerse contenida en la V1.

### Paciente
Persona que recibe atención en el consultorio y sobre la cual se registran datos personales básicos y eventos operativos.

### Recepcionista
Actor administrativo que puede coincidir o no con la figura del asistente, según el tamaño del negocio.

### Receta breve
Expresión simplificada de una prescripción o indicación, manejada de forma acotada dentro de la V1.

### Reprogramación
Cambio de fecha u hora de una cita existente, con necesidad de conservar coherencia y trazabilidad mínima.

### Superficie privada
Parte del sistema cuyo acceso debe limitarse a usuarios autorizados por tratar información sensible u operativamente interna.

### Usuario interno
Persona autorizada para operar el sistema del consultorio.

## Términos que conviene evitar o usar con cuidado

### Historia clínica
En este proyecto no debe usarse como sinónimo automático de atención. La V1 no modela una historia clínica extensa.

### Diagnóstico estructurado
No forma parte del foco principal de la V1 y no debe introducir complejidad clínica excesiva si no es estrictamente necesario.

### Cliente
Dentro del consultorio se prefiere el término paciente. El término cliente puede reservarse para farmacia o para una conversación comercial general.

## Resultado esperado

Este glosario debe servir como referencia mínima para escribir documentos, nombrar entidades, diseñar DTOs, construir tablas y redactar pantallas con vocabulario consistente.

