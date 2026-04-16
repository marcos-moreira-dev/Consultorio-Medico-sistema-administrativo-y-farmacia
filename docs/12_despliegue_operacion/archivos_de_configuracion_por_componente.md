# Archivos de configuración por componente

## Propósito

Dejar explícito qué archivos de configuración debe contemplar cada componente del proyecto, diferenciando entre configuración global, configuración por stack y archivos generados por herramientas.

## Principio general

No todo archivo importante es código fuente. Un proyecto profesional también vive de:

- archivos de proyecto;
- configuración de entorno;
- perfiles;
- wrappers;
- settings de build;
- plantillas de variables.

## Configuración global en la raíz

Archivos recomendados:

- `README.md`
- `.gitignore`
- `.gitattributes`
- `.editorconfig`
- `run_all.bat`

## Configuración global de apoyo

Rutas recomendadas:

- `config/env/`
- `config/profiles/`
- `infra/compose/`
- `infra/docker/`

## `backend-consultorio`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `.env.example`
- `pom.xml`
- `mvnw`
- `mvnw.cmd`
- `.mvn/wrapper/`
- `src/main/resources/application.yml`
- `src/main/resources/application-dev.yml`
- `src/main/resources/application-prod.yml`
- `src/test/resources/application-test.yml`

## `backend-farmacia`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `.env.example`
- `package.json`
- `tsconfig.json`
- `tsconfig.build.json`
- `nest-cli.json`

## `desktop-consultorio-javafx`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `pom.xml`
- `mvnw`
- `mvnw.cmd`
- `.mvn/wrapper/`
- `src/main/java/module-info.java`

## `storefront-farmacia-angular`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `.env.example`
- `package.json`
- `angular.json`
- `tsconfig.json`
- `tsconfig.app.json`
- `tsconfig.spec.json`

## `database-consultorio`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `docs/onboarding_database.md`
- scripts SQL de schema, seeds y migraciones según la fase

## `database-farmacia`

Archivos a contemplar:

- `README.md`
- `.gitignore`
- `docs/onboarding_database.md`
- scripts SQL de schema, seeds y migraciones según la fase

## Reglas importantes

### 1. `*.example` sí, secretos reales no
Las variables sensibles no deben subirse. Lo que sí conviene subir es la plantilla.

### 2. El archivo de proyecto es parte del scaffolding
`pom.xml`, `package.json`, `angular.json` y equivalentes sí deben contemplarse desde el inicio.

### 3. La configuración local por stack no debe ser tragada por la raíz
La raíz coordina. Cada componente conserva su configuración propia.

## Resultado esperado

Este documento debe servir como checklist de configuración inicial para que el proyecto nazca con archivos realmente importantes y no solo con carpetas vacías.