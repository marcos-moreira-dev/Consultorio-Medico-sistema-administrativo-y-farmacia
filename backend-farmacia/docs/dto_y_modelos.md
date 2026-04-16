# DTO y modelos

## Propósito

Definir cómo se distinguen y se relacionan los DTOs, las entidades persistentes y otros modelos internos dentro de `backend-farmacia`, para evitar que la implementación mezcle responsabilidades y termine exponiendo estructuras incorrectas.

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
- `LoginAdminRequestDto`
- `CrearCategoriaRequestDto`
- `CrearProductoRequestDto`
- `ActualizarDisponibilidadRequestDto`
- `SubirImagenProductoRequestDto` o equivalente

### No debe hacer
- contener lógica de negocio compleja;
- representar directamente la entidad TypeORM;
- llevar campos que el cliente no debería enviar.

## 2. Response DTO

Representa la estructura de salida que el backend decide exponer.

### Responsabilidad
- modelar la respuesta visible del recurso;
- proteger al cliente de detalles de persistencia innecesarios;
- soportar estabilidad del contrato.

### Ejemplos
- `CategoriaResponseDto`
- `ProductoAdminResponseDto`
- `ProductoPublicoResponseDto`
- `MediaResponseDto`
- `DisponibilidadResponseDto`

### No debe hacer
- exponer rutas internas del filesystem;
- reflejar automáticamente todo el estado de la entidad persistente;
- mezclar información admin en contratos públicos.

## 3. Entity persistente

Representa el modelo mapeado a base de datos.

### Responsabilidad
- sostener la relación con TypeORM y la persistencia relacional;
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
- criterio de búsqueda pública;
- comando de publicación;
- filtro de catálogo;
- objeto de asociación imagen-producto.

## Regla principal de separación

### Request DTO ≠ Entity
El cliente no debe construir directamente una entidad persistente.

### Response DTO ≠ Entity
La respuesta del backend no debe ser “la entidad serializada”.

### DTO público ≠ DTO admin
La superficie pública y la administrativa no deben exponer exactamente la misma proyección del producto si no corresponde.

## Organización sugerida por módulo

Cada módulo debe tener sus propios DTOs.

Ejemplo:
- `categorias.dto`
- `productos.dto`
- `catalogo-publico.dto`
- `media.dto`

No conviene una carpeta global de DTOs gigantesca para todo el sistema.

## Ejemplos de tipos de DTOs por módulo

## `auth-admin`
- login request;
- login response;
- sesión actual resumida si luego aplicara.

## `categorias`
- crear categoría;
- actualizar categoría;
- respuesta de categoría;
- respuesta resumida para listados.

## `productos`
- crear producto;
- actualizar producto;
- respuesta admin del producto;
- respuesta resumida para listados admin.

## `media`
- metadata de archivo;
- respuesta de imagen asociada;
- DTO de asociación o reemplazo de imagen.

## `catalogo-publico`
- respuesta pública del producto;
- respuesta resumida del catálogo;
- filtros de búsqueda pública.

## `disponibilidad-publicacion`
- request de publicación;
- request de cambio de disponibilidad;
- response resumida del estado público del producto.

## `reservas` para V1.1
- request de reserva;
- response de reserva;
- filtros por estado o producto.

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
- response DTO pública que filtra información admin por comodidad.

## DTOs y media

Como el sistema sirve imágenes desde backend, los DTOs deben reflejar eso con claridad:

- `imagenUrl` o equivalente en contratos públicos;
- metadata razonable en contratos admin;
- jamás binario pesado embebido en JSON salvo un caso excepcional muy justificado.

## Resultado esperado

El documento de DTOs y modelos debe dejar una disciplina clara de contratos y estructuras internas, de forma que el backend-farmacia no exponga mal su persistencia ni mezcle la frontera pública y administrativa por accidente.

