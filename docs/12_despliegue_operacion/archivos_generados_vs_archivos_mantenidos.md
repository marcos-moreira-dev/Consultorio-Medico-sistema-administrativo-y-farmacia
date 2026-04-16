# Archivos generados vs archivos mantenidos

## Propósito

Distinguir claramente entre archivos que forman parte del repositorio y del trabajo humano directo, y archivos que deben ser generados por herramientas, builds o procesos automáticos.

## Principio general

Un repo limpio no guarda todo. Guarda lo que debe versionarse y delega a herramientas lo que ellas deben producir.

## Archivos mantenidos por el proyecto

### Documentación
- markdowns de contexto;
- onboarding;
- README;
- ADRs;
- plantillas;
- checklists.

### Configuración humana
- `.gitignore`
- `.gitattributes`
- `.editorconfig`
- `.env.example`
- `application*.yml`
- `pom.xml`
- `package.json`
- `angular.json`
- `tsconfig*.json`
- `nest-cli.json`
- scripts manuales como `run_all.bat`

### SQL y diseño
- schema;
- seeds;
- migraciones oficiales;
- scripts de apoyo;
- documentación de base de datos.

## Archivos generados por herramientas

### Java / Maven
- `target/`
- ciertos artefactos del wrapper cuando se regeneren por herramienta
- compilados y empaquetados

### Node / Angular / Nest
- `node_modules/`
- `dist/`
- `.angular/`
- caches de build
- lockfiles generados si decides no versionarlos en una estrategia concreta

### Otros generados
- logs temporales;
- exportaciones de prueba;
- archivos de runtime;
- artefactos intermedios.

## Regla práctica

### Se versiona
Lo que expresa intención del proyecto.

### No se versiona
Lo que una herramienta puede reconstruir o lo que es puramente temporal.

## Qué no debe crearse manualmente en el scaffolding

- `node_modules/`
- `dist/`
- `build/`
- `target/`
- caches
- binarios generados
- lockfiles falsos

## Qué sí puede dejarse como plantilla

- `.env.example`
- scripts con comentarios iniciales
- archivos de configuración vacíos o semillenos
- archivos de código base con comentario corto de intención en una fase posterior

## Relación con `.gitignore`

Todo archivo o carpeta generada que no deba entrar al repo debe estar cubierto por `.gitignore`.

## Resultado esperado

Este documento debe ayudar a que el proyecto permanezca limpio y profesional, evitando la mezcla entre intención humana y basura generada por tooling.