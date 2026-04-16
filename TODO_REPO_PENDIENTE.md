# TODO detallado del repositorio

Este archivo intenta dejar **lo pendiente de la forma más específica posible** para evitar que otra IA o una revisión posterior tenga que adivinar el estado del proyecto.

## Estado general estimado

Además del material técnico, ya existe una capa de lectura funcional orientada a usuario final o validador en:

- `MANUAL_USUARIO_FARMACIA.md`
- `MANUAL_USUARIO_CONSULTORIO.md`
- `HANDOFF_RETOQUE_FINAL_GLOBAL.md`


- `backend-farmacia`: base seria y bastante madura; la gran deuda ya es validación local, media, seguridad fina y operación repetible.
- `storefront-farmacia-angular`: frontend público avanzado; la gran deuda ya es validación Angular real, assets finales y polish visual/control de calidad.
- `database-farmacia`: esquema y seeds útiles; falta cerrar del todo la política entre SQL versionado y migraciones ORM.
- `backend-consultorio`: backend fuerte y bastante sembrado; la deuda principal ya es compilación/ejecución real, tests Maven y validación runtime.
- `desktop-consultorio-javafx`: cliente desktop ya navegable y con módulos sembrados; la deuda principal ya es integración real, ejecución JavaFX y remate de formularios/UX.
- `database-consultorio`: base documental y SQL fuerte; la deuda principal ya es operación local repetible y alineación final con runtime.

---

## 0. Validación local obligatoria antes de seguir agregando código

### 0.1 Stack Docker de farmacia
- Ejecutar `run_all.bat up` en Windows con Docker Desktop levantado.
- Confirmar que los tres contenedores suben:
  - `postgres-farmacia`
  - `backend-farmacia`
  - `storefront-farmacia`
- Confirmar que el catálogo público responde en `http://localhost:3001/api/v1/catalogo?size=1`.
- Confirmar que Swagger abre en `http://localhost:3001/docs`.
- Confirmar que el storefront abre en `http://localhost:4200`.
- Si algo falla, registrar el error exacto y decidir si es:
  - un problema de dependencias Node;
  - un problema de Docker/puertos;
  - un problema de código real.

### 0.2 Frontend Angular fuera de Docker
- Entrar a `storefront-farmacia-angular`.
- Ejecutar `npm install`.
- Ejecutar `npm run build`.
- Ejecutar `npm run test`.
- Ejecutar `npm run start` y abrir el sitio en navegador.
- Confirmar que no existan errores de compilación ocultos por falta de `node_modules` en este repo corregido desde sandbox.

### 0.3 Backend NestJS fuera de Docker
- Entrar a `backend-farmacia`.
- Ejecutar `npm install`.
- Ejecutar `npm run verify`.
- Confirmar conexión real contra PostgreSQL local o Docker.
- Confirmar que las rutas públicas y administrativas responden con el prefijo `/api/v1`.

---

## 1. Pendientes específicos de `backend-farmacia`

### 1.1 Cerrar estrategia de migraciones
- Crear migraciones reales de TypeORM o decidir oficialmente que la fuente de verdad seguirá siendo SQL manual versionado.
- Si se usan migraciones TypeORM:
  - crear carpeta `src/app/database/migrations` con migraciones iniciales;
  - probar `migration:run` y `migration:revert`;
  - documentar el flujo exacto en `docs/onboarding_backend.md`.
- Si **no** se usarán migraciones TypeORM en V1:
  - dejarlo escrito explícitamente en README y onboarding para evitar confusión.

### 1.2 Verificación real del módulo de media
- Probar subida de imagen real contra `POST /api/v1/admin/media/productos/imagen/upload`.
- Confirmar que `STORAGE_ROOT` funciona bien tanto en local como en Docker.
- Confirmar URLs públicas generadas por `MEDIA_BASE_URL`.
- Decidir si conviene limitar tipos MIME de forma más estricta.
- Verificar manejo de reemplazo de imágenes y limpieza de archivos huérfanos.

