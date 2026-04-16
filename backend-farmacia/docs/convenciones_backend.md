# Convenciones backend

## Propósito

Definir reglas de implementación y consistencia interna para `backend-farmacia`, de forma que el proyecto se mantenga legible, predecible y profesional incluso cuando crezca en módulos, endpoints y lógica.

## Principio general

Las convenciones no existen para decorar documentos. Existen para reducir fricción, evitar decisiones arbitrarias y hacer que el backend se sienta como un solo producto coherente y no como piezas ensambladas con estilos distintos.

## Convenciones de naming

### Clases
Usar nombres claros y explícitos en PascalCase.

### DTOs
Nombrar con intención de entrada o salida.

Ejemplos:
- `CrearProductoRequestDto`
- `ProductoPublicoResponseDto`
- `ProductoAdminResponseDto`
- `PageResponseDto`

### Services
Usar nombres orientados a módulo o caso de uso.

Ejemplos:
- `ProductoService`
- `PublicarProductoUseCase`
- `SubirImagenProductoUseCase`

### Entities
Nombrar según el concepto del dominio.

Ejemplos:
- `CategoriaEntity`
- `ProductoEntity`
- `ReservaEntity` cuando llegue V1.1

### Repositories
Usar el sufijo `Repository` o el estilo equivalente claramente reconocible dentro de Nest + TypeORM.

### Controllers
Usar el sufijo `Controller`.

## Convenciones de endpoints

- usar sustantivos consistentes;
- respetar el versionado `/api/v1/...`;
- separar visualmente lo público y lo admin;
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

### TSDoc o equivalente
Debe usarse en:
- clases públicas importantes;
- services;
- casos de uso;
- DTOs relevantes;
- enums;
- métodos públicos con lógica no trivial;
- módulos o piezas donde la intención no sea obvia.

### Qué no conviene documentar de forma absurda
No hace falta escribir comentarios vacíos para getters, setters o métodos obvios sin valor explicativo.

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
- mantener seguridad técnica separada del dominio comercial;
- mantener visible la diferencia entre piezas públicas y admin.

## Convenciones para media

- no incrustar binarios de imagen en DTOs JSON normales;
- exponer `imagenUrl` o metadata equivalente;
- no filtrar rutas internas del storage como si fueran contratos estables;
- mantener el manejo de archivos en un módulo formal.

## Convenciones de evolución

- V1.0 debe quedar clara y cerrada;
- V1.1 debe quedar documentada como evolución, no mezclada como si ya existiera;
- reservas pertenecen a V1.1 y deben tratarse como expansión futura explícita.

## Qué no debe pasar

- estilos distintos por módulo como si fueran proyectos separados;
- nombres crípticos;
- clases gigantes sin responsabilidad clara;
- comentarios basura que repiten obviedades;
- mezcla de inglés y español sin criterio.

## Resultado esperado

Las convenciones del backend deben hacer que `backend-farmacia` se vea y se sienta como una base profesional, homogénea y estudiable, lista para crecer sin convertirse en desorden.