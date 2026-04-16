# Servicios y casos de uso

## Propósito

Definir cómo debe organizarse la lógica de aplicación de `backend-consultorio` y qué casos de uso principales debe soportar el sistema, de modo que la implementación no se convierta en una mezcla confusa de controladores, repositorios y reglas de negocio dispersas.

## Principio general

El backend no debe pensar en términos de “endpoints que hacen cosas” solamente. Debe pensar en **casos de uso del negocio** coordinados por servicios de aplicación claros.

La idea central es esta:

- el controller recibe la solicitud HTTP;
- valida estructura básica del request;
- delega a un servicio o caso de uso;
- el servicio coordina lógica, validaciones de negocio y persistencia;
- el controller traduce el resultado a un response DTO uniforme.

## Qué se entiende aquí por servicio

En este contexto, un servicio es una clase de aplicación responsable de coordinar un flujo del negocio.

No debe ser:
- un “super service” gigante con cien métodos;
- un simple wrapper vacío sobre el repository;
- una utilidad procedural sin responsabilidad clara.

## Organización recomendada

Yo recomiendo una mezcla pragmática de:

- **Application Service** para coordinación por módulo;
- **Use Case** explícito para flujos importantes o sensibles.

Eso significa que no hace falta convertir cada acción en una ceremonia extrema, pero sí conviene que los flujos relevantes queden identificables y estudiables.

## Casos de uso principales por módulo

## Módulo `auth`

### Casos de uso esperados
- autenticar usuario interno;
- construir contexto mínimo del usuario autenticado;
- devolver token y datos básicos de sesión.

### Servicio esperado
Un servicio de autenticación responsable de login y validación del contexto interno del usuario.

## Módulo `usuarios`

### Casos de uso esperados
- crear usuario;
- consultar usuario;
- listar usuarios;
- cambiar estado de usuario;
- asociar o desasociar usuario con profesional cuando aplique.

### Servicio esperado
Un servicio de gestión de usuarios con reglas básicas de seguridad y consistencia.

## Módulo `roles`

### Casos de uso esperados
- consultar roles disponibles;
- validar consistencia de rol en altas o cambios de usuario.

### Servicio esperado
Puede ser un servicio pequeño, muy enfocado y sin ceremonias excesivas.

## Módulo `profesionales`

### Casos de uso esperados
- registrar profesional;
- actualizar datos básicos del profesional;
- inactivar profesional;
- consultar profesional;
- listar profesionales.

### Servicio esperado
Debe coordinar identidad operativa del profesional y su relación opcional con usuario del sistema.

## Módulo `pacientes`

### Casos de uso esperados
- registrar paciente;
- actualizar paciente;
- consultar paciente por id;
- listar pacientes;
- buscar pacientes por criterios útiles.

### Servicio esperado
Debe proteger coherencia básica del paciente y centralizar reglas del registro.

## Módulo `citas`

### Casos de uso esperados
- crear cita;
- cancelar cita;
- consultar cita;
- listar citas;
- listar agenda por profesional;
- listar agenda por fecha o rango;
- en V1.1: reprogramar cita.

### Servicio esperado
Debe coordinar especialmente:
- validación de paciente;
- validación de profesional;
- no solapamiento por profesional;
- coherencia del estado de cita.

## Módulo `atenciones`

### Casos de uso esperados
- registrar atención con cita previa;
- registrar atención sin cita previa;
- consultar atención;
- listar atenciones por paciente;
- listar atenciones por profesional;
- listar atenciones por rango de fechas.

### Servicio esperado
Debe coordinar:
- paciente;
- profesional;
- cita opcional;
- nota breve;
- indicaciones breves;
- consistencia del flujo clínico-operativo.

## Módulo `cobros`

### Casos de uso esperados
- registrar cobro;
- consultar cobro;
- listar cobros;
- listar cobros por estado;
- listar cobros por fecha;
- listar cobros por usuario registrador;
- cambiar estado de cobro si el flujo lo requiere.

### Servicio esperado
Debe coordinar:
- existencia de atención previa;
- unicidad de cobro por atención;
- validación de monto y método;
- trazabilidad del usuario registrador.

## Módulo `reportes`

### Casos de uso esperados
- generar reporte PDF;
- generar reporte DOCX;
- generar reporte XLSX;
- construir datasets para reportes;
- filtrar reportes por profesional, paciente, fecha o estado según el caso.

### Servicio esperado
Puede dividirse entre:
- un servicio de armado de datos;
- un servicio o estrategia de generación por formato;
- una fachada de aplicación que expone el caso de uso al controller.

## Forma recomendada de los servicios

## Servicios de módulo
Conviene tener una clase principal por módulo que coordine operaciones frecuentes.

## Casos de uso específicos
Para flujos más delicados o con más pasos, conviene separarlos en clases explícitas.

Ejemplos razonables:
- `RegistrarPacienteUseCase`
- `CrearCitaUseCase`
- `RegistrarAtencionUseCase`
- `RegistrarCobroUseCase`
- `GenerarReportePdfUseCase`

No hace falta aplicarlo a absolutamente todo si no aporta claridad real.

## Qué no debe pasar en esta capa

- lógica de negocio fuerte en controller;
- consultas complejas repartidas arbitrariamente;
- servicios gigantes sin frontera;
- casos de uso duplicados entre módulos;
- mezcla de lógica de generación de reportes con autenticación o seguridad.

## Relación con repositorios

Los servicios y casos de uso sí pueden depender de repositorios, pero no deben convertirse en simples pasamanos de `save()` y `findById()`.

El valor de esta capa está en:
- coordinar pasos;
- decidir reglas;
- validar coherencia;
- componer resultados.

## Relación con DTOs

La capa de aplicación no debe quedar atada ciegamente al HTTP, pero en la práctica sí trabajará con request DTOs transformados o con comandos internos sencillos.

Lo importante es que:
- la entidad persistente no se exponga directamente;
- la respuesta final se prepare con intención clara;
- el mapper no quede olvidado ni mezclado con el controller.

## Resultado esperado

La organización por servicios y casos de uso debe hacer que el backend-consultorio sea entendible como sistema de negocio real: cada flujo importante debe poder rastrearse desde el endpoint hasta la persistencia sin sentir que todo sucede en una sola masa de código.

