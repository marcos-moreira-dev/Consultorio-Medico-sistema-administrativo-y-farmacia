# Scripts de backend-consultorio

Esta carpeta contiene helpers operativos del componente `backend-consultorio`.

## Scripts incluidos

- `dev-backend-consultorio.bat`
  - arranca el backend con perfil `dev` usando el wrapper ligero del proyecto.
- `verify-backend-consultorio.bat`
  - ejecuta una verificación básica: versión de Maven, compilación y tests.
- `doctor-backend-consultorio.bat`
  - revisa Java/Maven y recuerda la ubicación esperada de `toolchains.xml`.
- `smoke-backend-consultorio.bat`
  - prueba `health` y `api-docs` contra un backend ya levantado.
- `toolchains-temurin-21.example.xml`
  - ejemplo listo para copiar a `%USERPROFILE%\.m2	oolchains.xml`.

## Notas operativas

- El proyecto está pensado para **Java Eclipse Temurin 21** con **Maven Toolchains**.
- En este snapshot se deja un `mvnw` / `mvnw.cmd` ligero que delega a `mvn` si ya está instalado.
- Si deseas el wrapper oficial completo de Maven, genera el wrapper en tu máquina con:

```bat
mvn -N wrapper:wrapper
```

## Regla práctica

Todo script aquí debe tener nombre explícito, comentario inicial y una finalidad concreta.
