# Modelo relacional

## Propósito

Describir la forma relacional esperada de la base del consultorio bajo un escenario **multidoctor**, conectando el dominio operativo con las futuras tablas, relaciones y restricciones del esquema SQL.

## Punto de partida

El modelo relacional del consultorio debe derivarse del dominio ya definido:

- un paciente puede existir antes de tener citas;
- un profesional puede existir antes de atender;
- un usuario interno puede o no estar ligado a un profesional;
- una cita pertenece a un paciente y a un profesional;
- una atención pertenece a un paciente y a un profesional;
- una atención puede provenir de una cita o existir sin cita previa;
- un cobro nace de una atención y puede quedar asociado al usuario que lo registró.

## Entidades relacionales núcleo esperadas

### Rol
Representa la función interna del usuario dentro del sistema.

### Usuario
Representa la cuenta de acceso al backend del consultorio.

### Profesional
Representa al médico o profesional que atiende pacientes.

### Paciente
Representa a la persona atendida por el consultorio.

### Cita
Representa una reserva temporal de atención asociada a un profesional.

### Atencion
Representa el registro operativo de una consulta realizada por un profesional.

### Cobro
Representa el registro administrativo del pago asociado a una atención.

## Relaciones relacionales esperadas

### Rol 1:N Usuario
Un rol puede aplicarse a muchos usuarios.

### Usuario 1:0..1 Profesional
Un usuario puede o no estar asociado a un profesional. Si lo está, esa asociación debe ser única.

### Paciente 1:N Cita
Un paciente puede tener múltiples citas.

### Profesional 1:N Cita
Un profesional puede tener múltiples citas.

### Paciente 1:N Atencion
Un paciente puede tener múltiples atenciones.

### Profesional 1:N Atencion
Un profesional puede tener múltiples atenciones.

### Cita 1:0..1 Atencion
Una cita puede no concretarse o concretarse en una atención. La atención puede apuntar a la cita si existió.

### Atencion 1:0..1 Cobro
Una atención puede tener un cobro único o quedar temporalmente sin cobro registrado.

### Usuario 1:N Cobro (opcional como trazabilidad)
Un usuario interno puede registrar múltiples cobros.

## Decisiones relacionales importantes

### 1. Profesional es entidad propia
No debe quedar implícito ni embebido como simple texto en cita o atención.

### 2. Usuario y profesional no son lo mismo
Un profesional normalmente tendrá usuario, pero un usuario también puede ser admin u operador sin ser médico.

### 3. La atención es entidad propia
No debe confundirse con cita. La cita agenda. La atención representa el hecho realizado.

### 4. El cobro depende de la atención
No conviene colgar el cobro directamente de la cita, porque la cita puede cancelarse o no concretarse.

### 5. La base debe tolerar atención sin cita
Eso implica que la FK de cita en atención, si existe, debe ser opcional.

## Qué debe evitar el modelo relacional

- mezclar datos farmacéuticos dentro del consultorio;
- dejar el profesional implícito;
- duplicar identidad del paciente o del profesional en múltiples tablas sin necesidad;
- colapsar cita, atención y cobro en una sola tabla confusa;
- mezclar autenticación con hechos clínico-operativos dentro de una misma tabla.

## Resultado esperado

El modelo relacional del consultorio debe ofrecer una estructura suficientemente clara como para pasar después a tablas concretas, claves, restricciones e índices sin perder fidelidad respecto al dominio multidoctor del negocio.

