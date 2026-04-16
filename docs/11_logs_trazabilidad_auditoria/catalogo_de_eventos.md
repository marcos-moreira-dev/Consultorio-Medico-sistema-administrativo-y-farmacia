# Catálogo de eventos

## Propósito

Reunir los eventos más relevantes del sistema desde la perspectiva de trazabilidad, diagnóstico y auditoría útil, consolidando en un solo lugar los hechos del negocio y de operación que conviene registrar.

## Principio general

Un evento relevante es un hecho que cambió el estado del negocio, alteró una operación importante o tiene valor diagnóstico suficiente como para justificar su registro.

No todo clic ni todo detalle técnico merece quedar como evento significativo.

## Eventos del consultorio

### EVT-C-001 Paciente registrado
Se produce cuando se crea por primera vez un paciente.

### EVT-C-002 Paciente actualizado
Se produce cuando cambian datos relevantes del paciente.

### EVT-C-003 Posible duplicado detectado
Se produce cuando el sistema advierte coincidencias importantes antes de crear un registro.

### EVT-C-010 Cita programada
Se produce cuando una cita queda registrada correctamente.

### EVT-C-011 Cita cancelada
Se produce cuando una cita deja de estar vigente sin borrarse.

### EVT-C-012 Cita reprogramada
Se produce cuando se cambia fecha u hora manteniendo seguimiento del hecho.

### EVT-C-020 Atención registrada
Se produce cuando la consulta queda almacenada.

### EVT-C-021 Indicaciones emitidas
Se produce cuando se registran indicaciones o receta breve.

### EVT-C-030 Cobro registrado
Se produce cuando se crea un cobro asociado a una atención.

### EVT-C-031 Cobro marcado como pagado
Se produce cuando el cobro cambia a estado pagado.

### EVT-C-032 Cobro marcado como pendiente
Se produce cuando la atención existe, pero el pago no quedó completado.

## Eventos de farmacia

### EVT-F-001 Producto creado
Se produce cuando se registra un nuevo producto.

### EVT-F-002 Producto actualizado
Se produce cuando cambian datos relevantes del producto.

### EVT-F-003 Posible duplicado de producto detectado
Se produce cuando se detecta coincidencia relevante antes de confirmar el alta.

### EVT-F-010 Producto publicado
Se produce cuando el producto entra al catálogo visible.

### EVT-F-011 Producto despublicado
Se produce cuando deja de estar visible al público.

### EVT-F-012 Producto inactivado
Se produce cuando sale de la oferta vigente.

### EVT-F-020 Disponibilidad actualizada
Se produce cuando cambia la disponibilidad operativa del producto.

### EVT-F-021 Producto marcado como agotado
Se produce cuando deja de poder ofrecerse.

### EVT-F-022 Producto restituido a disponible
Se produce cuando vuelve a ofrecerse.

### EVT-F-030 Reserva creada
Evento previsto para V1.1.

### EVT-F-031 Reserva cancelada
Evento previsto para V1.1.

### EVT-F-032 Reserva concretada
Evento previsto para V1.1.

## Eventos técnicos y transversales

### EVT-T-001 Inicio de sesión exitoso
Útil para trazabilidad de acceso.

### EVT-T-002 Acceso denegado
Útil para seguridad y diagnóstico.

### EVT-T-003 Error de validación relevante
Útil para entender fallos frecuentes en flujos de negocio.

### EVT-T-004 Error inesperado en backend
Útil para diagnóstico operativo y técnico.

### EVT-T-005 Migración aplicada
Útil para seguimiento de evolución del sistema.

## Datos mínimos sugeridos por evento

Cada evento relevante debería, cuando aplique, registrar al menos:

- identificador del evento;
- fecha y hora;
- componente origen;
- actor o usuario si corresponde;
- entidad principal afectada;
- resultado o estado principal;
- correlation_id o identificador equivalente para diagnóstico.

## Reglas para el registro de eventos

- no registrar contenido sensible innecesario en el cuerpo del evento;
- distinguir entre evento de negocio y mensaje técnico de log;
- priorizar hechos con valor real para operación y análisis;
- permitir que el catálogo crezca con la V1.1 sin romper consistencia.

## Resultado esperado

Este catálogo debe funcionar como referencia común para backend, logs, auditoría y futuras capacidades de diagnóstico, ayudando a que el sistema tenga memoria operativa sin exceso de ruido.

