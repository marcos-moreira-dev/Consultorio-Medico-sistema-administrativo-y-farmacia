# Criterios de aceptación

## Propósito

Definir condiciones observables que permitan considerar cumplidos los comportamientos esenciales del consultorio en la V1.

## Criterios para pacientes

### CA-001 Registro mínimo de paciente
Dado que una persona llega por primera vez,
cuando el usuario registra nombres y apellidos con al menos un identificador operativo útil,
entonces el sistema debe permitir crear el paciente sin exigir todos los campos opcionales.

### CA-002 Advertencia de posible duplicado
Dado que ya existe un paciente parecido,
cuando el usuario intenta registrar uno nuevo con datos coincidentes,
entonces el sistema debe advertir la posible duplicidad antes de confirmar.

## Criterios para citas

### CA-010 Creación de cita válida
Dado un paciente existente,
cuando el usuario agenda una cita en un horario disponible,
entonces la cita debe quedar registrada con estado programada.

### CA-011 Prevención de solapamiento
Dado un horario ya ocupado para el mismo profesional,
cuando el usuario intenta agendar otra cita incompatible,
entonces el sistema debe rechazar la operación o impedir su confirmación.

### CA-012 Cancelación trazable
Dado que una cita existe,
cuando el usuario la cancela,
entonces la cita no debe desaparecer, sino quedar con estado cancelada.

### CA-013 Reprogramación coherente
Dado que una cita existe,
cuando el usuario la mueve a otro horario válido,
entonces el nuevo horario debe quedar reflejado sin perder claridad administrativa.

## Criterios para atención

### CA-020 Atención con cita
Dado que un paciente tiene una cita programada,
cuando el médico registra la atención asociada,
entonces la atención debe vincularse al paciente y la cita debe pasar a un estado coherente.

### CA-021 Atención sin cita
Dado que un paciente llega sin agendar,
cuando el médico registra una atención simple,
entonces el sistema debe permitirla sin obligar a crear una cita previa.

### CA-022 Registro simple y usable
Dado que el consultorio opera de forma ágil,
cuando el médico registra una atención,
entonces el sistema debe permitir guardar notas e indicaciones breves sin exigir formularios clínicos extensos.

## Criterios para cobro

### CA-030 Cobro asociado a atención
Dado que existe una atención registrada,
cuando el usuario registra el cobro,
entonces el cobro debe quedar asociado a esa atención.

### CA-031 Bloqueo de cobro inválido
Dado que no existe atención,
cuando el usuario intenta registrar un cobro aislado,
entonces el sistema debe rechazar la operación.

### CA-032 Estado de cobro explícito
Dado un cobro registrado,
cuando el usuario define si fue pagado o quedó pendiente,
entonces el sistema debe conservar ese estado de forma visible y consistente.

## Criterios de privacidad

### CA-040 Datos del consultorio no expuestos públicamente
Dado que el consultorio es una superficie privada,
cuando se navega la parte pública del sistema,
entonces no debe aparecer información de pacientes, citas, atenciones ni cobros.

### CA-041 Acceso restringido
Dado un usuario sin permisos suficientes,
cuando intenta acceder a información privada del consultorio,
entonces el sistema debe impedirlo.

## Criterios de usabilidad operativa

### CA-050 Flujo directo para tareas frecuentes
Dado un día normal de operación,
cuando el usuario necesita buscar paciente, agendar, atender o cobrar,
entonces debe poder completar esas tareas sin recorridos innecesarios.

### CA-051 Lenguaje claro
Dado el perfil no técnico del usuario,
cuando interactúa con formularios, estados o mensajes,
entonces el sistema debe usar etiquetas comprensibles y no lenguaje excesivamente técnico.

## Resultado esperado

Si estos criterios se cumplen, la V1 del consultorio ya puede considerarse funcionalmente sólida para demostración, estudio y evolución posterior.

