# Índices y rendimiento

## Propósito

Definir criterios razonables de indexación y rendimiento para la base de datos del consultorio multidoctor dentro del alcance de la V1.

## Principio general

La V1 no necesita optimización obsesiva, pero sí debe evitar decisiones ingenuas que vuelvan torpe la búsqueda de pacientes, la agenda por profesional, la consulta de atenciones o la revisión de cobros. El rendimiento debe ser suficiente para un consultorio pequeño realista con varios doctores.

## Áreas donde más importa el rendimiento

### 1. Búsqueda de pacientes
Debe poder encontrarse rápidamente un paciente por nombre, apellidos, teléfono o identificador útil.

### 2. Agenda por profesional
Las consultas por fecha y hora deben ser razonablemente eficientes, pero sobre todo filtradas por profesional.

### 3. Historial operativo del paciente
Debe poder recuperarse sin fricción el conjunto de atenciones y citas ligadas a un paciente.

### 4. Historial por profesional
Debe poder revisarse agenda y atenciones de un profesional sin escanear todo el conjunto.

### 5. Registro y revisión de cobros
Las búsquedas por atención, estado de cobro o usuario registrador deben mantenerse claras y eficientes.

## Criterios de indexación esperados

### IDX-001. Indexar claves foráneas relevantes
Particularmente en tablas como `usuario`, `profesional`, `cita`, `atencion` y `cobro`.

### IDX-002. Indexar campos de consulta frecuente
Especialmente campos usados para buscar pacientes o filtrar agenda por profesional y fecha.

### IDX-003. No indexar por costumbre todo indiscriminadamente
Demasiados índices también encarecen escritura y complejidad.

### IDX-004. Priorizar índices con valor real para el dominio
El índice debe responder a una consulta esperable del consultorio multidoctor.

## Consultas que conviene tener en mente

- buscar paciente por nombre o apellido;
- buscar paciente por teléfono o identificador;
- listar citas por profesional y fecha;
- recuperar atenciones de un paciente;
- recuperar atenciones de un profesional;
- encontrar cobros por estado o por usuario registrador.

## Rendimiento y normalización

La 3FN ayuda a mantener integridad y claridad, pero no elimina la necesidad de pensar en consultas frecuentes. Por eso, después de normalizar, conviene revisar si ciertos índices resultan necesarios para mantener buena respuesta operativa.

## Qué evitar

- optimización prematura sin consulta real detrás;
- falta total de índices en campos de búsqueda frecuente;
- confundir un problema de modelado con un problema de indexación;
- diseñar como si el volumen fuera hospitalario cuando el proyecto es de escala pequeña.

## Resultado esperado

Este documento debe dejar una postura clara: el consultorio multidoctor no necesita tuning extremo, pero sí una base razonablemente bien indexada para que la agenda, la atención, los pacientes y los cobros se sientan ágiles y estudiables.

