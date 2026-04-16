# Casos de uso

## Propósito

Traducir la operación del consultorio en interacciones ordenadas y reutilizables, de forma que backend, base de datos, desktop y pruebas puedan apoyarse en una misma visión funcional.

## UC-001 Registrar paciente

### Objetivo
Crear un registro inicial de paciente con datos mínimos suficientes para continuar con agenda o atención.

### Actores
- asistente o recepcionista;
- médico, si opera directamente el sistema.

### Precondiciones
- el paciente no existe aún o no fue encontrado con certeza.

### Flujo principal
1. El usuario abre el formulario de paciente.
2. Ingresa datos mínimos disponibles.
3. El sistema valida campos obligatorios mínimos.
4. El sistema advierte si detecta posible duplicidad.
5. El usuario confirma el registro.
6. El sistema crea el paciente y lo deja disponible para agenda o atención.

### Alternos
- falta cédula: se registra igualmente;
- se detecta duplicidad probable: el usuario revisa antes de confirmar.

### Resultado
Paciente creado de forma utilizable dentro del sistema.

## UC-002 Agendar cita

### Objetivo
Reservar una franja horaria para una futura atención.

### Actores
- asistente o recepcionista;
- médico, si gestiona su agenda directamente.

### Precondiciones
- existe un paciente registrado o se lo crea en el momento.

### Flujo principal
1. El usuario busca o selecciona paciente.
2. Elige fecha y hora.
3. Ingresa motivo breve si aplica.
4. El sistema valida solapamientos del profesional.
5. El usuario confirma.
6. El sistema registra la cita como programada.

### Alternos
- el horario ya está ocupado: se rechaza o se sugiere otro;
- el paciente no existe: se ejecuta antes el registro de paciente.

### Resultado
Cita creada y visible en la agenda.

## UC-003 Registrar atención

### Objetivo
Dejar constancia operativa de una consulta realizada.

### Actores
- médico.

### Precondiciones
- existe paciente;
- puede existir o no una cita previa.

### Flujo principal
1. El médico abre la atención desde la agenda o desde búsqueda de paciente.
2. El sistema muestra contexto suficiente del paciente.
3. El médico registra notas breves e indicaciones.
4. El sistema guarda la atención con fecha y hora.
5. Si la atención provino de una cita, la cita se actualiza a estado coherente.

### Alternos
- atención sin cita: se registra igualmente;
- atención con datos mínimos: se guarda sin exigir historia clínica compleja.

### Resultado
Atención simple registrada y asociada al paciente.

## UC-004 Registrar cobro

### Objetivo
Cerrar administrativamente la atención mediante un cobro.

### Actores
- asistente;
- recepcionista;
- médico, si aplica.

### Precondiciones
- existe una atención registrada.

### Flujo principal
1. El usuario abre el módulo de cobro desde una atención existente.
2. Ingresa monto, método y estado.
3. El sistema valida consistencia mínima.
4. El usuario confirma.
5. El sistema registra el cobro asociado a la atención.

### Alternos
- el pago no se concreta: se registra como pendiente;
- se intenta cobrar sin atención: el sistema rechaza la operación.

### Resultado
Cobro registrado de forma trazable.

## UC-005 Consultar paciente

### Objetivo
Encontrar rápidamente a un paciente para revisar su información operativa básica o continuar un flujo.

### Actores
- asistente;
- médico.

### Flujo principal
1. El usuario busca por nombre, apellido, teléfono o cédula.
2. El sistema devuelve coincidencias razonables.
3. El usuario selecciona al paciente.
4. El sistema permite continuar hacia cita, atención o revisión básica.

### Resultado
Paciente localizado para continuar el flujo del consultorio.

## UC-006 Cancelar cita

### Objetivo
Cerrar administrativamente una cita que no se concretará.

### Actores
- asistente;
- recepcionista;
- médico.

### Precondiciones
- existe una cita vigente.

### Flujo principal
1. El usuario abre la cita.
2. Selecciona la acción de cancelación.
3. El sistema solicita confirmación.
4. La cita cambia a estado cancelada.

### Resultado
La agenda conserva el hecho de la cancelación sin eliminar la cita.

## UC-007 Reprogramar cita

### Objetivo
Mover una cita a un nuevo horario sin perder consistencia operativa.

### Actores
- asistente;
- recepcionista.

### Flujo principal
1. El usuario selecciona la cita existente.
2. Elige nueva fecha y hora.
3. El sistema valida disponibilidad.
4. El usuario confirma.
5. El sistema conserva trazabilidad mínima del cambio.

### Resultado
Cita reprogramada con claridad administrativa.

## Resultado esperado

Los casos de uso del consultorio deben cubrir la operación central de la V1.0 y preparar, sin contradicciones, la evolución natural de la V1.1.

