# Convenciones backend

## Propósito

Definir reglas de implementación y consistencia interna para `backend-consultorio`, de forma que el proyecto se mantenga legible, predecible y profesional incluso cuando crezca en módulos, endpoints y lógica.

## Principio general

Las convenciones no existen para decorar documentos. Existen para reducir fricción, evitar decisiones arbitrarias y hacer que el backend se sienta como un solo producto coherente y no como piezas ensambladas con estilos distintos.

## Convenciones de naming

### Clases
Usar nombres claros y explícitos en PascalCase.

### DTOs
Nombrar con intención de entrada o salida.

Ejemplos:
- `CrearPacienteRequestDto`
- `PacienteResponseDto`
- `PageResponseDto`

### Services
Usar nombres orientados a módulo o caso de uso.

Ejemplos:
- `PacienteService`
- `RegistrarCobroUseCase`
- `GenerarReportePdfUseCase`

### Entities
Nombrar según el concepto del dominio.

Ejemplos:
- `PacienteEntity`
- `ProfesionalEntity`
- `CitaEntity`

### Repositories
Usar el sufijo `Repository` y lenguaje del dominio.

### Controllers
Usar el sufijo `Controller`.

## Convenciones de endpoints

- usar sustantivos consistentes;
- respetar el versionado `/api/v1/...`;
- no mezclar singular y plural sin criterio;
- no usar rutas demasiado verbosas si el verbo HTTP ya expresa la intención.

## Convenciones de capa

### Controller
Delgado, enfocado en HTTP.

### Service / Use Case
Coordina negocio y flujo.

### Repository
Acceso a persistencia.

### Mapper
Transformación entre entidad, DTO y modelos internos.

### Config / Security
Infraestructura transversal.

## Convenciones de documentación en código

### JavaDoc
Debe usarse en:
- clases públicas importantes;
- interfaces;
- servicios;
- casos de uso;
- DTOs relevantes;
- enums;
- métodos públicos con lógica no trivial.

### Qué no conviene documentar de forma absurda
No hace falta escribir JavaDoc vacío para getters, setters o métodos obvios sin valor explicativo.

### Objetivo de la documentación
Debe ayudar a estudiar el proyecto y a que otra IA entienda intención, no solo sintaxis.

## Convenciones de respuesta

Toda respuesta debe alinearse con la convención uniforme del proyecto.

No conviene que un módulo devuelva una estructura y otro módulo otra completamente distinta.

## Convenciones de error

Los errores deben seguir una estructura común y códigos consistentes.

## Convenciones de validación

- validación de forma en DTO o frontera HTTP;
- validación de negocio en servicios o casos de uso;
- no duplicar validaciones sin sentido en todas las capas.

## Convenciones de logging

- usar logs con intención;
- evitar spam;
- no loguear secretos ni datos sensibles innecesarios;
- incluir correlation_id donde corresponda.

## Convenciones de testing

- nombres de pruebas claros;
- foco en reglas relevantes;
- evitar tests triviales sin valor;
- priorizar integración donde aporte más que puro unit test vacío.

## Convenciones de paquetes

- priorizar módulo antes que tipo genérico;
- evitar carpetas globales gigantes de `dto`, `service` o `shared` si rompen legibilidad;
- mantener seguridad técnica separada del dominio clínico.

## Qué no debe pasar

- estilos distintos por módulo como si fueran proyectos separados;
- nombres crípticos;
- clases gigantes sin responsabilidad clara;
- comentarios basura que repiten obviedades;
- mezcla de inglés y español sin criterio.

## Resultado esperado

Las convenciones del backend deben hacer que `backend-consultorio` se vea y se sienta como una base profesional, homogénea y estudiable, lista para crecer sin convertirse en desorden.