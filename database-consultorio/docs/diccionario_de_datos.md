# Diccionario de datos

## Propósito

Definir el significado funcional de las tablas y columnas principales esperadas en la base de datos del consultorio multidoctor, de forma que el modelo sea legible tanto para implementación como para estudio posterior.

## Alcance

Este diccionario corresponde únicamente a `database-consultorio`. No describe estructuras de farmacia ni mezcla conceptos de otros componentes.

## Tablas núcleo esperadas

### `rol`
Representa la función interna del usuario.

**Columnas esperadas de alto nivel:**
- identificador principal;
- nombre_rol;
- descripcion_breve;
- timestamps mínimos de control.

### `usuario`
Representa la cuenta de acceso al sistema del consultorio.

**Columnas esperadas de alto nivel:**
- identificador principal;
- rol_id;
- username o identificador de acceso;
- password_hash;
- estado_usuario;
- fecha_creacion;
- fecha_actualizacion.

### `profesional`
Representa al médico o profesional que atiende dentro del consultorio.

**Columnas esperadas de alto nivel:**
- identificador principal;
- usuario_id opcional o único según diseño;
- nombres;
- apellidos;
- especialidad_breve;
- registro_profesional si aplica;
- estado_profesional;
- fecha_creacion;
- fecha_actualizacion.

### `paciente`
Representa a la persona atendida por el consultorio.

**Columnas esperadas de alto nivel:**
- identificador principal;
- nombres;
- apellidos;
- telefono;
- cedula o identificador equivalente;
- fecha_nacimiento;
- direccion_basica;
- fecha_creacion;
- fecha_actualizacion.

### `cita`
Representa una reserva temporal de atención.

**Columnas esperadas de alto nivel:**
- identificador principal;
- paciente_id;
- profesional_id;
- fecha_hora_inicio;
- estado_cita;
- motivo_breve;
- observacion_operativa;
- fecha_creacion;
- fecha_actualizacion.

### `atencion`
Representa el hecho operativo de una consulta realizada.

**Columnas esperadas de alto nivel:**
- identificador principal;
- paciente_id;
- profesional_id;
- cita_id opcional;
- fecha_hora_atencion;
- nota_breve;
- indicaciones_breves;
- fecha_creacion;
- fecha_actualizacion.

### `cobro`
Representa el registro administrativo del pago asociado a una atención.

**Columnas esperadas de alto nivel:**
- identificador principal;
- atencion_id;
- registrado_por_usuario_id opcional o recomendado;
- monto;
- metodo_pago;
- estado_cobro;
- observacion_administrativa;
- fecha_hora_registro;
- fecha_creacion;
- fecha_actualizacion.

## Criterios de lectura del diccionario

### 1. Este documento describe intención funcional, no todavía tipos SQL definitivos
Los tipos concretos y restricciones detalladas se consolidarán en el esquema relacional final.

### 2. El diccionario debe alinearse con el dominio
Si una columna existe, debe tener justificación dentro del consultorio multidoctor.

### 3. No se deben introducir columnas por costumbre vacía
Cada atributo debe tener valor operativo o pedagógico claro.

## Qué debe evitarse

- columnas que dupliquen innecesariamente información del paciente o del profesional en cita o atención;
- atributos farmacéuticos dentro del consultorio;
- nombres ambiguos sin significado funcional claro;
- acumulación de campos clínicos fuera del alcance de la V1.

## Resultado esperado

Este diccionario debe funcionar como un puente entre dominio y SQL: suficientemente concreto para orientar el esquema y suficientemente claro para que luego puedas estudiar por qué existe cada parte del modelo, ahora con multidoctor y control de usuarios y roles.

