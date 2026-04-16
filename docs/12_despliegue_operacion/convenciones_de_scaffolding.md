# Convenciones de scaffolding

## Propósito

Definir qué se crea manualmente en el esqueleto inicial del proyecto, qué debe generarse con herramientas oficiales y qué no debe formar parte del scaffolding inicial.

## Principio general

El scaffolding debe crear el **esqueleto humano-editable** del proyecto, no la basura generada por build ni artefactos que las herramientas oficiales producirán luego.

## Qué sí se crea manualmente

- carpetas del árbol canónico del repo;
- archivos de documentación;
- archivos de onboarding;
- `.gitignore`, `.gitattributes`, `.editorconfig`;
- plantillas como `.env.example`;
- `README.md`;
- `run_all.bat`;
- algunos archivos de configuración que luego se editarán manualmente.

## Qué puede quedar vacío o comentado al inicio

- `run_all.bat`;
- algunos `README.md` locales;
- plantillas de entorno;
- archivos de configuración que aún no tengan valores definitivos;
- archivos de código base con comentarios de intención.

## Qué debe generarse con herramientas oficiales

### Maven / Spring Boot / JavaFX
- wrapper real de Maven cuando corresponda;
- parte del proyecto Maven si decides generarla con herramienta oficial.

### Angular CLI
- archivos y estructura que el CLI de Angular produzca correctamente.

### Nest CLI
- estructura base del proyecto Nest y metadatos asociados.

### TypeORM / Flyway
- migraciones reales cuando ya exista el contexto técnico suficiente.

## Qué no debe crearse manualmente

- `node_modules/`
- `dist/`
- `build/`
- `target/`
- `.angular/`
- caches;
- lockfiles falsos;
- binarios generados;
- wrappers binarios inventados;
- artefactos compilados.

## Relación con `.gitignore`

Todo lo generado automáticamente y no versionable debe quedar cubierto por `.gitignore`.

## Regla para comentarios iniciales en archivos de código

Si se crean archivos fuente vacíos en una fase posterior, se pueden dejar comentarios mínimos que expliquen:

- intención de la clase o módulo;
- paquete o namespace esperado;
- responsabilidad general.

No conviene llenar cada archivo con comentarios enormes vacíos de valor.

## Regla para scripts

Los scripts iniciales pueden existir aunque al principio solo contengan:

- encabezado;
- comentario de propósito;
- `TODO` claros;
- estructura mínima de arranque.

## Resultado esperado

Estas convenciones deben evitar que el scaffolding inicial cree ruido innecesario y deben hacer que el proyecto nazca limpio, editable y listo para evolucionar con herramientas reales.