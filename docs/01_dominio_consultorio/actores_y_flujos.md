# Actores y flujos

## Objetivo

Describir quiénes participan en la operación del consultorio y cómo se encadenan las acciones principales del día a día en la V1 del sistema.

## Actores principales

### Médico
Es el actor central del proceso de atención. Necesita revisar agenda, identificar al paciente, registrar una atención breve, dejar indicaciones y cerrar o acompañar el flujo hacia el cobro.

Necesidades principales:

- ver rápidamente el día de trabajo;
- ubicar pacientes sin fricción;
- registrar atención simple;
- dejar indicaciones breves;
- no navegar pantallas innecesarias.

### Asistente o recepcionista
Actor operativo que ayuda a sostener la agenda y el registro administrativo. Puede buscar pacientes, crear nuevos registros, agendar citas, confirmar llegadas y apoyar el flujo previo o posterior a la atención.

Necesidades principales:

- localizar pacientes con rapidez;
- registrar datos mínimos;
- programar y reorganizar citas;
- marcar estados operativos de la cita;
- apoyar el cobro si el consultorio lo requiere.

### Paciente
No se modela como usuario directo del sistema interno, pero sí como sujeto central del flujo. Aporta datos, llega con o sin cita, recibe atención y eventualmente indicaciones o comprobante.

### Responsable de cobro
En consultorios pequeños puede ser el mismo médico, la asistencia o una persona de recepción. Desde el punto de vista del sistema, es el actor que registra el cobro asociado a una atención.

## Actores secundarios o contextuales

### Acompañante
No necesariamente interactúa con el sistema, pero puede participar en entrega de información o recepción de indicaciones.

### Farmacia asociada
No forma parte del flujo interno del consultorio, pero puede recibir información indirecta a futuro. En la V1 se mantiene separada del dominio privado del consultorio.

## Flujo principal del consultorio

### Flujo típico del día

1. Se abre la agenda del día.
2. Se revisan citas programadas.
3. Cuando llega una persona, se busca al paciente o se lo registra si aún no existe.
4. Se confirma la cita o se genera atención sin cita si llegó de forma espontánea.
5. El médico registra la atención simple.
6. Se dejan indicaciones breves o receta breve si aplica.
7. Se registra el cobro correspondiente.
8. El flujo queda cerrado con trazabilidad mínima.

## Flujos operativos clave

### Flujo 1. Registrar paciente
Se usa cuando la persona no existe en el sistema o cuando hace falta completar datos mínimos para poder continuar con agenda o atención.

### Flujo 2. Agendar cita
Se usa para reservar una franja horaria futura. Debe respetar disponibilidad razonable y evitar solapamientos para el mismo profesional.

### Flujo 3. Confirmar llegada o atender sin cita
Permite continuar con la atención de un paciente que ya estaba agendado o registrar una atención espontánea cuando la operación lo permite.

### Flujo 4. Registrar atención simple
Es el corazón del subdominio. Debe permitir capturar notas breves operativas, indicaciones y vínculo opcional con una cita.

### Flujo 5. Registrar cobro
Cierra administrativamente la atención. El cobro depende de que exista una atención registrada.

## Flujos alternos frecuentes

### Paciente sin cédula
El sistema debe permitir continuar con datos mínimos mientras quede claramente identificado el registro para futura consolidación.

### Paciente sin cita
La atención puede existir sin una cita previa. El sistema debe soportar este caso sin forzar una agenda artificial.

### Cita cancelada
Una cita puede cancelarse sin generar atención. El flujo se cierra a nivel de agenda, no de atención.

### Reprogramación
Cuando el horario cambia, debe mantenerse claridad sobre el nuevo turno y el estado final de la cita original.

### Cobro pendiente
El sistema debe permitir registrar que la atención existió aunque el cobro quede pendiente.

## Límites importantes del flujo

- no se debe forzar un flujo clínico complejo;
- no se debe exponer información del consultorio a la superficie pública;
- no se debe asumir que todos los pacientes llegan con documentación completa;
- no se debe modelar la operación como si fuera un hospital o una clínica grande.

## Resultado esperado

Los flujos del consultorio deben sentirse directos, creíbles y compatibles con una operación pequeña, donde registrar, atender y cobrar importa más que sostener burocracia innecesaria.

