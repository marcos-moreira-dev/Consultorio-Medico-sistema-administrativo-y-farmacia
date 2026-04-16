# Logging y observabilidad

## Propósito

Definir cómo debe pensarse el logging del sistema y qué significa observabilidad práctica dentro del alcance de esta V1.

## Principio general

La observabilidad en este proyecto no busca replicar una plataforma corporativa compleja. Busca algo más concreto: que el sistema sea entendible cuando funciona, cuando falla y cuando evoluciona.

El logging debe ayudar a responder preguntas como:

- qué ocurrió;
- en qué componente ocurrió;
- a quién afectó;
- si la operación fue exitosa o falló;
- con qué contexto mínimo se puede diagnosticar.

## Objetivos del logging

### 1. Diagnóstico técnico
Permitir entender errores, flujos rotos y validaciones fallidas.

### 2. Seguimiento operativo
Permitir seguir hechos relevantes del negocio sin depender de memoria manual.

### 3. Soporte a mantenimiento y evolución
Facilitar refactorización, pruebas y análisis de incidentes durante V1.0 y V1.1.

## Niveles de log sugeridos

### DEBUG
Para detalle técnico útil en desarrollo o depuración local.

**Ejemplos:**
- entrada y salida resumida de un caso de uso;
- paso por validaciones importantes;
- información diagnóstica de integración.

### INFO
Para hechos normales relevantes del sistema.

**Ejemplos:**
- inicio de aplicación;
- operación de negocio completada;
- publicación de producto;
- atención registrada.

### WARN
Para situaciones anómalas pero controladas.

**Ejemplos:**
- intento de duplicado;
- acceso denegado;
- datos incompletos tolerados;
- operación parcialmente válida.

### ERROR
Para fallos que impiden completar una operación o reflejan comportamiento inesperado.

**Ejemplos:**
- error inesperado en backend;
- fallo de persistencia;
- excepción no controlada.

## Qué debe loguearse

### Hechos operativos relevantes
- creación y cambio de estado de entidades importantes;
- operaciones exitosas de negocio con valor diagnóstico;
- rechazos por reglas de negocio;
- accesos denegados.

### Hechos técnicos relevantes
- errores inesperados;
- problemas de integración;
- fallos de migración o arranque;
- cambios relevantes de entorno o servicio.

## Qué no debe loguearse indiscriminadamente

- datos sensibles completos de pacientes;
- notas clínicas extensas;
- credenciales o secretos;
- cuerpos de error que filtren información innecesaria;
- ruido repetitivo sin valor real.

## Observabilidad práctica para esta V1

En el alcance de esta V1, observabilidad significa como mínimo:

- logs claros por componente;
- mensajes útiles y consistentes;
- posibilidad de correlacionar operaciones;
- errores distinguibles entre validación, negocio y fallo inesperado;
- registro suficiente para entender un flujo sin mirar toda la base de datos a mano.

## Criterios de calidad del log

Un buen log en este proyecto debe ser:

- breve, pero útil;
- contextual, pero sin exceso sensible;
- consistente en estructura;
- interpretable por una persona que está depurando el sistema horas o días después.

## Resultado esperado

El logging y la observabilidad deben permitir que el sistema sea más legible como producto técnico: menos caja negra, más capacidad de entender el comportamiento real del software sin caer en sobreinstrumentación.