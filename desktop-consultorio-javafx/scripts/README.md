# Scripts de desktop-consultorio-javafx

## Scripts incluidos

- `dev-desktop-consultorio.bat`
  - arranca el desktop con `javafx:run`.
- `verify-desktop-consultorio.bat`
  - corre compilación y tests del componente.
- `toolchains-temurin-21.example.xml`
  - ejemplo listo para copiar a `%USERPROFILE%\.m2\toolchains.xml`.

## Regla práctica

Primero copiar el ejemplo de toolchains, luego validar `mvnw.cmd -v`, y recién después correr `javafx:run`.
