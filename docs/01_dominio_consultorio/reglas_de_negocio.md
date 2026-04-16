# Reglas de negocio

## Propósito

Formalizar las reglas operativas que gobiernan el subdominio del consultorio para evitar ambigüedades en base de datos, backend, UI y validaciones.

## Reglas generales

### RN-001. El consultorio es una superficie privada
Pacientes, citas, atenciones, indicaciones internas y cobros no forman parte de ninguna exposición pública.

### RN-002. El sistema organiza lo operativo, no reemplaza criterio clínico
El software registra y ordena la operación. No decide diagnósticos ni sustituye la decisión médica.

### RN-003. La V1 prioriza atención simple
La información clínica en la V1 debe mantenerse breve y suficiente para sostener la operación, sin intentar cubrir una historia clínica extensa.

## Reglas sobre pacientes

### RN-010. Un paciente puede registrarse con datos mínimos
No se exige cédula como condición obligatoria para crear un registro inicial.

### RN-011. Debe evitarse la duplicación evidente de pacientes
Si existen coincidencias razonables por nombre, teléfono o cédula, el sistema debe advertir posible duplicidad antes de crear un nuevo registro.

### RN-012. Los datos del paciente pueden completarse después
El registro inicial puede ser mínimo, pero debe poder enriquecerse sin recrear el paciente.

## Reglas sobre citas

### RN-020. No se deben permitir citas solapadas para el mismo profesional
La agenda debe preservar coherencia temporal básica.

### RN-021. Una cita puede existir sin atención
Esto ocurre cuando la cita está programada, fue cancelada o simplemente aún no se ha concretado.

### RN-022. Una cita atendida debe reflejar cierre operativo coherente
Si una cita fue efectivamente utilizada para atender, su estado no debe quedar como programada.

### RN-023. Una cita puede cancelarse sin borrar su existencia
La cancelación es un estado operativo, no una desaparición del hecho administrativo.

### RN-024. La reprogramación debe conservar trazabilidad mínima
Si una cita cambia de horario, el sistema debe preservar suficiente claridad sobre el cambio para no perder seguimiento.

## Reglas sobre atención

### RN-030. Puede existir atención sin cita previa
El sistema debe soportar pacientes que llegan sin agendar.

### RN-031. Toda atención debe estar vinculada a un paciente
No puede existir una atención huérfana.

### RN-032. La atención simple debe registrar un mínimo útil
Debe existir al menos fecha y hora, referencia al paciente y un cuerpo breve de atención o indicación operativa.

### RN-033. La atención no exige historia clínica extensa
La V1 no debe obligar a llenar formularios clínicos largos para completar la operación.

## Reglas sobre cobro

### RN-040. No se puede registrar cobro sin atención
El cobro nace del acto de atención, no de una cita aislada.

### RN-041. Una atención puede tener cobro pendiente
La existencia de la atención no depende de que el cobro haya sido completado de inmediato.

### RN-042. El método de pago debe registrarse de forma controlada
Debe usarse un catálogo de métodos válidos en vez de texto libre ilimitado.

### RN-043. El estado del cobro debe ser explícito
Al menos debe poder distinguirse entre pagado y pendiente.

## Reglas sobre privacidad y sensibilidad

### RN-050. La información del consultorio debe manejarse con mínimo privilegio
Solo roles autorizados deben acceder al contenido del subdominio.

### RN-051. Las notas del consultorio no deben exponerse al catálogo de farmacia ni a superficies públicas
La separación entre contextos es obligatoria.

### RN-052. Los datos sensibles deben minimizarse en la V1
Se debe registrar solo lo necesario para la operación y el aprendizaje del proyecto.

## Reglas sobre operación y consistencia

### RN-060. Los estados deben representar hechos administrativos claros
No se deben usar estados ambiguos ni contradictorios.

### RN-061. Los cambios relevantes deben dejar rastro suficiente
Cancelaciones, reprogramaciones, creación de atención y registro de cobro son hechos que más adelante deben poder auditarse o trazarse.

### RN-062. El sistema debe tolerar operación simple y rápida
No se deben imponer validaciones o pasos innecesarios que rompan el flujo natural del consultorio pequeño.

## Excepciones frecuentes contempladas

- paciente sin cédula;
- paciente sin cita;
- cita cancelada;
- cita reprogramada;
- cobro pendiente;
- datos incompletos al momento de la primera atención.

## Resultado esperado

Las reglas de negocio del consultorio deben servir como base directa para restricciones de base de datos, validaciones de backend, comportamiento de pantallas y criterios de aceptación, evitando interpretaciones improvisadas.

