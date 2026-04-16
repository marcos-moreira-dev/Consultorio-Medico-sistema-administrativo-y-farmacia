# Plantilla componente

## Nombre del componente

Indicar el nombre del componente de forma clara.

**Ejemplos:**
- `Backend consultorio`
- `Storefront farmacia`
- `Módulo de disponibilidad`

## Tipo de componente

Especificar qué clase de pieza es.

**Ejemplos:**
- backend;
- cliente desktop;
- frontend web;
- módulo funcional;
- componente transversal;
- servicio interno.

## Propósito

Explicar qué responsabilidad principal cumple dentro del sistema.

## Contexto o superficie a la que pertenece

Indicar si forma parte de:

- consultorio privado;
- farmacia administrativa;
- farmacia pública;
- infraestructura transversal.

## Responsabilidades

Listar lo que sí le corresponde hacer.

## No responsabilidades

Listar explícitamente lo que no debe hacer para evitar mezclar fronteras.

## Entradas o contratos consumidos

Describir qué recibe o consume el componente.

**Ejemplos:**
- endpoints;
- DTOs;
- eventos;
- configuración;
- acceso a persistencia.

## Salidas o contratos expuestos

Describir qué entrega o publica hacia otros componentes.

## Dependencias principales

Indicar de qué otros componentes o servicios depende para funcionar.

## Riesgos o tensiones de diseño

Anotar riesgos relevantes de este componente.

**Ejemplos:**
- mezcla de lógica;
- exposición indebida;
- acoplamiento fuerte;
- crecimiento desordenado.

## Consideraciones de seguridad y privacidad

Indicar si maneja información sensible, superficies públicas o controles de acceso particulares.

## Consideraciones de trazabilidad y operación

Anotar qué hechos conviene registrar o qué soporte operativo necesita.

## Evolución esperable

Si aplica, describir cómo podría crecer este componente en V1.1 o más adelante.

## Relación con otros documentos

Listar documentos del proyecto que se conectan especialmente con este componente.

## Nota de uso

Esta plantilla sirve para documentar componentes con enfoque de responsabilidad y frontera. Debe ayudar a entender qué lugar ocupa la pieza dentro del sistema y qué compromisos arrastra, no solo a describirla superficialmente.