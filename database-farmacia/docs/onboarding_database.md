# Onboarding database

## Propósito

Explicar cómo debe incorporarse alguien a `database-farmacia`, qué necesita entender primero, por dónde debe empezar a leer y cómo se relacionan las distintas fases de diseño y evolución de la base de datos.

## Principio general

Esta carpeta no es solo un conjunto de SQL de catálogo. Es una base documental y técnica para estudiar y evolucionar la persistencia de la farmacia pública y administrativa de forma ordenada.

## Qué debe entender primero una persona nueva

### 1. Hay dos niveles de trabajo
- `Design V1` para el proceso de diseño y normalización
- `V2` para la estructura más formal y evolucionada

### 2. La base de datos de farmacia es un componente propio
No debe mezclarse con consultorio ni con el storefront más allá de sus contratos públicos.

### 3. Aquí conviven estudio y preparación técnica
Hay material pensado tanto para aprender como para servir de referencia a backend y evolución futura.

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
Zona de estudio y diseño previo del catálogo y sus relaciones.

### `V2/schema`
Esquema más estable de la base.

### `V2/seeds`
Datos semilla para estudio, demo o apoyo.

### `V2/migrations/flyway`
Lugar posible para migraciones SQL documentales, aunque el runtime de evolución podría terminar concentrándose en el backend de farmacia.

### `V2/tools`
Scripts de apoyo, bootstrap o consulta.

## Qué debe ubicar rápido una persona nueva

- el modelo conceptual del catálogo;
- el SQL inicial en 1FN;
- el informe que explica la normalización;
- el schema 3FN o equivalente más estable;
- los seeds demo;
- la relación entre diseño, SQL de referencia y evolución futura.

## Regla práctica

### Primero comprender el catálogo
Antes de hablar de imágenes, disponibilidad o publicación desde backend, conviene entender bien la persistencia base.

### Luego comprender la frontera con backend
La base documenta y sostiene el dominio, pero no reemplaza la lógica de `backend-farmacia`.

## Resultado esperado

Este onboarding debe permitir que una persona nueva entre a `database-farmacia` y entienda rápidamente qué estudiar primero, qué representa cada carpeta y cómo se conecta el diseño inicial con la persistencia más formal del proyecto.