# Módulos y responsabilidades

## Propósito

Definir los módulos funcionales oficiales de `backend-consultorio` y dejar explícito qué responsabilidad tiene cada uno, qué no debe hacer y con qué otros módulos se relaciona.

## Principio general

Cada módulo debe existir porque resuelve un área concreta del backend. No conviene crear módulos por capricho, pero tampoco conviene mezclar demasiadas responsabilidades en uno solo.

## Módulo `auth`

### Responsabilidad
Gestionar autenticación de usuarios internos del consultorio.

### Incluye
- login;
- emisión de JWT;
- validación técnica del token;
- integración con Spring Security;
- flujo de sesión técnica del backend.

### No debe hacer
- administrar toda la información detallada de usuarios como si fuera módulo completo de negocio;
- absorber por completo roles o profesionales.

## Módulo `usuarios`

### Responsabilidad
Gestionar las cuentas internas del sistema.

### Incluye
- alta de usuarios;
- edición de estado de usuario;
- consulta de usuarios;
- asociación de usuario a profesional cuando corresponda.

### No debe hacer
- reemplazar a `auth`;
- absorber toda la lógica clínica del consultorio.

## Módulo `roles`

### Responsabilidad
Gestionar la estructura de roles internos y su uso dentro del sistema.

### Roles base de la V1
- `ADMIN_CONSULTORIO`
- `OPERADOR_CONSULTORIO`
- `PROFESIONAL_CONSULTORIO`

### Incluye
- consulta de roles;
- validación de consistencia de roles;
- soporte a autorización.

### No debe hacer
- controlar directamente el login;
- absorber la gestión completa de usuarios.

## Módulo `profesionales`

### Responsabilidad
Gestionar la identidad operativa de los doctores o profesionales clínicos del consultorio.

### Incluye
- alta de profesional;
- edición básica;
- activación e inactivación;
- asociación opcional a usuario;
- datos operativos básicos como especialidad breve.

### No debe hacer
- autenticación;
- reemplazar agenda o atención.

## Módulo `pacientes`

### Responsabilidad
Gestionar el registro de pacientes del consultorio.

### Incluye
- alta de paciente;
- edición de datos básicos;
- búsqueda;
- consulta de datos operativos.

### No debe hacer
- gestionar agenda;
- registrar cobros;
- cargar historia clínica extensa fuera del alcance.

## Módulo `citas`

### Responsabilidad
Gestionar la agenda del consultorio.

### Incluye
- creación de cita;
- cancelación;
- consulta de agenda;
- filtros por profesional, fecha y estado;
- validación de no solapamiento por profesional.

### No debe hacer
- registrar atención como si cita y atención fueran lo mismo;
- manejar cobros.

## Módulo `atenciones`

### Responsabilidad
Registrar el hecho operativo de la consulta realizada.

### Incluye
- creación de atención;
- referencia opcional a cita previa;
- asociación obligatoria a profesional y paciente;
- nota breve;
- indicaciones breves.

### No debe hacer
- reemplazar agenda;
- registrar cobro dentro de la misma responsabilidad.

## Módulo `cobros`

### Responsabilidad
Gestionar el hecho administrativo del pago asociado a una atención.

### Incluye
- registrar cobro;
- consultar estado;
- asociar cobro a atención;
- registrar usuario operador cuando corresponda.

### No debe hacer
- crear atención;
- decidir lógica clínica;
- depender de cita directamente como eje del cobro.

## Módulo `reportes`

### Responsabilidad
Generar documentos del consultorio para uso administrativo o clínico-operativo.

### Formatos esperados
- PDF
- DOCX
- XLSX

### Incluye
- armado de datasets;
- composición del documento;
- exportación del archivo;
- filtros por fechas, profesional, paciente o estado según el reporte.

### No debe hacer
- convertirse en motor de jobs pesados si no es necesario en V1;
- absorber lógica de negocio que pertenece a pacientes, citas, atenciones o cobros.

## Relaciones principales entre módulos

- `auth` depende de `usuarios` y `roles` para contexto de acceso;
- `usuarios` se relaciona con `roles` y, opcionalmente, con `profesionales`;
- `citas` depende de `pacientes` y `profesionales`;
- `atenciones` depende de `pacientes`, `profesionales` y opcionalmente `citas`;
- `cobros` depende de `atenciones` y puede relacionarse con `usuarios`;
- `reportes` depende de varios módulos como consumidor de información consolidada.

## Qué debe evitarse

- que `auth` absorba todo lo relacionado con usuarios y roles sin frontera clara;
- que `pacientes` termine cargando agenda o atención;
- que `citas` y `atenciones` se colapsen en un mismo módulo por comodidad;
- que `reportes` se vuelva un cajón desordenado de queries y utilidades sueltas.

## Resultado esperado

Los módulos y sus responsabilidades deben permitir que el backend se implemente con claridad mental y estructural, evitando que Spring Boot termine convertido en una masa única de controladores y servicios confusos.