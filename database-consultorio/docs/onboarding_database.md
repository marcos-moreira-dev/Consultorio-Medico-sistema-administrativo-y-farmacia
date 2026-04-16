# Onboarding database

## Propósito

Explicar cómo debe incorporarse alguien a `database-consultorio`, qué necesita entender primero, por dónde debe empezar a leer y cómo se relacionan las distintas fases de diseño y evolución de la base de datos.

## Principio general

Esta carpeta no es solo una colección de SQL sueltos. Es una base documental y técnica para estudiar y evolucionar la persistencia del consultorio de forma ordenada.

## Qué debe entender primero una persona nueva

### 1. Hay dos niveles de trabajo
- `Design V1` para el proceso de diseño y normalización
- `V2` para la estructura más formal y evolucionada

### 2. La base de datos del consultorio es un componente propio
No debe mezclarse con farmacia ni con frontends.

### 3. Aquí conviven estudio y operatividad
Hay material pensado tanto para aprender como para preparar persistencia más profesional.

## Ruta sugerida de lectura

### Paso 1
Leer `README.md` y los docs locales de base de datos.

### Paso 2
Entrar a `Design V1` para entender el proceso:
- modelo conceptual;
- 1FN;
- informe de normalización.

### Paso 3
Entrar a `V2` para entender la estructura más formal:
- `schema/`
- `seeds/`
- `migrations/`
- `tools/`
- `views/`
- `routines/`
- `security/`

## Significado de las carpetas principales

### `Design V1`
Zona de estudio y diseño previo.

### `V2/schema`
Esquema más estable de la base.

### `V2/seeds`
Datos semilla de demostración o apoyo.

### `V2/migrations/flyway`
Lugar pensado para migraciones formales si esta carpeta queda como dueña canónica de migraciones.

### `V2/tools`
Scripts de apoyo o bootstrap.

## Qué debe ubicar rápido una persona nueva

- el modelo conceptual;
- el SQL inicial en 1FN;
- el informe que explica la normalización;
- el schema 3FN o equivalente más estable;
- los seeds demo;
- la relación entre diseño, migración y evolución.

## Regla práctica

### Primero comprender
No conviene empezar por ejecutar scripts al azar sin entender la evolución del diseño.

### Luego comparar fases
La fuerza pedagógica de esta carpeta está en ver cómo se pasa del diseño inicial a una estructura más sólida.

## Resultado esperado

Este onboarding debe permitir que una persona nueva entre a `database-consultorio` y entienda rápidamente qué estudiar primero, qué representa cada carpeta y cómo se conecta el diseño inicial con la persistencia más formal del proyecto.

