# Restricciones de integridad

## Propósito

Definir las restricciones de integridad que deben proteger la consistencia de la base de datos del consultorio multidoctor y evitar estados contradictorios o relaciones inválidas.

## Principio general

La integridad no debe dejarse solo al frontend ni a la buena voluntad del backend. La base de datos también debe defender activamente ciertas reglas mínimas del subdominio.

## Integridad de entidad

### RI-001. Toda tabla principal debe tener clave primaria
Rol, usuario, profesional, paciente, cita, atención y cobro deben tener una identidad única y estable.

### RI-002. Las claves primarias no deben depender de atributos de negocio cambiantes
No conviene usar nombre, teléfono, cédula o username como PK natural principal.

## Integridad referencial

### RI-010. Toda cita debe referenciar un paciente válido
No puede existir una cita huérfana.

### RI-011. Toda cita debe referenciar un profesional válido
No puede existir una cita sin profesional responsable.

### RI-012. Toda atención debe referenciar un paciente válido
No puede existir una atención sin paciente.

### RI-013. Toda atención debe referenciar un profesional válido
No puede existir una atención sin profesional responsable.

### RI-014. Toda atención que apunte a una cita debe hacerlo a una cita existente
Si se usa referencia opcional a cita, la FK debe ser válida.

### RI-015. Todo cobro debe referenciar una atención válida
No puede existir un cobro sin atención.

### RI-016. Todo usuario debe referenciar un rol válido
No debe existir usuario interno sin rol controlado.

## Integridad de dominio persistido

### RI-020. Los campos obligatorios mínimos no deben ser nulos
Por ejemplo, nombres básicos del paciente y profesional, fecha y hora de cita, fecha y hora de atención, monto y estado del cobro cuando corresponda.

### RI-021. Los estados cerrados deben restringirse
Estados de cita, usuario y cobro deben limitarse a un conjunto conocido y no quedar abiertos a cualquier texto arbitrario.

### RI-022. Los montos de cobro deben ser coherentes
No deben permitirse valores negativos o estructuras absurdas para el dominio.

### RI-023. Debe evitarse solapamiento lógico de citas del mismo profesional
La regla real en entorno multidoctor no es impedir toda coincidencia global, sino impedir coincidencia del mismo profesional en el mismo horario.

## Integridad histórica y operativa

### RI-030. Cancelar no es borrar
Una cita cancelada debe conservarse como hecho persistido cuando el modelo así lo requiera.

### RI-031. Una atención realizada no debe desaparecer del historial operativo sin causa extraordinaria
La base debe favorecer trazabilidad de hechos realizados.

### RI-032. El cobro debe reflejar un estado claro
Al menos debe distinguir entre pendiente y pagado.

### RI-033. La relación usuario ↔ profesional debe ser coherente
Si un usuario representa a un profesional, esa asociación debe ser única y consistente.

## Restricciones que conviene evaluar con cuidado

### Integridad por unicidad del paciente
No siempre conviene forzar unicidad absoluta por cédula o teléfono en la V1, porque puede haber registros incompletos o ausencia de documento.

### Integridad por unicidad de profesional
Puede haber un campo profesional de registro o matrícula, pero conviene tratarlo con cuidado si su disponibilidad real no está garantizada en todos los casos.

## Resultado esperado

Las restricciones de integridad deben ayudar a que la base del consultorio sea consistente incluso cuando la aplicación falle o cuando alguien intente persistir estados incompatibles con el dominio multidoctor.

