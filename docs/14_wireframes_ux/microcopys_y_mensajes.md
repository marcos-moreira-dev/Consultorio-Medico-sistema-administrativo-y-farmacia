# Microcopys y mensajes

## Propósito

Definir el estilo de textos visibles del sistema para que mensajes, etiquetas, validaciones y confirmaciones sean comprensibles, sobrios y coherentes con la operación del proyecto.

## Principio general

Los textos del sistema deben ayudar a actuar, no a confundir. Deben ser directos, claros y proporcionales al contexto. La interfaz no necesita sonar grandilocuente ni excesivamente técnica.

## Criterios globales

### TXT-001. Lenguaje claro
Usar frases simples, comprensibles y orientadas a la acción.

### TXT-002. Brevedad útil
Los mensajes deben decir lo necesario sin convertirse en párrafos largos dentro de la interfaz.

### TXT-003. Consistencia terminológica
Usar los mismos términos del dominio: paciente, cita, atención, producto, disponibilidad, publicar, inactivar, cobro.

### TXT-004. Tono sobrio
Evitar mensajes infantiles, bromistas o demasiado coloquiales.

## Tipos de mensaje

### Mensajes de acción exitosa
Deben confirmar lo importante sin interrumpir demasiado.

**Ejemplos de estilo:**
- `Paciente registrado correctamente.`
- `Cita reprogramada.`
- `Producto publicado.`
- `Cobro registrado.`

### Mensajes de validación
Deben indicar qué falta o qué está mal de forma concreta.

**Ejemplos de estilo:**
- `Ingrese el nombre del paciente.`
- `La fecha y hora son obligatorias.`
- `El producto no puede publicarse con datos incompletos.`

### Mensajes de error de negocio
Deben explicar por qué una acción no puede realizarse.

**Ejemplos de estilo:**
- `No puede registrar un cobro sin una atención previa.`
- `Ya existe una cita para ese horario.`
- `El producto está inactivo y no puede publicarse.`

### Mensajes de confirmación
Convienen para cambios de estado importantes o acciones con impacto operativo.

**Ejemplos de estilo:**
- `¿Desea cancelar esta cita?`
- `¿Desea inactivar este producto?`
- `¿Desea despublicar este producto?`

### Mensajes de acceso o seguridad
Deben ser sobrios y no filtrar detalles innecesarios.

**Ejemplos de estilo:**
- `No tiene permisos para realizar esta acción.`
- `Su sesión no es válida o ha expirado.`

## Criterios por superficie

### Consultorio
Los mensajes deben priorizar claridad operativa y discreción, especialmente al tratar datos sensibles.

### Farmacia pública
Los textos deben ser más ligeros y orientados a exploración o consulta, pero igual de claros.

### Farmacia administrativa
Los mensajes deben parecer internos, directos y centrados en mantenimiento del catálogo.

## Etiquetas y acciones recomendadas

Preferir verbos concretos:
- Guardar;
- Cancelar;
- Buscar;
- Registrar;
- Publicar;
- Inactivar;
- Reprogramar;
- Cobrar.

## Qué evitar

- mensajes vagos como `Error inesperado` sin más contexto útil para el usuario;
- tecnicismos internos de backend o base de datos;
- mensajes demasiado largos para tareas simples;
- tono inconsistente entre pantallas.

## Resultado esperado

Los microcopys del proyecto deben hacer que la interacción sea más clara, más segura y más profesional, reforzando la comprensión del sistema en lugar de añadir ruido o confusión.