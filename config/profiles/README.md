# Perfiles globales del proyecto

Esta carpeta documenta perfiles o convenciones compartidas de ejecución entre componentes.

## Objetivo

Dejar claro cómo se diferencian los ambientes del proyecto a nivel conceptual, aunque luego cada componente tenga su propia implementación técnica.

## Perfiles mínimos esperados

- `local`: desarrollo en máquina del autor;
- `demo`: entorno demostrable o de exhibición controlada;
- `prod`: producción real o despliegue serio.

## Regla importante

El detalle operativo de cada perfil vive dentro del componente correspondiente. Esta carpeta actúa como referencia global y punto de alineación.
