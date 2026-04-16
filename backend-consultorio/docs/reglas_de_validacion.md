# Reglas de validación

## Propósito

Definir las reglas de validación que debe aplicar `backend-consultorio`, distinguiendo entre validaciones de forma, validaciones de negocio y validaciones de contexto operativo.

## Principio general

Validar no es solo verificar que un campo no venga vacío. En este backend, validar significa proteger:

- la forma del request;
- la coherencia del dominio;
- la integridad del flujo multidoctor;
- la seguridad básica de las operaciones.

## Tipos de validación

## 1. Validación de forma

Se aplica sobre el request DTO.

### Ejemplos
- campos obligatorios;
- longitud mínima o máxima;
- formato de texto;
- enums válidos;
- fechas con estructura correcta.

### Dónde debe vivir
Preferentemente en DTOs, anotaciones y validadores simples cercanos a la frontera HTTP.

## 2. Validación de negocio

Se aplica cuando una operación puede ser formalmente correcta, pero inválida para el dominio.

### Ejemplos
- registrar cita para profesional inexistente;
- crear atención con paciente inexistente;
- intentar registrar cobro sin atención;
- crear cita en horario ya ocupado por el mismo profesional;
- asociar un usuario inactivo a una operación restringida.

### Dónde debe vivir
En la capa de aplicación o en lógica de negocio suficientemente cercana al caso de uso.

## 3. Validación de autorización contextual

Se aplica cuando una acción depende de la identidad y rol del usuario autenticado.

### Ejemplos
- profesional accediendo solo a lo que le corresponde;
- operador intentando hacer una acción reservada al admin;
- acceso a reportes restringido por rol.

### Dónde debe vivir
En seguridad y autorización, complementada por chequeos de aplicación cuando haga falta.

## Reglas de validación por módulo

## `auth`

### Reglas mínimas
- username obligatorio;
- password obligatoria;
- usuario debe existir;
- usuario debe estar activo;
- credenciales deben ser válidas.

## `usuarios`

### Reglas mínimas
- username obligatorio y no vacío;
- username con unicidad razonable;
- rol válido;
- estado válido;
- asociación a profesional coherente cuando corresponda.

## `profesionales`

### Reglas mínimas
- nombres y apellidos obligatorios;
- estado válido;
- no romper unicidad de asociación con usuario cuando exista esa relación.

## `pacientes`

### Reglas mínimas
- nombres obligatorios;
- apellidos obligatorios;
- teléfono opcional;
- cédula opcional en la V1;
- fechas razonables cuando se envíen.

## `citas`

### Reglas mínimas
- paciente obligatorio;
- profesional obligatorio;
- fecha y hora obligatorias;
- estado válido;
- no solapar agenda del mismo profesional;
- cita cancelada no debe tratarse como vigente;
- futura reprogramación debe respetar reglas propias de V1.1.

## `atenciones`

### Reglas mínimas
- paciente obligatorio;
- profesional obligatorio;
- fecha y hora obligatorias;
- nota breve obligatoria;
- cita opcional, pero si existe debe ser válida;
- si hay cita asociada, su relación debe ser coherente con paciente y profesional.

## `cobros`

### Reglas mínimas
- atención obligatoria;
- un solo cobro por atención en V1;
- monto no negativo;
- método de pago válido;
- estado de cobro válido;
- usuario registrador coherente si se persiste.

## `reportes`

### Reglas mínimas
- formato solicitado válido;
- filtros con estructura correcta;
- rango de fechas razonable si se usa;
- combinación de filtros coherente con el reporte solicitado.

## Reglas específicas importantes del sistema

### RV-001. La agenda no debe solaparse por profesional
La validación correcta no es bloquear cualquier cita simultánea global, sino impedir citas simultáneas del mismo profesional.

### RV-002. Atención puede existir sin cita
Eso debe ser válido si el resto de datos obligatorios está completo.

### RV-003. Cobro depende de atención
Nunca debe aceptarse un cobro suelto desligado del hecho clínico-operativo.

### RV-004. Usuario y profesional no deben mezclarse de forma incoherente
No todo usuario es profesional. La asociación debe validarse cuidadosamente.

### RV-005. Los roles deben ser consistentes con la operación
No basta con que el request esté “bien escrito”; también debe venir de un rol autorizado.

## Qué no debe pasar

- poner toda la validación en el controller;
- confiar en que el frontend ya validó todo;
- repetir la misma validación en cinco capas sin sentido;
- permitir que el ORM o la base sean el único lugar donde revienta la incoherencia;
- lanzar errores genéricos cuando el backend sí puede identificar el problema con más precisión.

## Resultado esperado

Las reglas de validación del backend-consultorio deben dejar claro qué se valida, por qué se valida y en qué capa conviene hacerlo, evitando un sistema frágil o ambiguo en operaciones sensibles del consultorio multidoctor.

