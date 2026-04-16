# Estado operativo actual

## Objetivo de este documento

Este documento deja por escrito el estado operativo real del repositorio después del saneamiento inicial. La idea es evitar que la documentación prometa más o menos de lo que hoy hace el runtime.

## Farmacia

### Fuente operativa

- `backend-farmacia`: NestJS + TypeORM.
- `storefront-farmacia-angular`: Angular.
- `database-farmacia`: PostgreSQL.
- `run_all.bat`: launcher raíz para la stack local integrada de farmacia.

### Qué hace hoy `run_all.bat`

- levanta la stack de farmacia en segundo plano con Docker Compose;
- espera healthchecks de PostgreSQL, backend y storefront;
- imprime el estado real de los contenedores;
- muestra las URLs finales del backend, Swagger y frontend;
- abre Swagger y storefront cuando `OPEN_BROWSER` no está desactivado.

### Comandos principales

```bat
run_all.bat up
run_all.bat doctor
run_all.bat logs
run_all.bat rebuild
run_all.bat resetdb
```

## Consultorio

### Fuente operativa

- `backend-consultorio`: Spring Boot + Flyway.
- `desktop-consultorio-javafx`: JavaFX + Maven.
- `database-consultorio`: PostgreSQL documental y SQL de apoyo.
- `run_consultorio_all.bat`: launcher raíz para abrir backend y desktop por separado.

### Qué hace hoy `run_consultorio_all.bat`

- localiza los scripts existentes del backend y del desktop;
- abre ventanas separadas para cada componente;
- permite arrancar backend y desktop juntos o por separado;
- puede abrir el backend en el navegador cuando `OPEN_BROWSER` no está desactivado.

### Comandos principales

```bat
run_consultorio_all.bat up
run_consultorio_all.bat backend
run_consultorio_all.bat desktop
run_consultorio_all.bat doctor
```

## Decisiones cerradas

- farmacia mantiene TypeORM como capa profesional de persistencia, con migraciones controladas y `synchronize=false` para flujos serios;
- consultorio mantiene Flyway como fuente operativa de evolución del esquema;
- la prioridad actual del repositorio es validación local, saneamiento de runtime y cierre de inconsistencias, no expansión de alcance.
