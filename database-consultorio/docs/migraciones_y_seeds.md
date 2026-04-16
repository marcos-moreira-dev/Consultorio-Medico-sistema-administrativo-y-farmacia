# Migraciones y seeds

## Propósito

Definir cómo debe evolucionar el esquema de `database-consultorio` y cómo se manejarán los datos de ejemplo o estudio de forma reproducible.

## Principio general

La base del consultorio no debe evolucionar a punta de cambios manuales invisibles. El esquema y los datos demo deben poder reconstruirse de forma ordenada para desarrollo, estudio y demostración.

## Estrategia de migraciones

### 1. Evolución por versiones explícitas
El componente debe poder pasar de un diseño inicial estudiable hacia un esquema 3FN y luego a migraciones incrementales controladas.

### 2. Separación entre diseño académico y migración operativa
Conviene distinguir entre:

- SQL de estudio y modelado;
- migraciones efectivas para levantar el componente.

### 3. Migraciones propias del subdominio
Las migraciones del consultorio pertenecen exclusivamente a `database-consultorio` y no deben mezclarse con cambios de farmacia.

## Qué deberían cubrir las migraciones

- creación inicial del esquema del consultorio;
- restricciones y catálogos necesarios;
- ajustes evolutivos de V1.1 cuando correspondan;
- cambios de estructura documentados y explicables.

## Estrategia de seeds

### Seeds demo
Deben servir para:

- probar flujos básicos;
- estudiar consultas;
- demostrar el sistema con datos plausibles.

### Seeds de reset o reinicio
Conviene poder volver a un estado controlado antes de una demo o de una sesión de estudio.

## Criterios de calidad para seeds

- usar datos ficticios y controlados;
- mantener coherencia entre paciente, cita, atención y cobro;
- no introducir volumen innecesario en la V1;
- facilitar lectura del modelo y de las consultas.

## Qué debe evitarse

- cambios manuales en la base sin reflejo en migraciones;
- seeds con datos absurdos o incoherentes;
- scripts mezclados de consultorio y farmacia;
- migraciones tan opacas que no se puedan estudiar después.

## Resultado esperado

La estrategia de migraciones y seeds debe permitir que `database-consultorio` pueda reconstruirse, evolucionar y demostrarse con orden, sirviendo tanto a implementación como a aprendizaje formal del componente.