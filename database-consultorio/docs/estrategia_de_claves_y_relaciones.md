# Estrategia de claves y relaciones

## Propósito

Definir cómo se identificarán las entidades principales del consultorio multidoctor y cómo se modelarán sus relaciones dentro de la base de datos.

## Principio general

Las claves deben dar estabilidad y las relaciones deben expresar el dominio con claridad. Una buena estrategia aquí reduce acoplamientos confusos y facilita tanto la normalización como la evolución futura del esquema.

## Estrategia de claves primarias

### ECR-001. Usar claves sustitutas como identidad principal
Las tablas núcleo del consultorio deben usar una PK artificial y estable.

### ECR-002. La PK no debe depender de datos de negocio cambiantes
No conviene usar cédula, teléfono, username o nombre como clave primaria.

### ECR-003. La elección exacta de tipo de clave debe ser consistente en todo el componente
Si se decide UUID o bigint autogenerado, debe sostenerse con criterio a lo largo del esquema.

## Estrategia de claves foráneas

### Rol ← Usuario
Cada usuario debe tener un rol válido.

### Usuario ← Profesional
La relación puede ser 1:0..1, según el usuario represente o no a un profesional.

### Paciente ← Cita
Cada cita debe tener FK al paciente que la origina.

### Profesional ← Cita
Cada cita debe tener FK al profesional asignado.

### Paciente ← Atencion
Cada atención debe tener FK al paciente atendido.

### Profesional ← Atencion
Cada atención debe tener FK al profesional que realizó la atención.

### Cita ← Atencion
La atención puede tener FK opcional a cita cuando proviene de una agenda previa.

### Atencion ← Cobro
Cada cobro debe tener FK obligatoria a la atención correspondiente.

### Usuario ← Cobro
Opcionalmente, el cobro puede guardar FK al usuario que lo registró.

## Cardinalidades esperadas

### Rol 1:N Usuario
Un rol puede aplicarse a muchos usuarios.

### Usuario 1:0..1 Profesional
Un usuario puede o no corresponder a un profesional. Esa asociación debe ser única si existe.

### Paciente 1:N Cita
Un paciente puede tener varias citas.

### Profesional 1:N Cita
Un profesional puede tener varias citas.

### Paciente 1:N Atencion
Un paciente puede tener varias atenciones.

### Profesional 1:N Atencion
Un profesional puede tener varias atenciones.

### Cita 1:0..1 Atencion
Una cita puede no concretarse o concretarse en una atención.

### Atencion 1:0..1 Cobro
Una atención puede tener un cobro o quedar temporalmente sin uno.

## Estrategia para relaciones opcionales

### Relación opcional entre atención y cita
Debe modelarse de modo que no rompa el caso válido de atención sin cita.

### Relación opcional entre usuario y profesional
Permite tener admins y operadores que no son profesionales clínicos.

## Estrategia para catálogos y estados

### ECR-010. Evaluar si un estado vive como CHECK o como tabla catálogo
En V1, varios estados pueden resolverse con restricciones simples. Si el dominio necesita más flexibilidad o trazabilidad, puede justificarse tabla de catálogo.

### ECR-011. No sobrediseñar catálogos innecesarios
No todo texto requiere una tabla aparte en esta etapa.

## Qué debe evitar esta estrategia

- claves naturales frágiles como eje del modelo;
- relaciones circulares innecesarias;
- claves cruzadas hacia farmacia;
- tablas puente artificiales sin necesidad real del dominio.

## Resultado esperado

La estrategia de claves y relaciones debe dejar preparada una base del consultorio clara, estable y apta para pasar a SQL relacional y luego a 3FN con el menor ruido conceptual posible, incorporando de forma explícita el escenario multidoctor.

