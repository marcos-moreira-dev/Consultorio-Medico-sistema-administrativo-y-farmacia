# storefront-farmacia-angular

Frontend público de farmacia construido con Angular para `La Alameda Farma`.

## Rol dentro del sistema

Este componente representa la vitrina pública de farmacia: home, catálogo y detalle de producto. Debe ser claro, confiable y ligero, con una identidad comercial sobria y una navegación muy entendible.

## Responsabilidades principales

- presentar la marca pública de farmacia;
- permitir explorar el catálogo;
- consumir la API pública de `backend-farmacia`;
- mostrar disponibilidad visible y datos públicos de producto;
- manejar estados de loading, vacío y error sin romper la experiencia.

## Alcance base de V1

- home;
- catálogo;
- detalle de producto;
- búsqueda simple;
- filtros razonables;
- navegación pública estable.

## Qué no debe hacer

- lógica administrativa interna;
- autenticación de cliente si no está justificada;
- replicar reglas de negocio que pertenecen al backend;
- parecer una landing decorativa sin función real.

## Relación con otros componentes

- consume la superficie pública de `backend-farmacia`;
- no accede directamente a `database-farmacia`;
- no se mezcla con la app interna del consultorio.

## Base técnica adoptada en esta tanda

- Angular `20.3.x`;
- Node.js `22.12+`;
- TypeScript `5.9.x`;
- RxJS `7.8.x`;
- Zone.js `0.15.x`;
- testing base con Karma + Jasmine.

## Scripts principales

- `npm run start`: arranca el dev server con proxy local hacia `backend-farmacia` usando el prefijo real `/api/v1`.
- `npm run build`: build de producción.
- `npm run build:dev`: build de desarrollo.
- `npm run watch`: build incremental.
- `npm run test`: suite base de tests.
- `npm run typecheck`: chequeo TypeScript.
- `npm run verify`: typecheck + build.

## Arranque rápido

1. Instalar una versión compatible de Node.js.
2. Ejecutar `npm install`.
3. Levantar `backend-farmacia` en `http://localhost:3001` y consumir la API pública bajo `http://localhost:3001/api/v1`.
4. Ejecutar `npm run start`.

## Nota importante sobre el estado del proyecto

El frontend ya no está solo en onboarding. Esta versión deja una base pública funcional con shell, rutas, home, catálogo, detalle, estado básico, observabilidad ligera, navegación de retorno al catálogo, navbar móvil y una tanda inicial de pruebas útiles. Todavía faltan imágenes finales, más polish responsive y una validación completa con `npm install` + `ng build` en entorno local.

## Documento de entrada recomendado

- `docs/onboarding_frontend.md`
- `docs/vision_frontend.md`
- `docs/arquitectura_frontend.md`
- `docs/consumo_de_api.md`
- `docs/rutas_y_navegacion.md`
- `docs/assets_y_recursos.md`
- `docs/testing_frontend.md`
