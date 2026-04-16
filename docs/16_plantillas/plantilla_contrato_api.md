# Plantilla contrato API

## Nombre del contrato

Indicar un nombre claro para el endpoint o contrato.

**Ejemplo:**
`Registrar paciente`

## Contexto

Indicar a qué superficie o subdominio pertenece.

**Ejemplos:**
- consultorio privado;
- farmacia administrativa;
- farmacia pública.

## Objetivo

Explicar qué operación resuelve este contrato y qué valor aporta al flujo.

## Método y ruta

Especificar el método HTTP y la ruta esperada.

**Ejemplo:**
`POST /api/consultorio/pacientes`

## Autenticación y autorización

Indicar si requiere autenticación y qué rol o nivel de acceso se espera.

## Request

### Descripción
Explicar qué datos recibe.

### DTO de entrada
Nombrar el request DTO esperado.

### Campos principales
Listar los campos relevantes y su sentido funcional.

### Validaciones
Indicar las validaciones mínimas que deben cumplirse.

## Response exitosa

### Descripción
Explicar qué devuelve cuando la operación sale bien.

### DTO de salida
Nombrar el response DTO esperado.

### Estructura general
Indicar si usa `ApiResponse<T>` u otra convención oficial del proyecto.

### Campos principales
Listar los campos visibles para el consumidor.

## Errores esperados

Distinguir errores por categoría cuando aplique:

- validación;
- regla de negocio;
- autorización;
- error inesperado.

### Formato de error
Indicar la convención usada para respuestas de error.

## Reglas de negocio relacionadas

Listar reglas que condicionan este contrato.

## Consideraciones de privacidad

Indicar si el contrato maneja datos sensibles o información publicable y qué límites de exposición tiene.

## Consideraciones de trazabilidad

Anotar qué eventos o logs relevantes debería generar este contrato.

## Impacto sobre consumidores

Indicar qué cliente o componente consume esta API y qué espera de ella.

## Evolución futura

Si aplica, anotar cómo podría cambiar en V1.1 o qué puntos conviene mantener estables.

## Nota de uso

Esta plantilla sirve para documentar contratos API de forma suficientemente precisa como para alinear backend, clientes, seguridad y pruebas. Debe evitar tanto la vaguedad excesiva como el detalle ceremonial sin utilidad real.