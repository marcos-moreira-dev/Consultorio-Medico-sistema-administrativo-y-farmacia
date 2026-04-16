# backend-farmacia

Backend del subdominio farmacia, con dos superficies diferenciadas: una **administrativa privada** y una **pública** orientada al catálogo.

## Rol dentro del sistema

Este componente coordina la parte comercial y operativa básica de farmacia. Debe separar claramente la administración interna del catálogo público, evitando mezclar permisos, datos y contratos.

## Responsabilidades principales

- autenticación administrativa;
- gestión de categorías y productos;
- control de publicación y disponibilidad pública;
- manejo de imágenes o media asociada al catálogo;
- exposición de endpoints públicos de lectura;
- trazabilidad y validaciones del dominio comercial.

## Módulos conceptuales clave

- `auth-admin`
- `categorias`
- `productos`
- `media`
- `catalogo-publico`
- `disponibilidad-publicacion`

## Relación con otros componentes

- consume `database-farmacia`;
- sirve datos públicos a `storefront-farmacia-angular`;
- no debe mezclar lógica clínica ni datos del consultorio.

## Estado actual

El backend ya no debe leerse como simple scaffolding. Actualmente tiene:

- módulos funcionales sembrados y conectados;
- compilación, typecheck y tests operativos;
- contratos públicos y administrativos suficientemente visibles;
- documentación fuerte para seguir consolidando sin improvisar;
- espacio explícito para mejoras futuras sin mezclar V1.1 dentro de V1.0.

Sigue siendo un **borrador serio** y no una versión final cerrada. La fuente de verdad práctica hoy es la combinación de:

- documentación del componente;
- código que compila;
- tests que pasan;
- entidades TypeORM y migraciones controladas del backend;
- SQL formal de `database-farmacia` como referencia y apoyo de persistencia.

## Comandos útiles

- `npm install`
- `npm run start:dev`
- `npm run start:docker-local`
- `npm run build`
- `npm run typecheck`
- `npm run test:unit`
- `npm run test:e2e -- --runInBand`
- `npm run verify`

`npm run verify` ejecuta la verificación mínima completa del backend: build, typecheck, tests unitarios y tests e2e.

## Rutas base actuales

Con `API_PREFIX=api/v1`, la superficie principal queda así:

### Pública
- `GET /api/v1/catalogo/categorias`
- `GET /api/v1/catalogo`
- `GET /api/v1/catalogo/buscar`
- `GET /api/v1/catalogo/:productoId`

### Administrativa
- `POST /api/v1/admin/auth/login`
- `GET /api/v1/admin/auth/me`
- `GET|POST|PATCH /api/v1/admin/categorias/...`
- `GET|POST|PATCH /api/v1/admin/productos/...`
- `PATCH /api/v1/admin/productos/:productoId/publicacion/...`
- `PATCH /api/v1/admin/productos/:productoId/disponibilidad`
- `POST|PATCH /api/v1/admin/media/productos/...`

## Documentos que conviene leer primero

- `docs/vision_backend.md`
- `docs/arquitectura_backend.md`
- `docs/modulos_y_responsabilidades.md`
- `docs/estructura_de_paquetes.md`
- `docs/contratos_api.md`
- `docs/media_y_archivos.md`
- `docs/testing_backend.md`
- `docs/onboarding_backend.md`

## Regla práctica

En este componente es vital no perder la frontera entre lo público y lo administrativo. Cualquier implementación debe respetar esa separación desde el inicio.


## Docker local de farmacia

Desde la raíz del repo se puede levantar la stack local con:

```bat
run_all.bat up
```

Esto arranca PostgreSQL, `backend-farmacia` y `storefront-farmacia-angular`, espera sus healthchecks y resume el estado real del stack antes de abrir Swagger y el storefront.
