# Onboarding frontend

## Propósito

Explicar cómo debe incorporarse alguien a `storefront-farmacia-angular`, qué necesita entender primero, qué piezas debe reconocer rápido y cómo empezar a trabajar sin perderse en el proyecto.

## Principio general

Un buen frontend público no solo debe verse bien. También debe poder leerse, levantarse y comprenderse sin depender de la memoria del autor. El onboarding existe para reducir fricción, evitar decisiones improvisadas y dejar una base Angular seria antes de poblar `src/`.

## Qué debe entender primero una persona nueva

Antes de tocar código, quien entra al proyecto debería entender estas ideas:

### 1. Esto es un storefront público
No es un panel admin ni una app de consultorio. Es la cara comercial de la farmacia.

### 2. La experiencia gira alrededor del catálogo
Home orienta, catálogo explora y detalle aclara.

### 3. El layout público debe sentirse estable
Header, footer y tono visual deben mantenerse coherentes entre páginas.

### 4. El sistema visual ya está fijado
Verdes farmacéuticos, blanco, tonos claros y branding controlado.

### 5. El producto tiene narrativa propia
`La Alameda Farma` no es una tienda genérica; es una farmacia de barrio confiable.

### 6. V1.0 es pública y acotada
No hay cuenta de cliente ni reservas implementadas desde el frontend en esta etapa.

## Decisiones de onboarding ya cerradas en esta tanda

### Toolchain base
- Angular `20.3.x`;
- Node.js `22.12+`;
- npm `10+`;
- TypeScript `5.9.x`;
- RxJS `7.8.x`.

### Build y dev server
- builder principal: `@angular-devkit/build-angular:application`;
- dev server: `@angular-devkit/build-angular:dev-server`;
- proxy local hacia `backend-farmacia` mediante `proxy.conf.json`.

### Testing base
- runner inicial: Karma + Jasmine;
- configuración central en `karma.conf.js`;
- `ng test` como comando oficial de prueba base.

### Alcance de esta tanda
Esta pasada deja listo el proyecto para arrancar con Angular de forma coherente, pero **no** pretende completar `src/`. Las páginas, componentes y servicios seguirán trabajándose después.

## Ruta sugerida de lectura

Alguien nuevo debería recorrer el proyecto en este orden mental:

### Paso 1. Leer la documentación base
- `README.md`
- `vision_frontend.md`
- `arquitectura_frontend.md`
- `estructura_de_carpetas.md`

### Paso 2. Entender identidad y sistema visual
- `identidad_visual_frontend.md`
- `sistema_visual_y_tokens.md`
- `layout_y_composicion.md`
- `prompts_visuales_y_branding.md`

### Paso 3. Entender piezas del sitio
- `componentes_de_catalogo_y_cards.md`
- `paginas_y_componentes.md`
- `rutas_y_navegacion.md`
- `convenciones_ui.md`

### Paso 4. Entender operación real
- `consumo_de_api.md`
- `manejo_de_estado.md`
- `formularios_y_validaciones.md`
- `manejo_de_errores_ui.md`
- `assets_y_recursos.md`
- `testing_frontend.md`

## Flujo práctico de arranque

### 1. Verificar versión de Node.js
Usar una versión compatible con Angular `20.3.x`. La referencia mínima adoptada es `22.12.0`.

### 2. Instalar dependencias
Ejecutar:

```bash
npm install
```

### 3. Levantar backend-farmacia
Este storefront asume la API pública en `http://localhost:3001`.

### 4. Arrancar dev server
Ejecutar:

```bash
npm run start
```

### 5. Verificación mínima recomendada
Antes de empezar a poblar `src/`, correr:

```bash
npm run verify
```

## Archivos que definen el onboarding técnico

- `package.json`
- `angular.json`
- `tsconfig.json`
- `tsconfig.app.json`
- `tsconfig.spec.json`
- `karma.conf.js`
- `proxy.conf.json`
- `.env.example`
- `public/README.md`

## Relación con backend-farmacia

Este storefront consume **solo** la superficie pública de farmacia:

- `GET /api/v1/catalogo`
- `GET /api/v1/catalogo/buscar`
- `GET /api/v1/catalogo/categorias`
- `GET /api/v1/catalogo/:productoId`
- recursos de media bajo `/media/...`

Por prudencia, el onboarding deja preparado el proxy para `/api/v1` y `/media`, que son las dos superficies que el frontend público necesita primero.

## Regla práctica para la siguiente fase

Cuando el onboarding técnico ya esté sano, recién ahí conviene trabajar `src/` en este orden:

1. shell y layout público;
2. rutas reales;
3. modelos y contratos de API;
4. home;
5. catálogo;
6. detalle.

## Qué no se debe hacer en esta etapa

- inventar slugs si el backend actual expone detalle por `productoId`;
- esconder configuración clave en archivos dispersos;
- mezclar pruebas futuras con setup técnico del proyecto;
- tratar `.env.example` como si Angular ya lo leyera automáticamente;
- empezar a poblar `src/` sobre un workspace todavía ambiguo.

## Resultado esperado

El onboarding del storefront debe permitir que una persona nueva, o una IA que reciba el contexto, pueda instalar, levantar, verificar y entender la base Angular del proyecto sin convertir el frontend en una caja negra dependiente del autor original.
