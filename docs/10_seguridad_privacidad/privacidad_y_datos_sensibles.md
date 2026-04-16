# Privacidad y datos sensibles

## Propósito

Clasificar la sensibilidad de la información del sistema y fijar criterios de tratamiento acordes con el alcance de la V1.

## Principio general

La privacidad no depende solo de esconder pantallas. Depende de reconocer qué datos son sensibles, minimizar su exposición y limitar quién puede consultarlos, modificarlos o reutilizarlos.

## Clasificación de sensibilidad

### Nivel 1. Datos públicos
Son datos que pueden mostrarse al exterior sin comprometer privacidad personal ni funcionamiento interno delicado.

**Ejemplos:**
- nombre del producto;
- presentación;
- categoría;
- disponibilidad publicada;
- detalle público autorizado;
- precio visible, si se decide mostrarlo.

## Nivel 2. Datos internos no públicos
Son datos que no necesariamente son sensibles en sentido clínico, pero pertenecen a la operación interna del negocio.

**Ejemplos:**
- estado interno del producto;
- decisiones de publicación;
- referencias operativas de stock;
- notas administrativas internas;
- configuraciones de usuarios y permisos.

## Nivel 3. Datos privados sensibles
Son datos cuyo tratamiento requiere mayor cuidado por estar asociados a personas y al contexto de atención de salud.

**Ejemplos:**
- identidad y datos de pacientes;
- citas y horarios vinculados a pacientes;
- notas de atención;
- indicaciones o receta breve;
- cobros del consultorio vinculados a la atención.

## Reglas de tratamiento por nivel

### Para datos públicos
- pueden circular por la superficie pública controlada;
- deben filtrarse desde backend para exponer solo lo autorizado.

### Para datos internos no públicos
- deben limitarse a usuarios autenticados con rol adecuado;
- no deben formar parte de contratos públicos por comodidad.

### Para datos privados sensibles
- requieren autenticación y autorización;
- deben tratarse con mínimo privilegio;
- no deben aparecer en superficies públicas ni en respuestas no autorizadas;
- deben minimizarse en volumen dentro de la V1.

## Principio de minimización

La V1 no debe almacenar ni exponer más información de la necesaria para sostener la operación del negocio y el aprendizaje del proyecto.

### Implicaciones prácticas
- no forzar historia clínica extensa;
- no pedir campos clínicos innecesarios;
- no registrar datos sensibles sin propósito operativo claro;
- no usar un solo DTO para datos públicos y privados.

## Riesgos de privacidad relevantes

### Riesgo 1. Mezclar datos clínicos y comerciales
Puede ocurrir si se modela el proyecto como una sola masa funcional.

### Riesgo 2. Exceso de exposición por contratos mal diseñados
Sucede cuando se reutilizan respuestas internas en superficies públicas o administrativas distintas.

### Riesgo 3. Acumulación innecesaria de datos
Aumenta complejidad, riesgo conceptual y carga de mantenimiento sin aportar valor real a la V1.

## Criterios de diseño derivados

### PRI-001. Todo dato del consultorio se presume privado salvo decisión explícita en contrario

### PRI-002. La farmacia solo publica datos previamente clasificados como publicables

### PRI-003. Logs, errores y respuestas no deben filtrar contenido sensible innecesario

### PRI-004. La documentación debe distinguir entre dato operativo, dato interno y dato sensible

## Resultado esperado

Este documento debe ayudarte a pensar la privacidad como una cualidad estructural del sistema: qué datos existen, qué tan delicados son y cómo su sensibilidad afecta diseño de APIs, base de datos, UI, logs y pruebas.

