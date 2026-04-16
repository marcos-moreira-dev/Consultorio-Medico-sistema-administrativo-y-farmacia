# DTO y modelos

## Propósito

Definir cómo se distinguen y se relacionan los DTOs, las entidades persistentes y otros modelos internos dentro de `backend-consultorio`, para evitar que la implementación mezcle responsabilidades y termine exponiendo estructuras incorrectas.

## Principio general

En este backend se debe separar con claridad:

- lo que entra por HTTP;
- lo que sale por HTTP;
- lo que vive en persistencia;
- lo que se usa internamente para coordinar lógica.

No todo objeto del sistema cumple el mismo papel, y parte de la calidad del backend depende de no confundir esas capas.

## Tipos de modelos que conviene distinguir

## 1. Request DTO

Representa la estructura de entrada esperada por un endpoint.

### Responsabilidad
- recibir datos del cliente;
- declarar validaciones de forma;
- servir como contrato explícito de entrada.

### Ejemplos
- `LoginRequestDto`
- `CrearPacienteRequestDto`
- `CrearCitaRequestDto`
- `RegistrarAtencionRequestDto`
- `RegistrarCobroRequestDto`

### No debe hacer
- contener lógica de negocio compleja;
- representar directamente la entidad JPA;
- llevar campos que el cliente no debería enviar.

## 2. Response DTO

Representa la estructura de salida que el backend decide exponer.

### Responsabilidad
- modelar la respuesta visible del recurso;
- proteger al cliente de detalles de persistencia innecesarios;
- soportar estabilidad del contrato.

### Ejemplos
- `UsuarioResponseDto`
- `ProfesionalResponseDto`
- `PacienteResponseDto`
- `CitaResponseDto`
- `AtencionResponseDto`
- `CobroResponseDto`

### No debe hacer
- exponer password_hash, flags internos o información sensible irrelevante;
- reflejar automáticamente todo el estado de la entidad persistente.

## 3. Entity persistente

Representa el modelo mapeado a base de datos.

### Responsabilidad
- sostener la relación con JPA y la persistencia relacional;
- representar el estado del dominio según la base diseñada.

### No debe hacer
- comportarse como response HTTP;
- convertirse en DTO por comodidad;
- filtrar directamente lo que el cliente ve.

## 4. Modelos internos o comandos de aplicación

Son objetos intermedios que pueden existir para coordinar lógica o encapsular criterios.

### Responsabilidad
- desacoplar la capa HTTP de la lógica de aplicación cuando haga falta;
- agrupar parámetros de un caso de uso;
- modelar filtros, criterios o decisiones internas.

### Ejemplos
- criterio de búsqueda;
- comando de generación de reporte;
- filtro de agenda por profesional y rango.

## Regla principal de separación

### Request DTO ≠ Entity
El cliente no debe construir directamente una entidad persistente.

### Response DTO ≠ Entity
La respuesta del backend no debe ser “la entidad serializada”.

### DTO ≠ modelo de seguridad
La identidad autenticada y el contexto de sesión deben tratarse con su propia intención, no como DTO de negocio mezclado con entidades.

## Organización sugerida por módulo

Cada módulo debe tener sus propios DTOs.

Ejemplo:
- `pacientes.dto`
- `citas.dto`
- `cobros.dto`

No conviene una carpeta global de DTOs gigantesca para todo el sistema.

## Ejemplos de tipos de DTOs por módulo

## `auth`
- login request;
- login response;
- sesión actual resumida si luego aplicara.

## `usuarios`
- crear usuario;
- actualizar estado de usuario;
- respuesta de usuario;
- respuesta resumida para listados.

## `profesionales`
- crear profesional;
- actualizar profesional;
- respuesta de profesional;
- resumen para selector o agenda.

## `pacientes`
- crear paciente;
- actualizar paciente;
- respuesta de paciente;
- respuesta resumida para listados.

## `citas`
- crear cita;
- cancelar cita;
- respuesta de cita;
- respuesta de agenda;
- filtro de agenda.

## `atenciones`
- registrar atención;
- respuesta de atención;
- resumen de atención para listados.

## `cobros`
- registrar cobro;
- respuesta de cobro;
- resumen de cobro para filtros administrativos.

## `reportes`
- request de generación;
- metadatos del reporte si hiciera falta;
- objetos internos de armado de dataset.

## Relación con mappers

Si los DTOs y las entidades se separan correctamente, conviene tener mappers o convertidores explícitos.

Los mappers deben encargarse de:
- transformar request DTO a comando o entidad parcial según el caso;
- transformar entity a response DTO;
- transformar agregados o composiciones a respuestas más ricas.

No conviene dejar toda esa traducción dentro del controller.

## Qué no debe pasar

- entity usada como request body;
- entity usada como response;
- DTO gigantesco que intenta servir para crear, actualizar, listar y detallar al mismo tiempo;
- request DTO con campos que en realidad pertenecen al backend decidir;
- response DTO que filtra más datos de los debidos por comodidad de serialización.

## DTOs y multidoctor

Como el sistema es multidoctor, los DTOs deben reflejar con claridad relaciones importantes como:

- `profesionalId`;
- nombres resumidos del profesional cuando sea útil en response;
- `usuarioId` o `registradoPorUsuarioId` en casos administrativos si aplica;
- datos resumidos del paciente en contextos de agenda o atención.

Pero siempre sin mezclar responsabilidades de forma confusa.

## Resultado esperado

El documento de DTOs y modelos debe dejar una disciplina clara de contratos y estructuras internas, de forma que el backend-consultorio no exponga mal su persistencia ni fuerce al frontend a depender de decisiones accidentales del ORM.

