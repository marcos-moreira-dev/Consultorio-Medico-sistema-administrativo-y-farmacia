# Contexto para el modelo conceptual de `database-consultorio`

## Propósito

Este documento sirve como contexto base para diseñar el **modelo conceptual** del componente `database-consultorio`, ahora bajo un escenario **multidoctor**. Está pensado para alimentar una IA o servir como referencia previa al diagrama.

## Regla principal de alcance

Este modelo conceptual debe concentrarse **exclusivamente en el consultorio**.

Sí pertenecen conceptos como:

- rol;
- usuario interno;
- profesional;
- paciente;
- cita;
- atención;
- cobro;
- indicaciones breves dentro de atención.

No pertenecen conceptos como:

- producto;
- stock;
- disponibilidad;
- reservas de farmacia;
- cualquier entidad propia del backend o BD de farmacia.

## Intención del modelo conceptual

El objetivo del modelo conceptual no es escribir SQL todavía. Su función es representar el dominio del consultorio a nivel de:

- entidades;
- atributos relevantes;
- relaciones;
- cardinalidades;
- límites del subdominio.

Debe ser lo bastante claro para que luego pueda transformarse en:

1. una versión tabular en 1FN;
2. un informe de normalización;
3. un esquema final en 3FN;
4. SQL estudiable en PostgreSQL.

## Enfoque de diseño

Este modelo debe representar un **consultorio pequeño y realista con varios profesionales**, no una clínica grande ni un hospital.

La meta es modelar con fidelidad operativa suficiente:

- múltiples profesionales;
- usuarios internos con roles distintos;
- registro de pacientes;
- agenda de citas por profesional;
- atención simple;
- indicaciones breves;
- cobro asociado a la atención.

No debe inflarse con historia clínica extensa, hospitalización, laboratorio, farmacia integrada ni módulos clínicos empresariales innecesarios para la V1.

## Entidades núcleo recomendadas

### 1. Rol

Representa la función interna del usuario.

**Atributos conceptuales sugeridos:**
- rol_id
- nombre_rol
- descripcion_breve

**Roles esperados para la V1:**
- ADMIN_CONSULTORIO
- OPERADOR_CONSULTORIO
- PROFESIONAL_CONSULTORIO

### 2. Usuario

Representa la cuenta de acceso al sistema del consultorio.

**Atributos conceptuales sugeridos:**
- usuario_id
- username
- password_hash
- estado_usuario
- fecha_creacion
- fecha_actualizacion

**Notas:**
- un usuario tiene un rol;
- no todo usuario tiene que ser profesional clínico.

### 3. Profesional

Representa al médico o profesional que atiende en el consultorio.

**Atributos conceptuales sugeridos:**
- profesional_id
- nombres
- apellidos
- especialidad_breve
- registro_profesional
- estado_profesional
- fecha_creacion
- fecha_actualizacion

**Notas:**
- el profesional debe ser entidad propia;
- un profesional puede estar asociado a un usuario del sistema;
- el sistema deja de ser mono-doctor implícito.

### 4. Paciente

Representa a la persona atendida por el consultorio.

**Atributos conceptuales sugeridos:**
- paciente_id
- nombres
- apellidos
- telefono
- cedula
- fecha_nacimiento
- direccion_basica
- fecha_creacion
- fecha_actualizacion

### 5. Cita

Representa una reserva temporal de atención.

**Atributos conceptuales sugeridos:**
- cita_id
- fecha_hora_inicio
- estado_cita
- motivo_breve
- observacion_operativa
- fecha_creacion
- fecha_actualizacion

**Notas:**
- una cita pertenece a un paciente;
- una cita pertenece a un profesional;
- una cita puede cancelarse;
- una cita puede concretarse o no en una atención;
- cita no es lo mismo que atención.

### 6. Atencion

Representa el hecho operativo de una consulta realizada.

**Atributos conceptuales sugeridos:**
- atencion_id
- fecha_hora_atencion
- nota_breve
- indicaciones_breves
- fecha_creacion
- fecha_actualizacion

**Notas:**
- una atención pertenece a un paciente;
- una atención pertenece a un profesional;
- una atención puede venir de una cita previa o puede existir sin cita;
- la atención debe modelarse como entidad propia.

### 7. Cobro

Representa el registro administrativo del pago asociado a una atención.

**Atributos conceptuales sugeridos:**
- cobro_id
- monto
- metodo_pago
- estado_cobro
- observacion_administrativa
- fecha_hora_registro
- fecha_creacion
- fecha_actualizacion

**Notas:**
- el cobro depende de la atención;
- conviene poder relacionarlo con el usuario que lo registró;
- no conviene colgarlo directamente de cita.

## Relaciones conceptuales recomendadas

### Rol 1:N Usuario
Un rol puede aplicarse a muchos usuarios.
Cada usuario tiene un rol principal.

### Usuario 1:0..1 Profesional
Un usuario puede estar asociado a un profesional o no.

### Paciente 1:N Cita
Un paciente puede tener muchas citas.
Cada cita pertenece a un solo paciente.

### Profesional 1:N Cita
Un profesional puede tener muchas citas.
Cada cita pertenece a un solo profesional.

### Paciente 1:N Atencion
Un paciente puede tener muchas atenciones.
Cada atención pertenece a un solo paciente.

### Profesional 1:N Atencion
Un profesional puede tener muchas atenciones.
Cada atención pertenece a un solo profesional.

### Cita 1:0..1 Atencion
Una cita puede no concretarse nunca.
O puede concretarse en una sola atención.

### Atencion 1:0..1 Cobro
Una atención puede tener un cobro único o todavía no tenerlo registrado.
Todo cobro pertenece a una sola atención.

### Usuario 1:N Cobro
Un usuario interno puede registrar muchos cobros.

## Decisiones conceptuales importantes

### 1. Paciente y profesional son entidades distintas
No deben confundirse ni simplificarse como si todo fuera “persona” dentro de la V1.

### 2. Usuario y profesional no son la misma cosa
Hay usuarios que no son médicos, como admin u operador.

### 3. Cita y atención son conceptos distintos
La cita organiza el tiempo.
La atención registra el hecho consumado.

### 4. El cobro depende de la atención
No depende de la cita, porque la cita puede cancelarse o no concretarse.

### 5. La atención puede existir sin cita
Esto es esencial para representar pacientes que llegan espontáneamente.

## Lo que conviene dejar fuera por ahora

Para esta fase del modelo conceptual, conviene no meter todavía:

- farmacia;
- catálogos clínicos amplios;
- historia clínica extensa;
- hospitalización;
- laboratorio;
- scheduler o worker;
- cualquier dependencia externa innecesaria.

## Sugerencia visual para el diagrama

Conviene ubicar visualmente las entidades así:

- `Rol` y `Usuario` en una zona de seguridad interna;
- `Profesional` cerca de `Usuario`;
- `Paciente` al centro operativo;
- `Cita` y `Atencion` como eventos del consultorio;
- `Cobro` colgando de `Atencion`.

## Resultado esperado

Si el diagrama conceptual se construye a partir de este contexto, el resultado debería ser un modelo:

- centrado en consultorio;
- explícitamente multidoctor;
- coherente con usuarios y roles;
- limpio para estudiar;
- apto para transformarse luego en 1FN, normalización y 3FN sin contradicciones fuertes.

