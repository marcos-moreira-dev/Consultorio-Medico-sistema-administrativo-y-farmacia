# Docker y Compose en el proyecto

## Propósito

Explicar para qué se usarán Docker y Docker Compose en este proyecto, qué papel cumplen en `local`, `demo` y `prod`, y cómo deben entenderse dentro de la arquitectura general.

## Principio general

Docker y Compose no existen aquí para complicar el proyecto, sino para volverlo más reproducible, portable y demostrable.

## ¿Para qué sí usar Docker en este proyecto?

### 1. Reproducibilidad
Permitir levantar servicios sin depender demasiado de configuraciones manuales locales.

### 2. Demo controlada
Facilitar una versión demostrable del sistema con menos fricción.

### 3. Coordinación de componentes
Ayudar a levantar backend, bases de datos y, cuando tenga sentido, otros servicios relacionados.

## ¿Para qué no usar Docker por deporte?

No conviene meter Docker en todo solo por “verse profesional” si eso entorpece el aprendizaje. Debe usarse donde realmente aporte.

## Rol de `docker compose`

`docker compose` sirve como orquestador sencillo de varios servicios del proyecto.

### Ejemplos razonables
- base de datos de consultorio;
- base de datos de farmacia;
- backend-consultorio;
- backend-farmacia;
- servicios auxiliares si luego aparecen.

## Relación con entornos

## `local`
Docker puede ser opcional, especialmente para quien quiera aprender primero de forma más manual.

## `demo`
Docker Compose sí tiene mucho sentido para dejar el sistema más fácil de mostrar y repetir.

## `prod`
Docker puede seguir teniendo sentido, pero la configuración debe ser más estricta y no improvisada.

## ¿Qué no hace Docker por sí solo?

Docker no reemplaza:
- la arquitectura;
- la documentación;
- la configuración por entorno;
- la seguridad real;
- la separación entre datos demo y datos reales.

## Ubicación recomendada en el repo

### Infraestructura
- `infra/compose/`
- `infra/docker/`
- `infra/scripts/`

Así se mantiene separada la infraestructura compartida del código de cada componente.

## Qué podría dockerizarse primero

### Prioridad razonable
1. bases de datos;
2. backend-consultorio;
3. backend-farmacia;
4. otros elementos más adelante.

### Menor prioridad inicial
- desktop JavaFX;
- frontend Angular público, si primero quieres estudiarlo más en modo local tradicional.

## Relación con `run_all.bat`

El script raíz puede apoyarse en Docker o Compose cuando exista una versión de arranque integrada. No debe esconder mágicamente toda la complejidad, pero sí facilitar el onboarding.

## Qué evitar

- meter Compose sin explicar qué levanta cada servicio;
- usar la misma configuración de Compose para todos los entornos sin distinguir contexto;
- asumir que Docker resuelve automáticamente secretos, seguridad y arquitectura.

## Resultado esperado

Este documento debe dejar claro que Docker y Compose son herramientas de apoyo para reproducibilidad y demo, no fines en sí mismos, y que su uso debe alinearse con el aprendizaje y la evolución real del proyecto.