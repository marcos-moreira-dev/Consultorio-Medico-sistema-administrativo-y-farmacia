# Entornos y perfiles: conceptos

## Propósito

Explicar qué son los entornos y perfiles de ejecución, por qué existen y cómo deben entenderse tanto conceptualmente como dentro de este proyecto.

## ¿Qué es un entorno?

Un entorno es un contexto de ejecución del sistema. Cambia cómo se comporta el software sin cambiar necesariamente su código fuente.

## Ejemplos típicos

- `local`
- `demo`
- `prod`

## ¿Qué es un perfil?

Un perfil es una forma concreta de activar una variante de configuración dentro de un entorno o para un objetivo específico.

En la práctica, muchas herramientas y frameworks usan “perfil” para cargar configuraciones distintas.

## ¿Por qué separar entornos?

Porque no conviene usar la misma configuración para:

- estudiar en la laptop;
- mostrar una demo a un cliente;
- correr un sistema real.

Cada entorno cambia cosas como:

- variables de entorno;
- credenciales;
- URLs;
- logs;
- datos demo o reales;
- políticas de seguridad;
- comportamiento de Docker o Compose.

## Entorno `local`

### Idea conceptual
Entorno para desarrollo, estudio y pruebas manuales.

### Suele tener
- configuración flexible;
- logs más verbosos;
- datos demo locales;
- servicios corriendo en la propia máquina o con Docker opcional;
- mayor tolerancia al debug.

## Entorno `demo`

### Idea conceptual
Entorno para mostrar el sistema de forma más limpia y controlada.

### Suele tener
- datos demo preparados;
- branding visible;
- menos ruido de debug;
- configuración más estable que local;
- comportamiento más cercano a producción, pero sin datos reales.

## Entorno `prod`

### Idea conceptual
Entorno real de operación.

### Suele tener
- secretos fuera del repo;
- logs más controlados;
- datos reales;
- mayor seguridad;
- menor tolerancia a improvisación;
- configuración estricta.

## Cómo aterriza esto en nuestro proyecto

### `backend-consultorio`
Usará perfiles tipo `dev`, `prod` y eventualmente `test`, además de la lógica global `local/demo/prod`.

### `backend-farmacia`
Usará variables y configuración por entorno propias del stack Nest.

### `desktop-consultorio-javafx`
No necesita “perfiles” tan pesados como un backend, pero sí debe poder apuntar a distintos endpoints o configuraciones base.

### `storefront-farmacia-angular`
Puede cambiar endpoints, branding o flags según entorno.

### Bases de datos
Deben diferenciar claramente datos demo, semillas de estudio y operación real.

## Idea reutilizable para la vida real

Esta separación sirve más allá de este proyecto. En cualquier sistema profesional conviene preguntarse:

- ¿esto es para desarrollar?
- ¿esto es para demostrar?
- ¿esto es para operar de verdad?

Y a partir de eso definir:

- configuración;
- datos;
- secretos;
- logging;
- Docker;
- despliegue.

## Resultado esperado

Este documento debe ayudarte a pensar entornos y perfiles no solo como archivos de configuración, sino como una forma profesional de separar contextos de ejecución para no mezclar estudio, demo y producción.