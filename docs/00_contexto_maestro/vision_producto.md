# Visión del producto

## Identidad del proyecto

Este proyecto representa un sistema administrativo híbrido para un consultorio médico pequeño con farmacia asociada en Guayaquil. Su construcción tiene un propósito deliberadamente dual: por un lado, modelar un negocio plausible con suficiente fidelidad operativa; por otro, funcionar como una plataforma de aprendizaje serio para estudiar diseño de dominio, bases de datos, arquitectura, backend, frontend y documentación profesional.

No se busca construir un software de juguete ni un sistema exageradamente complejo. Se busca una solución creíble, modular y bien pensada, capaz de transmitir oficio técnico.

## Problema que resuelve

En negocios pequeños de salud y venta de medicamentos, gran parte de la operación suele sostenerse con memoria, cuadernos, mensajes informales, hojas dispersas o herramientas genéricas. Eso produce varios problemas:

- citas desordenadas o poco trazables;
- información de pacientes difícil de localizar;
- cobros poco estructurados;
- baja claridad sobre disponibilidad de productos;
- escasa separación entre información privada y superficie pública;
- dependencia excesiva del conocimiento informal de una sola persona.

El producto propone ordenar esa operación con una base digital sobria y realista, sin exigir una infraestructura desproporcionada para un negocio pequeño.

## Propuesta de valor

La propuesta de valor del sistema es simple y fuerte:

- ayudar a registrar y consultar la operación básica del consultorio;
- facilitar la administración mínima de la farmacia;
- ofrecer una superficie pública controlada para explorar productos;
- separar correctamente lo privado de lo público;
- servir como base estable para crecimiento posterior.

## Tipo de negocio que se modela

El sistema está pensado para un negocio pequeño o mediano-bajo, de carácter barrial, con atención directa y procesos relativamente simples. Se asume un entorno donde la prioridad no es la sofisticación tecnológica, sino la claridad, la rapidez operativa, la legibilidad y el orden.

Esto implica que el diseño debe evitar dos extremos:

- un sistema demasiado pobre, que no aguanta casos reales;
- un sistema demasiado pesado, que parece hecho para una clínica grande.

## Objetivos principales

### Objetivo de producto
Construir una V1 sólida que cubra el núcleo útil del consultorio y la farmacia sin perder coherencia entre dominio, datos, interfaces y arquitectura.

### Objetivo de aprendizaje
Usar el proyecto para estudiar aplicaciones administrativas de forma integral, incluyendo:

- modelado de dominio con reglas y estados;
- diseño de base de datos relacional con evolución controlada;
- contratos API con DTOs y respuestas consistentes;
- separación por capas y responsabilidades;
- documentación transversal y por componente;
- evolución de esquema mediante migraciones.

### Objetivo de portafolio
Obtener una pieza profesional que pueda mostrarse a clientes como ejemplo serio de capacidad para analizar, estructurar y construir software de negocio.

## Filosofía de construcción

### Fidelidad operativa
El dominio debe ser suficientemente realista para evitar un modelo frágil. Lo importante no es saber medicina, sino modelar bien la operación administrativa y comercial que rodea al negocio.

### Modularidad
Consultorio y farmacia comparten contexto, pero no deben mezclarse sin criterio. La arquitectura, la base de datos y los contratos deben reflejar esa separación.

### Sobriedad
La experiencia de usuario debe ser formal, clara y legible. El producto no se diseña para deslumbrar visualmente, sino para transmitir control, orden y utilidad.

### Evolución disciplinada
La V1 no se entenderá como un bloque inmóvil. Se fija una evolución interna V1.0 y V1.1 para permitir crecimiento funcional razonable y aprendizaje real de migraciones y mantenimiento.

## Superficies del producto

### Superficie privada del consultorio
Incluye pacientes, citas, atención, notas operativas, indicaciones breves y cobro de consulta. Esta superficie debe proteger datos sensibles y estar disponible solo para personal autorizado.

### Superficie pública o semipública de farmacia
Incluye catálogo, búsqueda de productos, detalle básico, disponibilidad y visibilidad controlada. Esta superficie puede exponerse con mucha más libertad que la parte clínica.

### Superficie administrativa de farmacia
Incluye mantenimiento de productos, control básico de disponibilidad y acciones necesarias para sostener la capa pública.

## Resultado esperado de la visión

Al terminar la V1, el proyecto debe sentirse como una solución administrativa plausible para un negocio pequeño real, y al mismo tiempo como una base académica y profesional que te permita estudiar, refactorizar, extender y explicar el sistema con criterio.