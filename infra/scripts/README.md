# Scripts compartidos de infraestructura

El punto de entrada operativo principal de esta tanda está en la raíz del repo:

- `run_all.bat`

Ese BAT orquesta la stack local de farmacia con Docker Compose y sirve para:

- levantar la stack completa;
- seguir logs;
- ver estado de contenedores;
- recrear la base local cuando haga falta.

Esta carpeta queda reservada para scripts compartidos futuros que realmente afecten a más de un componente.
