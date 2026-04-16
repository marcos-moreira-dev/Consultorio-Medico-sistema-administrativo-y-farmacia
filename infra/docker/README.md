# Recursos Docker compartidos

En esta tanda se decidió mantener los Dockerfiles de desarrollo **junto a cada componente** para evitar ambigüedad:

- `backend-farmacia/Dockerfile.dev`
- `storefront-farmacia-angular/Dockerfile.dev`

Esta carpeta queda reservada para recursos Docker realmente compartidos entre varios componentes.

## Regla práctica

Si un Dockerfile solo sirve a un componente concreto, conviene que viva en ese componente y no aquí.
