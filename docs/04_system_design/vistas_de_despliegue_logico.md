# Vistas de despliegue lógico

## Propósito

Proponer cómo debe pensarse el despliegue lógico del sistema en la V1, diferenciando entre separación conceptual fuerte y complejidad física razonable.

## Principio general

El proyecto necesita una arquitectura clara, pero no una infraestructura exagerada para demostrar valor. Por eso, en esta etapa se prioriza un despliegue lógico ordenado y defendible sobre una topología distribuida compleja.

## Vista lógica base

A nivel lógico, el sistema se entiende como un conjunto de seis piezas principales:

- `database-consultorio`;
- `database-farmacia`;
- `backend-consultorio`;
- `backend-farmacia`;
- `desktop-consultorio-javafx`;
- `storefront-farmacia-angular`.

Estas piezas pueden coexistir en un entorno local de demostración sin perder sus fronteras lógicas.

## Separación lógica esperada

### Desktop consultorio
Se ejecuta como cliente interno y consume el backend consultorio.

### Backend consultorio
Se ejecuta como servicio privado o local protegido, con acceso exclusivo a `database-consultorio`.

### Database consultorio
Se ejecuta como persistencia propia del consultorio y no debe ser accedida por clientes ni por backend farmacia.

### Storefront farmacia
Se ejecuta como aplicación web pública o semipública, separada visual y funcionalmente de la herramienta del consultorio.

### Backend farmacia
Se ejecuta como servicio que atiende tanto la administración interna como la capa pública controlada del catálogo, con acceso exclusivo a `database-farmacia`.

### Database farmacia
Se ejecuta como persistencia propia de la farmacia y no debe ser accedida por clientes ni por backend consultorio.

## Escenario lógico de despliegue para demo local

En una demo local razonable, se puede asumir lo siguiente:

- `database-consultorio` en entorno local;
- `database-farmacia` en entorno local;
- backend consultorio en ejecución local;
- backend farmacia en ejecución local;
- cliente desktop consultorio ejecutándose como aplicación local;
- storefront farmacia ejecutándose localmente como frontend web conectado al backend farmacia.

Esta topología es suficiente para demostrar:

- separación entre contextos;
- separación de persistencia;
- contratos entre clientes y backends;
- coexistencia de superficies pública y privada.

## Escenario lógico de crecimiento posterior

Sin cambiar la arquitectura conceptual, el sistema podría evolucionar a una disposición más separada, por ejemplo:

- storefront desplegado aparte;
- backends empaquetados independientemente;
- cada base en su propio contenedor o instancia dedicada;
- acceso diferenciado por red interna o entornos de despliegue.

No es obligatorio en esta V1, pero la arquitectura debe permitirlo.

## Restricciones de despliegue lógico

- el desktop no debe saltarse el backend;
- la capa pública no debe consultar directamente estructuras privadas;
- ninguna base de datos debe tratarse como interfaz pública;
- la simplicidad de la demo no debe destruir las fronteras del sistema;
- backend consultorio y backend farmacia no deben depender de acceso técnico directo entre sus bases.

## Beneficios del enfoque elegido

- menor complejidad operativa inicial;
- mayor facilidad de estudio y depuración;
- coherencia con una V1 orientada a aprendizaje y portafolio;
- capacidad de explicar el sistema con claridad;
- mejor aislamiento entre persistencia clínica y persistencia comercial.

## Resultado esperado

La vista de despliegue lógico debe mostrar que el proyecto puede ser simple de ejecutar y al mismo tiempo serio en su separación interna, permitiendo evolución futura sin traicionar el diseño original ni volver a una sola persistencia compartida.