### 1.3 Cerrar contrato de errores del backend
- Revisar respuestas de error reales desde frontend y desde Swagger.
- Confirmar que `correlationId`/trazabilidad esté presente cuando corresponde.
- Verificar que validaciones de DTO no devuelvan estructuras contradictorias entre módulos.

### 1.4 Hardening mínimo de seguridad administrativa
- Confirmar que el login administrativo no quede con credenciales demo en ningún entorno no local.
- Evaluar si conviene añadir rate limiting al login.
- Revisar expiración JWT real y política de renovación.
- Confirmar CORS final para entorno local y futuro demo.

### 1.5 Tests faltantes del backend
- Añadir e2e al menos para:
  - `GET /api/v1/catalogo`
  - `GET /api/v1/catalogo/:productoId`
  - login administrativo exitoso y fallido;
  - cambio de disponibilidad/publicación.
- Añadir tests del módulo de media más allá del happy path.

---

## 2. Pendientes específicos de `storefront-farmacia-angular`

### 2.1 Validación de compilación real
- Confirmar que la corrección del source compila con Angular 20.3.x real.
- Confirmar que Karma/Jasmine arranca sin ajustes extra.
- Confirmar que no haya imports faltantes, providers omitidos o templates inválidos al compilar de verdad.

### 2.2 Resolver imágenes y branding final
- Crear imágenes reales para:
  - hero/home;
  - placeholders de producto;
  - posibles categorías destacadas;
  - favicon y marca final si cambia.
- Revisar logos actuales (`logo Farmacia.png`) y decidir si se quedan así o se reemplazan.
- Confirmar tamaños, pesos y rutas dentro de `public/` o `src/assets/`.

### 2.3 Cerrar responsive con verificación visual real
- Revisar catálogo, detalle y navbar móvil en tamaños reales:
  - 360 px;
  - 390 px;
  - 768 px;
  - 1024 px;
  - escritorio ancho.
- Corregir desbordes, saltos raros y espacios muertos.
- Revisar line-height, tamaños de botón y chips de filtros en móvil.

### 2.4 Mejorar UX final del catálogo
- Confirmar si se quedará solo vista grid o si también se activará una vista lista real usando `ProductListItemComponent`.
- Revisar si el ordenamiento actual es suficiente o si conviene uno más claro para usuario final.
- Confirmar si la búsqueda debe tener debounce visible o no.
- Confirmar si la paginación por 12/24/36 queda como decisión final.

### 2.5 Mejorar UX final del detalle
- Revisar si se mostrará precio siempre o si puede venir nulo para ciertos productos.
- Confirmar copy final de disponibilidad (`Disponible`, `Agotado`, etc.).
- Revisar si el bloque de relacionados debe ocultarse completo cuando falle o cuando venga vacío.
- Revisar si conviene una galería real o una sola imagen por producto en V1.

### 2.6 Runtime config del frontend
- Hoy existe `.env.example` solo como documento.
- Decidir si el frontend usará:
  - proxy + paths relativos en dev;
  - `environment.ts` por ambiente;
  - configuración runtime cargada desde JSON;
  - o una mezcla controlada.
- Dejarlo escrito en `docs/onboarding_frontend.md` para que no quede como ambigüedad permanente.

### 2.7 Pruebas faltantes del frontend
- Añadir tests a componentes importantes, no solo servicios/facades:
  - navbar;
  - paginación;
  - loading bar;
  - breadcrumb;
  - product card;
  - catálogo page;
  - detalle page.
- Añadir al menos una prueba de integración básica de navegación o estado.

### 2.8 Accesibilidad pendiente
- Revisar contraste de colores con herramientas reales.
- Confirmar foco visible en navegación por teclado.
- Confirmar orden de tabulación en navbar móvil.
- Revisar `aria-label` y `aria-live` donde aplique.

---

## 3. Pendientes de `database-farmacia`

### 3.1 Flujo repetible de creación local
- Confirmar que los scripts SQL corren limpios desde cero.
- Confirmar que el orden de ejecución sigue siendo:
  1. `V2_3FN.sql`
  2. `V2_1_backend_extension.sql`
  3. `02_seed_demo_3FN.sql`
  4. `03_seed_backend_extension.sql`
