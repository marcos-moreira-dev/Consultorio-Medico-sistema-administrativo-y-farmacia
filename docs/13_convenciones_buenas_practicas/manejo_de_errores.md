# Manejo de errores

## Propósito

Definir cómo deben tratarse los errores del sistema para mantener coherencia entre dominio, backend, clientes y diagnóstico técnico.

## Principio general

Un error bien tratado informa, protege y ayuda a diagnosticar. Un error mal tratado confunde, filtra información indebida o rompe la experiencia del sistema.

## Tipos de error a distinguir

### 1. Error de validación
Ocurre cuando la entrada no cumple requisitos mínimos.

### 2. Error de regla de negocio
Ocurre cuando la operación es formalmente válida, pero contradice una regla del dominio.

### 3. Error de autorización o acceso
Ocurre cuando el usuario no tiene permiso suficiente.

### 4. Error técnico inesperado
Ocurre cuando falla infraestructura, persistencia o aparece una excepción no prevista.

## Reglas de manejo

### ERR-001. No mezclar todos los errores en una sola categoría
La diferencia entre validación, negocio y fallo técnico debe verse en el tratamiento y en la respuesta.

### ERR-002. Los errores externos deben ser uniformes
Las respuestas de error hacia clientes deben seguir una convención estable del proyecto.

### ERR-003. Los errores no deben filtrar datos sensibles
El detalle técnico útil para diagnóstico interno no debe convertirse en exposición indebida hacia el cliente.

### ERR-004. Todo error relevante debe dejar rastro diagnóstico suficiente
El backend debe registrar contexto mínimo útil para investigar.

## Tratamiento esperado por capa

### Dominio
Expresa violaciones de reglas y estados incompatibles.

### Aplicación
Coordina y traduce fallos de dominio o infraestructura según el caso.

### Infraestructura
Registra y encapsula fallos técnicos de persistencia, red o servicios.

### Cliente
Muestra mensajes claros y adecuados al usuario, sin reemplazar la lógica real de validación o autorización.

## Resultado esperado

El manejo de errores debe hacer que el sistema sea más robusto, entendible y seguro, evitando respuestas improvisadas y mejorando la experiencia de uso y depuración.