# Informe de normalización de V0/1FN a 3FN del consultorio multidoctor

## Propósito

Este documento explica cómo se transforma la primera versión relacional de estudio del subdominio consultorio hacia un modelo más limpio y estable en tercera forma normal (3FN), ahora bajo un escenario **multidoctor**.

## Alcance

Este informe corresponde exclusivamente a `database-consultorio`. No analiza farmacia ni ninguna otra base del sistema.

## Punto de partida

La versión inicial en 1FN fue construida con intención pedagógica. Ya cumple una estructura tabular válida y usa valores atómicos, pero todavía arrastra redundancias y dependencias que no conviene conservar en el diseño final.

Las tablas consideradas en ese punto de partida son:

- `rol_1fn`
- `usuario_1fn`
- `profesional_1fn`
- `paciente_1fn`
- `cita_1fn`
- `atencion_1fn`
- `cobro_1fn`

## Qué sí cumple la versión 1FN

La versión inicial ya cumple 1FN porque:

- cada tabla tiene filas identificables;
- los atributos contienen valores atómicos;
- no hay grupos repetidos dentro de una misma columna;
- ya existe una noción básica de relación entre seguridad interna, paciente, profesional, agenda, atención y cobro.

## Problemas principales detectados en el diseño inicial

### 1. Redundancia de datos de profesional
En la versión 1FN, datos como nombres y apellidos del profesional aparecen repetidos en `cita_1fn`, `atencion_1fn` y `cobro_1fn`.

### 2. Redundancia de datos del paciente
Datos del paciente aparecen repetidos en `cita_1fn` y `atencion_1fn`.

### 3. Redundancia de datos de atención en cobro
`cobro_1fn` repite narrativa breve de la atención, aunque ese dato realmente pertenece a `atencion`.

### 4. Uso de claves compuestas con intención didáctica
En `cita_1fn`, `atencion_1fn` y `cobro_1fn` se usan claves compuestas basadas en profesional y marca temporal, lo cual ayuda al estudio, pero complica referencias y deja espacio a dependencias parciales.

### 5. Mezcla entre identidad y evento
La identidad del profesional y del paciente se arrastra dentro de entidades cuyo foco debería ser el evento operativo: cita, atención o cobro.

## Análisis hacia segunda forma normal (2FN)

## Recordatorio conceptual

Una tabla está en 2FN si:

- ya está en 1FN;
- todos los atributos no clave dependen de la clave completa;
- no existen dependencias parciales respecto de una clave compuesta.

## Dónde aparecen dependencias parciales en la versión inicial

### Caso de `cita_1fn`
La clave primaria es `(profesional_id, fecha_hora_inicio)`, pero los atributos del profesional dependen solo de `profesional_id` y los del paciente dependen de `paciente_id`, no de la clave completa.

### Caso de `atencion_1fn`
La clave primaria es `(profesional_id, fecha_hora_atencion)`, pero los atributos del profesional dependen solo de `profesional_id` y los del paciente dependen solo de `paciente_id`.

### Caso de `cobro_1fn`
La clave primaria es `(profesional_id, fecha_hora_atencion)`, pero:

- datos del profesional dependen de `profesional_id`;
- parte del contenido depende del hecho de atención;
- el cobro no debería cargar narrativa clínica.

## Transformaciones necesarias para llegar a 2FN

### Transformación 1. Mantener al profesional como fuente única de sus datos
Toda la información identificatoria del profesional debe vivir en `profesional`.

### Transformación 2. Mantener al paciente como fuente única de sus datos
Toda la información identificatoria del paciente debe vivir en `paciente`.

### Transformación 3. Separar identidad de atención y cobro
`cobro` no debe arrastrar columnas narrativas o clínicas de `atencion`.

### Transformación 4. Adoptar PK sustitutas más limpias
Para el diseño final conviene usar:

- `cita_id`
- `atencion_id`
- `cobro_id`

## Resultado esperado al alcanzar 2FN

Al llegar a 2FN, el modelo ya debería:

- eliminar repeticiones innecesarias de paciente y profesional;
- separar mejor cada entidad según su responsabilidad;
- dejar de depender de PK compuestas como eje del diseño final.

## Análisis hacia tercera forma normal (3FN)

## Recordatorio conceptual

Una tabla está en 3FN si:

- ya está en 2FN;
- no existen dependencias transitivas entre atributos no clave.

## Dónde conviene vigilar dependencias transitivas

### 1. Estados y catálogos cerrados
Atributos como `estado_cita`, `estado_cobro`, `estado_usuario` y `estado_profesional` pueden resolverse con `CHECK` simples en V1.

### 2. Separación entre usuario y profesional
Usuario y profesional se relacionan, pero no deben colapsarse en una sola entidad. Usuario es acceso; profesional es actor clínico.

### 3. Separación entre atención y cobro
La atención representa un hecho clínico-operativo simple. El cobro representa un hecho administrativo.

## Transformaciones necesarias para llegar a 3FN

### Transformación 5. Consolidar cada entidad con su propio identificador
El modelo final debe usar tablas del estilo:

- `rol`
- `usuario`
- `profesional`
- `paciente`
- `cita`
- `atencion`
- `cobro`

### Transformación 6. Mantener solo atributos que dependan de la entidad correcta
Cada tabla debe guardar únicamente atributos propios de su responsabilidad.

### Transformación 7. Mantener la relación entre cita y atención como opcional y limpia
Esto permite soportar:

- citas que no se concretan;
- atenciones espontáneas sin cita.

### Transformación 8. Mantener explícita la dimensión multidoctor
Cita y atención deben mantener `profesional_id` como referencia clara.

## Modelo conceptual de llegada a 3FN

### `rol`
Entidad de autorización básica.

### `usuario`
Entidad de acceso al sistema.

### `profesional`
Entidad del médico/profesional clínico.

### `paciente`
Entidad eje de la persona atendida.

### `cita`
Entidad de agenda, con FK a `paciente` y `profesional`.

### `atencion`
Entidad de hecho operativo, con FK a `paciente`, `profesional` y FK opcional a `cita`.

### `cobro`
Entidad administrativa, con FK a `atencion` y opcionalmente a `usuario` registrador.

## Beneficios del paso a 3FN

- menor redundancia;
- menor riesgo de inconsistencias;
- relaciones más claras;
- esquema más profesional para backend, ORM, migraciones y estudio formal;
- mejor base para reportes, filtros por profesional y trazabilidad administrativa.

## Decisiones finales que este informe recomienda

- usar PK sustitutas estables para `cita`, `atencion` y `cobro`;
- conservar `paciente` como fuente única de identidad del paciente;
- conservar `profesional` como fuente única de identidad clínica;
- mantener `usuario` separado de `profesional`;
- mantener `atencion` separada de `cita`;
- mantener `cobro` dependiente de `atencion`;
- resolver estados simples con `CHECK` en la V1;
- no mezclar ninguna estructura de farmacia.

## Resultado esperado

Después de este proceso, el siguiente paso natural es construir el SQL de la versión final en 3FN del consultorio multidoctor, con una estructura más limpia, menos redundancia y mejor alineación con backend, migraciones, reportes y estudio formal de PostgreSQL.

