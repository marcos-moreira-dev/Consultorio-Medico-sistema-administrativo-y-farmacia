# Plantilla regla de negocio

## Identificador

Asignar un código estable y único.

**Ejemplo:**
`RN-020 No permitir citas solapadas`

## Nombre corto

Describir la regla en una frase breve y clara.

## Propósito

Explicar qué problema resuelve esta regla y por qué existe dentro del negocio.

## Subdominio o contexto

Indicar dónde aplica la regla.

**Ejemplos:**
- consultorio;
- farmacia;
- seguridad;
- operación transversal.

## Descripción de la regla

Redactar la regla principal de forma directa.

Debe quedar claro:

- qué exige;
- qué prohíbe;
- en qué condiciones aplica.

## Justificación operativa

Explicar por qué la regla tiene sentido en el negocio o en la arquitectura del sistema.

## Entidades o conceptos afectados

Listar los conceptos del dominio sobre los que impacta.

**Ejemplos:**
- paciente;
- cita;
- atención;
- cobro;
- producto;
- disponibilidad.

## Casos donde aplica

Describir uno o varios escenarios típicos en los que la regla debe activarse.

## Excepciones o tolerancias

Indicar si la regla admite excepciones y bajo qué condiciones.

## Impacto técnico esperado

Señalar qué partes del sistema deberían reflejar esta regla.

**Ejemplos:**
- validación en backend;
- restricción en base de datos;
- criterio de UI;
- pruebas.

## Error o rechazo asociado

Explicar cómo debería manifestarse el incumplimiento de la regla en términos funcionales.

## Relación con otras reglas

Anotar otras reglas que complementan, dependen o pueden entrar en tensión con esta.

## Observaciones

Espacio opcional para notas de evolución, aclaraciones o límites.

## Nota de uso

Esta plantilla sirve para documentar reglas con impacto real sobre el comportamiento del sistema. No debe llenarse con obviedades triviales ni con frases tan abstractas que luego no puedan implementarse o verificarse.