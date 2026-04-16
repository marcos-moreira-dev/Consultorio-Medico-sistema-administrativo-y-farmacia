# Datos mínimos requeridos

## Propósito

Definir los datos mínimos que el sistema necesita para operar el consultorio de manera útil en la V1, diferenciando entre campos obligatorios, opcionales y de tratamiento delicado.

## Principio general

Se debe registrar solo lo necesario para sostener la operación y el aprendizaje del proyecto. La V1 no persigue acumulación excesiva de información clínica ni formularios largos.

## Paciente

### Obligatorios
- nombres;
- apellidos.

### Recomendados
- teléfono;
- cédula o identificador equivalente.

### Opcionales
- fecha de nacimiento;
- dirección básica;
- notas internas no clínicas.

### Observaciones
- el sistema debe permitir registrar pacientes sin cédula;
- la combinación de datos debe ser suficiente para encontrarlos después;
- la información sensible de salud no pertenece al registro base del paciente, sino a la atención.

## Cita

### Obligatorios
- paciente asociado;
- fecha y hora de inicio;
- estado.

### Recomendados
- motivo breve;
- observación operativa breve si aplica.

### Estados mínimos sugeridos
- programada;
- atendida;
- cancelada.

### Observaciones
- el motivo debe mantenerse breve y sin convertir la cita en una ficha clínica extensa.

## Atención

### Obligatorios
- paciente asociado;
- fecha y hora;
- cuerpo breve de atención o nota operativa útil.

### Opcionales
- referencia a cita;
- indicaciones breves;
- receta breve si el diseño lo separa conceptualmente.

### Observaciones
- la atención puede existir sin cita previa;
- la información clínica debe mantenerse acotada en la V1;
- no se busca historia clínica completa.

## Cobro

### Obligatorios
- atención asociada;
- monto;
- método de pago;
- estado del cobro.

### Opcionales
- observación administrativa breve;
- fecha y hora explícita si no se deriva del registro.

### Catálogos mínimos sugeridos
#### Método de pago
- efectivo;
- transferencia;
- tarjeta.

#### Estado del cobro
- pagado;
- pendiente.

## Usuario interno del consultorio

### Obligatorios
- identificador;
- nombre de usuario o equivalente;
- rol;
- activo.

### Observaciones
- este dato pertenece más a seguridad y acceso, pero conviene reconocer desde el dominio que el consultorio necesita usuarios internos autorizados.

## Datos delicados o sensibles

Dentro de este subdominio deben tratarse con mayor cuidado:

- motivo de consulta si revela salud;
- notas de atención;
- indicaciones;
- cualquier referencia clínica o diagnóstica.

La regla general es minimizar, proteger y no exponer públicamente.

## Datos que no deben forzarse en la V1

- antecedentes clínicos extensos;
- diagnósticos estructurados complejos;
- formularios médicos especializados;
- imágenes, adjuntos o archivos pesados;
- seguros y convenios complejos como requisito base.

## Resultado esperado

Con estos datos mínimos, el sistema debe poder registrar pacientes, manejar agenda, documentar atención simple y sostener cobros sin caer en un modelo excesivamente pobre ni en una complejidad clínica innecesaria.