- Documentar el flujo exacto también fuera de Docker.

### 3.2 Revisar seeds demo
- Validar que los datos demo cubren bien:
  - productos activos;
  - productos agotados;
  - categorías sin ruido;
  - login admin demo.
- Revisar si conviene añadir más imágenes demo o referencias demo de media.

### 3.3 Decidir estrategia final entre SQL formal y ORM
- Confirmar si la evolución del esquema seguirá primero en SQL manual y luego se reflejará en backend.
- O confirmar si la evolución pasa a migraciones del ORM.
- Evitar quedar con dos fuentes de verdad inconsistentes.

---

## 4. Pendientes de `backend-consultorio`

### 4.1 Definir estado real del componente
- Auditar cuánto código real existe y cuánto sigue siendo scaffolding documental.
- Decidir si el siguiente esfuerzo del proyecto irá a consultorio o si farmacia sigue siendo prioridad.

### 4.2 Cerrar onboarding técnico mínimo
- Verificar `package.json`, scripts, `.env.example` y build real.
- Si no compila todavía, dejar eso explícito en README para no inducir a error.

### 4.3 Contratos y módulos pendientes
- Verificar qué módulos ya están sembrados de verdad.
- Detectar si hay endpoints o DTOs todavía nominales sin implementación.

---

## 5. Pendientes de `desktop-consultorio-javafx`

### 5.1 Verificar onboarding real
- Confirmar JDK exacto, Maven y JavaFX necesarios.
- Confirmar si el `pom.xml` ya está realmente cableado para correr.
- Confirmar empaquetado o al menos ejecución local.

### 5.2 Estado del source
- Revisar qué tan cerca está de una app JavaFX usable y qué tanto sigue siendo estructura base.
- Alinear README y docs con ese estado real.

---

## 6. Pendientes de `database-consultorio`

### 6.1 Confirmar versión oficial del esquema
- Dejar clarísimo cuál archivo es la versión oficial operativa del esquema.
- Evitar que varias versiones SQL parezcan igualmente activas.

### 6.2 Seeds y operación local
- Confirmar si ya existen seeds útiles.
- Si no existen, decidir si hacen falta antes del backend/desktop.

---

## 7. Limpieza de repo todavía deseable

### 7.1 Revisar archivos puramente documentales que ya no aporten
- Buscar READMEs o placeholders redundantes si empiezan a contradecir el estado real.
- Mantener documentación que **sí** explique el proyecto, pero evitar duplicar lo mismo en cinco sitios.

### 7.2 Revisar nombres dañados o artefactos raros del zip
- Verificar que no queden rutas con caracteres dañados heredadas de exportaciones previas.
- Si aparecen, renombrarlas o moverlas a una ruta limpia.

### 7.3 Confirmar política de assets grandes
- Evitar meter imágenes pesadas, builds o `node_modules` dentro del repo versionado o de zips manuales.

---

## 8. Decisiones de diseño todavía abiertas

### 8.1 API pública con prefijo
- Ya se alineó el frontend con el backend para consumir `/api/v1/catalogo`.
- Falta confirmar si esa será la decisión final o si en el futuro se excluirá la API pública del prefijo global.
- No cambiar esto sin actualizar backend, frontend y documentación a la vez.

### 8.2 Slug vs `productoId`
- Hoy el storefront usa `productoId` para el detalle.
- Falta decidir si V1 se queda así o si en una fase posterior se introduce slug público.

### 8.3 Configuración runtime del frontend
- Aún falta decidir la estrategia definitiva.
- No dejarla en un limbo documental indefinido.

---

## Criterio general para próximas tandas

No conviene seguir agrandando el proyecto “por inercia”. Lo más rentable ahora es:

1. **validar localmente lo ya sembrado**;
2. corregir lo que explote de verdad;
3. cerrar decisiones abiertas;
4. recién después agregar nuevas capas funcionales.
