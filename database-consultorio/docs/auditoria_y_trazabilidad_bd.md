# Auditoría y trazabilidad BD

## Propósito

Definir qué nivel de rastro y control conviene sostener desde la base de datos del consultorio multidoctor, distinguiendo entre trazabilidad útil y auditoría más deliberada.

## Principio general

La base del consultorio no debe convertirse en una bitácora infinita, pero sí debe permitir reconstruir ciertos hechos importantes del subdominio cuando sea necesario para estudio, diagnóstico o control operativo.

## Qué conviene poder rastrear desde la BD

### Paciente
- creación del registro;
- actualización relevante de datos.

### Profesional
- creación del registro;
- activación o inactivación;
- asociación o desasociación con usuario interno si aplica.

### Usuario interno
- creación;
- cambio relevante de estado;
- vínculo con rol.

### Cita
- creación;
- cancelación;
- futura reprogramación en V1.1;
- profesional asignado.

### Atención
- registro de la atención;
- profesional responsable;
- referencia temporal del hecho.

### Cobro
- creación del cobro;
- estado del cobro;
- usuario que lo registró, si se persiste esa referencia.

## Trazabilidad mínima esperada

Como mínimo, el componente debería poder sostener:

- timestamps de creación y actualización donde aplique;
- relaciones claras entre entidades;
- rastro suficiente para entender secuencia operativa básica;
- posibilidad de asociar hechos clínico-operativos a profesional y hechos administrativos a usuario interno.

## Auditoría útil en esta V1

No se busca una plataforma de compliance pesada, pero sí tiene sentido considerar más control sobre:

- cambios importantes de cita;
- cambios relevantes en paciente;
- cobros registrados o cambiados;
- cambios de estado de usuario y profesional;
- acciones que luego necesiten explicación operativa.

## Decisiones a cuidar

### 1. No duplicar innecesariamente lo que ya vive mejor en aplicación
No todo debe auditarse a nivel relacional si el backend ya lo resuelve mejor.

### 2. No exponer contenido sensible por querer trazar demasiado
La auditoría no debe transformarse en fuga de datos.

### 3. Mantener proporcionalidad
El rastro debe ser útil para un consultorio pequeño multidoctor, no una estructura hospitalaria desbordada.

## Resultado esperado

La base del consultorio debe poder sostener un nivel razonable de trazabilidad y auditoría útil, suficiente para estudiar el comportamiento del sistema y apoyar diagnóstico, sin perder simplicidad ni privacidad, e incorporando además la dimensión de profesional y usuario interno.

