# Testing backend

## Propósito

Definir qué debe probarse en `backend-consultorio`, con qué criterio y en qué profundidad razonable para una V1 privada, administrativa y suficientemente realista.

## Principio general

El testing de este backend no debe consistir en “probar por probar” ni limitarse a comprobar que el contexto de Spring arranca. Debe proteger los flujos esenciales del negocio, las reglas críticas y la frontera de seguridad.

## Qué se busca con el testing en esta V1

### 1. Proteger reglas de negocio clave
Especialmente las relacionadas con citas, atenciones, cobros, usuarios internos y permisos.

### 2. Evitar regresiones en contratos
Los endpoints privados deben devolver respuestas coherentes, errores consistentes y validaciones previsibles.

### 3. Dar confianza a seguridad y autorización
No basta con que exista JWT. Debe verificarse qué puede hacer cada rol.

### 4. Servir como material de estudio
El proyecto también debe enseñar qué conviene probar en un backend administrativo serio.

## Niveles de prueba recomendados

## 1. Unit tests

### Qué sí conviene probar
- validadores con lógica real;
- reglas de dominio no triviales;
- utilidades compartidas que transforman o verifican datos;
- mapeos o adaptaciones con comportamiento relevante.

### Qué no conviene inflar
No hace falta llenar el proyecto de tests cosméticos sobre getters, setters o clases sin lógica.

## 2. Integration tests

### Objetivo
Comprobar colaboración real entre capas.

### Casos importantes
- controller + seguridad + servicio + repositorio;
- persistencia de entidades clave;
- validaciones de negocio que dependen de BD;
- filtros, paginación y ordenamiento;
- generación o solicitud de reportes si aplica.

## 3. Security tests

### Objetivo
Verificar que la seguridad no sea solo declarativa.

### Casos importantes
- acceso denegado sin token;
- acceso denegado con rol incorrecto;
- acceso permitido con rol correcto;
- endpoints públicos inexistentes o bien cerrados;
- manejo consistente de `401` y `403`.

## Módulos o áreas que conviene cubrir sí o sí

## `auth`
- login válido;
- login inválido;
- emisión de token;
- rechazo de credenciales incorrectas.

## `pacientes`
- alta válida;
- validación de datos requeridos;
- búsqueda y listado;
- consulta por identificador.

## `profesionales`
- alta y edición básica;
- activación e inactivación;
- consistencia de asociación con usuario si aplica.

## `citas`
- creación válida;
- rechazo por conflictos o reglas temporales;
- filtros por profesional o fecha;
- cancelación o cambio de estado si el diseño lo contempla.

## `atenciones`
- registro correcto;
- coherencia con cita previa o atención directa;
- validación de datos mínimos clínico-operativos.

## `cobros`
- registro de cobro;
- rechazo de importes o estados inválidos;
- relación con atención o flujo operativo que corresponda.

## `reportes`
- solicitud válida;
- validación de parámetros;
- protección por rol;
- manejo de error cuando el reporte falla.

## Qué no debe pasar

- tests que dependen del orden casual de ejecución;
- pruebas excesivamente acopladas a implementación interna;
- cero pruebas sobre seguridad;
- cero pruebas sobre reglas temporales o de consistencia;
- confiar únicamente en pruebas manuales.

## Cobertura razonable para V1

No hace falta obsesionarse con un porcentaje mágico. La prioridad es cubrir lo que más duele si se rompe:

- seguridad;
- contratos HTTP;
- reglas de negocio principales;
- filtros, validaciones y errores;
- persistencia básica de módulos núcleo.

## Resultado esperado

La estrategia de testing del backend debe dejar un sistema privado con una base de confianza razonable, capaz de crecer sin degradarse y suficientemente claro como para servir también como proyecto de estudio serio.
