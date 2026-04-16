# Lienzo de continuidad: backend-farmacia

## Objetivo real de este lienzo
Este lienzo **no** existe para detener el trabajo y pasar todo a otra IA.

Existe para que la próxima continuación del trabajo haga esto, en este orden:

1. **seguir avanzando todo lo posible en el `backend-farmacia`**;
2. **corregir, consolidar y limpiar lo necesario del backend**;
3. **dejar señalizado qué partes pueden estallar al compilar o integrar**;
4. **recién después pasar al frontend de farmacia con el mismo enfoque**;
5. y luego, cuando toque, hacer algo equivalente con el frontend del consultorio.

Este documento, por tanto, debe usarse como **guía operativa de continuación del backend-farmacia**, no como cierre prematuro.

---

## Alcance de este lienzo
**Solo backend de farmacia.**

No frontend de farmacia todavía dentro de este lienzo.
No consultorio todavía dentro de este lienzo.
No refactor global del repo.

---

## Enfoque correcto para la próxima IA
La próxima IA debe asumir que:
- ya se sembró bastante estructura;
- parte de esa estructura puede tener malos olores o incoherencias finas;
- la meta inmediata es **terminar de empujar el backend-farmacia lo más lejos posible** antes de pasar al frontend.

Eso significa:
- no reescribir desde cero;
- no abrir frentes innecesarios;
- sí revisar con precisión qué compila, qué no, qué está débil y qué quedó solo conceptualmente bien;
- sí dejar muy claro qué puede estallar después.

---

## Qué ya se trabajó en backend-farmacia

### Módulos sembrados
Se trabajó o sembró lógica/código en:
- `auth-admin`
- `categorias`
- `productos`
- `disponibilidad-publicacion`
- `catalogo-publico`
- `media`
- `common`
- `security`
- parte de `config` y `database`

### Base de datos y soporte operativo sembrado
Se preparó:
- uso de `DB_SCHEMA=farmacia`
- extensión SQL operativa para:
  - `usuario_admin`
  - `media_recurso`
- seed inicial de usuario admin con `password_hash`
- script de generación de hash BCrypt

### Contrato público sembrado para frontend futuro
Se dejó encaminado que el backend público entregue:
- catálogo público
- búsqueda pública
- detalle público
- categorías públicas
- `imagenUrl` en catálogo y detalle, resuelta desde backend

### Tests sembrados
Se dejó una base de tests:
- unit de use cases
- unit de servicios/validadores
- unit de utilidades/interceptor/strategy
- e2e mínimo del envelope HTTP

---

## Lo que **sí** debe hacer la próxima IA antes del frontend

## Fase 1. Revisar y consolidar el backend-farmacia
La próxima IA debe seguir trabajando sobre el backend, no saltar aún al frontend.

### Objetivo de esta fase
Dejar el backend-farmacia en el punto más estable y coherente posible para que luego el frontend se apoye sobre contratos reales y no sobre suposiciones.

### Tareas concretas
1. **Revisar coherencia interna archivo por archivo** en las áreas más tocadas.
2. **Corregir imports, rutas relativas, tipos y dependencias cruzadas** que hayan quedado frágiles.
3. **Alinear DTOs, entidades, servicios, tests y SQL**.
4. **Detectar sobreingeniería o capas redundantes** que se hayan metido por avanzar rápido.
5. **Mantener trazabilidad de lo que quede potencialmente roto o dudoso**.

---

## Fase 2. Prioridades específicas de revisión dentro del código

## 1. `catalogo-publico` es la prioridad más alta
Esta es la zona más delicada ahora mismo porque conecta backend con el futuro frontend.

### Archivos que deben revisarse con mucho cuidado
- `src/app/modules/catalogo-publico/catalogo-publico.module.ts`
- `src/app/modules/catalogo-publico/controllers/catalogo-publico.controller.ts`
- `src/app/modules/catalogo-publico/dto/buscar-catalogo.query.dto.ts`
- `src/app/modules/catalogo-publico/dto/catalogo-item.response.dto.ts`
- `src/app/modules/catalogo-publico/dto/producto-publico-detalle.response.dto.ts`
- `src/app/modules/catalogo-publico/dto/categoria-publica.response.dto.ts`
- `src/app/modules/catalogo-publico/mappers/catalogo-publico.mapper.ts`
- `src/app/modules/catalogo-publico/services/catalogo-publico.service.ts`
- `src/app/modules/catalogo-publico/use-cases/*.ts`

### Qué corroborar o corregir ahí
- que los `imports` de `MediaRecursoEntity` y `ProductoEntity` sean correctos;
- que `TypeOrmModule.forFeature([ProductoEntity, MediaRecursoEntity])` esté bien justificado;
- que los query builders no dependan de nombres de columna inconsistentes con la BD real;
- que `innerJoin('producto.categoria', 'categoria')` funcione con la relación definida;
- que los `select(...)`, `addSelect(...)`, `groupBy(...)` y `getRawMany(...)` sean consistentes con PostgreSQL;
- que `findImagenesPrincipalesByProductoIds(...)` no tenga problemas de tipado con `In(productoIds)`;
- que `tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO` sea compatible con la entidad real;
- que `imagenUrl` realmente deba salir como `null` cuando no haya imagen, y no `undefined`;
- que los DTOs públicos no estén pidiendo más o menos campos de los que el mapper entrega;
- que `page`/`limit` sea el contrato definitivo, no `page`/`size`;
- que la búsqueda pública con `q` mínimo 2 caracteres esté bien aplicada y no rompa tests o UX futura;
- que el servicio no esté mezclando demasiada lógica de consulta + enriquecimiento + decisiones de presentación.

### Posibles malos olores aquí
- demasiada lógica en `CatalogoPublicoService`;
- mapper demasiado delgado o demasiado trivial;
- uso de media como acople extra dentro de catálogo;
- query builder difícil de mantener si crece el módulo.

La próxima IA debe decidir si conviene **mantener esto así** o **extraer algo pequeño y útil** sin sobreingeniería.

---

## 2. `media` debe revisarse como módulo operativo real

### Archivos críticos
- `src/app/modules/media/media.module.ts`
- `src/app/modules/media/controllers/media.controller.ts`
- `src/app/modules/media/dto/*.ts`
- `src/app/modules/media/entities/media-recurso.entity.ts`
- `src/app/modules/media/repositories/media-recurso.repository.ts`
- `src/app/modules/media/services/media.service.ts`
- `src/app/modules/media/storage/filesystem-storage.provider.ts`
- `src/app/modules/media/storage/media-path.resolver.ts`
- `src/app/modules/media/validation/media-file.validator.ts`
- `src/app/modules/media/mappers/media.mapper.ts`

### Qué corroborar o corregir ahí
- que `media_recurso` refleje de verdad el SQL sembrado;
- que la longitud de columnas y nombres coincida con la tabla;
- que `productoId` nullable se comporte bien en entidad, repositorio y servicio;
- que el flujo actual (`upload -> asociar -> reemplazar`) siga teniendo sentido para V1;
- que `FilesystemStorageProvider` use correctamente `storage.root` y `app.publicBaseUrl`;
- que la construcción de `urlPublica` no duplique o rompa `/media/`;
- que `file.buffer` sea una suposición aceptable para el interceptor/multer que se usará;
- que el controlador esté realmente preparado para `multipart/form-data`;
- que `MediaFileValidator` no dependa de condiciones demasiado finas o redundantes;
- que el módulo no esté sobrecomplicado para una farmacia pequeña.

### Decisión que la próxima IA debe evaluar
Si el flujo queda demasiado pesado para V1, evaluar si conviene simplificarlo a:
- subir y asociar en una sola acción;
- reemplazar en otra.

Pero **no cambiarlo solo por gusto**. Justificarlo si se hace.

---

## 3. `auth-admin` debe quedar realmente coherente

### Archivos críticos
- `src/app/modules/auth-admin/auth-admin.module.ts`
- `src/app/modules/auth-admin/controllers/auth-admin.controller.ts`
- `src/app/modules/auth-admin/dto/*.ts`
- `src/app/modules/auth-admin/entities/usuario-admin.entity.ts`
- `src/app/modules/auth-admin/repositories/usuario-admin.repository.ts`
- `src/app/modules/auth-admin/services/auth-admin.service.ts`
- `src/app/modules/auth-admin/use-cases/*.ts`
- `src/app/modules/auth-admin/mappers/auth-admin.mapper.ts`
- `src/app/security/strategies/jwt.strategy.ts`
- `src/app/security/types/authenticated-admin.type.ts`

### Qué corroborar o corregir ahí
- que el login use realmente `bcrypt.compare(...)`;
- que el seed SQL y la entidad `UsuarioAdminEntity` coincidan;
- que el `rolCodigo` y los roles efectivos estén bien alineados;
- que `getRoles()` no esté demasiado rígido ni demasiado abierto;
- que `JwtStrategy.validate(...)` normalice bien `adminId`, `username`, `email` y `roles`;
- que `/me` consulte persistencia y no dependa solo del token;
- que el `payload` JWT no tenga inconsistencias entre `sub` y `adminId`;
- que `expiresIn` y el cálculo de segundos sean coherentes con configuración;
- que los DTOs de login y sesión no estén demasiado acoplados a la forma interna del JWT.

### Riesgos potenciales
- imports o tipos rotos en mocks/tests;
- seed y entidad desalineados;
- flujo de auth conceptualmente bien, pero con detalles de wiring rotos.

---

## 4. `categorias` y `productos` deben revisarse contra la BD formal

### Archivos críticos en categorías
- `src/app/modules/categorias/entities/categoria.entity.ts`
- `src/app/modules/categorias/dto/*.ts`
- `src/app/modules/categorias/services/categorias.service.ts`
- `src/app/modules/categorias/repositories/categoria.repository.ts`
- `src/app/modules/categorias/validation/categoria-rules.validator.ts`

### Archivos críticos en productos
- `src/app/modules/productos/entities/producto.entity.ts`
- `src/app/modules/productos/dto/*.ts`
- `src/app/modules/productos/services/productos.service.ts`
- `src/app/modules/productos/repositories/producto.repository.ts`
- `src/app/modules/productos/validation/producto-rules.validator.ts`
- `src/app/modules/productos/mappers/producto.mapper.ts`

### Qué corroborar o corregir ahí
- que longitudes de `varchar` coincidan con SQL formal;
- que `descripcion_breve` y `precio_visible` coincidan con el modelo real;
- que el `DecimalToNumberTransformer` sea necesario y correcto;
- que índices y nombres de columnas no estén desalineados con la BD;
- que las reglas de unicidad natural de producto estén coherentes con la tabla;
- que la normalización de texto no contradiga constraints SQL;
- que los DTOs no permitan más longitud que la columna real.

---

## 5. `disponibilidad-publicacion` debe revisarse como semántica de dominio

### Archivos críticos
- `src/app/modules/disponibilidad-publicacion/dto/*.ts`
- `src/app/modules/disponibilidad-publicacion/services/disponibilidad-publicacion.service.ts`
- `src/app/modules/disponibilidad-publicacion/validation/publicacion-rules.validator.ts`
- `src/app/modules/disponibilidad-publicacion/use-cases/*.ts`

### Qué corroborar o corregir ahí
- que `NO_PUBLICADO` quede reservado para despublicación;
- que `actualizar-disponibilidad` no acepte `NO_PUBLICADO`;
- que `publicar` y `despublicar` mantengan coherencia con `productos`;
- que la lógica centralizada en `ProductoPublicacionUtil` realmente evite duplicación;
- que no haya reglas de publicación repetidas en demasiados sitios.

---

## 6. `common`, `config` y `database` deben revisarse como pegamento del proyecto

### Archivos críticos
- `src/main.ts`
- `src/app/config/configuration.ts`
- `src/app/database/data-source.ts`
- `src/app/common/interceptors/api-response.interceptor.ts`
- `src/app/common/interceptors/correlation-id.interceptor.ts`
- `src/app/common/filters/http-exception.filter.ts`
- `src/app/common/utils/parse-query-boolean.util.ts`
- `src/app/common/utils/producto-publicacion.util.ts`
- `src/app/common/utils/text-normalization.util.ts`

### Qué corroborar o corregir ahí
- que `/media` se sirva correctamente desde `STORAGE_ROOT`;
- que `PUBLIC_BASE_URL` y `MEDIA_BASE_URL` no se contradigan;
- que el `ApiResponseInterceptor` no rompa envelopes ya normalizados;
- que el filtro de errores y el interceptor produzcan contratos consistentes;
- que el parseo booleano en query realmente ayude y no genere rarezas;
- que `DB_SCHEMA=farmacia` esté alineado en config, data source y SQL;
- que `DB_SYNC=false` siga siendo la política recomendada.

---

## Fase 3. Revisar la base de datos con mucho cuidado
La próxima IA debe asumir que el usuario quiere conservar el rastro de normalización.

### Regla
No romper arbitrariamente el SQL base formal.

### Qué hacer
Tratar el SQL actual como dos capas:
1. **modelo normalizado base**;
2. **extensión operativa para backend**.

### Archivos SQL a revisar
- SQL base normalizado de farmacia
- `database-farmacia/V2/schema/V2_1_backend_extension.sql`
- `database-farmacia/V2/seeds/03_seed_backend_extension.sql`

### Qué corroborar ahí
- que `usuario_admin` no contradiga decisiones previas de modelado;
- que `media_recurso` no rompa el núcleo 3FN, sino que lo complemente;
- que nombres, tipos, FKs y constraints coincidan con las entidades TypeORM;
- que el seed del admin use realmente un hash BCrypt válido;
- que los nombres de schema/tablas sean coherentes con lo que usa el backend.

---

## Fase 4. Qué puede estallar después
La próxima IA debe dejar anotado explícitamente cualquier punto de riesgo real.

### Riesgos probables
- imports relativos mal armados;
- mocks de tests incompletos;
- diferencias finas entre SQL y entidades;
- problemas con nombres de columnas en query builders;
- multipart/file upload dependiendo de configuración concreta de multer;
- detalles de auth aparentemente correctos pero rotos en wiring;
- `catalogo-publico.service.ts` demasiado cargado;
- contrato público correcto conceptualmente pero con pequeños huecos de implementación.

---

## Qué NO debe hacer la próxima IA todavía
- No empezar frontend Angular fuerte antes de dejar el backend bastante estable.
- No meter Flyway en esta etapa salvo decisión estratégica explícita.
- No hacer refactor cosmético masivo sin revisar compilación/integración.
- No abrir módulos nuevos por impulso.
- No rehacer desde cero lo que ya está sembrado.

---

## Señal de salida para pasar al frontend de farmacia
El backend-farmacia estará suficientemente listo para pasar al frontend cuando la próxima IA deje razonablemente estable esto:
- auth admin coherente;
- `usuario_admin` alineado con SQL y código;
- media coherente con `media_recurso`;
- `/media` resuelto correctamente;
- catálogo público con `imagenUrl`;
- categorías públicas funcionales;
- contratos públicos estables;
- tests principales alineados o al menos corregidos donde se rompieron;
- puntos de riesgo claramente documentados.

En ese momento, el trabajo debe continuar **con el frontend de farmacia**, no saltar todavía al consultorio.

---

## Nota final importante
El usuario sospecha, con razón, que parte del trabajo reciente pudo haber metido malos olores o torpeza arquitectónica por la necesidad de avanzar.

La próxima IA debe asumir eso como hipótesis seria de trabajo y:
- revisar con ojo crítico;
- simplificar donde haga falta;
- consolidar donde convenga;
- pero sin destruir el progreso ni abrir un refactor infinito.

La meta no es “elegancia pura”.
La meta es: **terminar de empujar el backend-farmacia de forma útil, dejar notificados los riesgos y luego pasar al frontend de farmacia con buena base**.



---

# Segunda tanda: contexto operativo y mapa fino de continuidad

## Propósito de esta segunda tanda
Esta segunda tanda existe para darle a la próxima IA **más contexto fino**, no solo una lista de tareas.

La idea es que la próxima IA no vea el backend-farmacia como un árbol aislado de archivos, sino como un trabajo ya encaminado con:
- decisiones de dominio tomadas;
- zonas tocadas con distinta profundidad;
- piezas que quedaron sembradas conceptualmente pero pueden estar frágiles al compilar;
- y una intención fuerte de no improvisar reglas ni contratos.

---

## Lectura correcta del estado del proyecto
El `backend-farmacia` no está en estado “vacío”.
Tampoco está en estado “estable y terminado”.

Está en un estado intermedio muy importante:

- **ya hay arquitectura sembrada**;
- **ya hay bastante intención de producto y dominio**;
- **ya se definieron varios contratos**;
- **pero todavía no se ha pasado por la fase dura de consolidación técnica real**.

La próxima IA debe leer esto como:
> “No me toca inventar el backend. Me toca terminar de aterrizar y limpiar lo que ya fue decidido.”

---

## Decisiones de producto/backend que ya deben considerarse tomadas
Estas decisiones ya se empujaron lo suficiente como para tratarlas, por defecto, como base del proyecto.

### 1. Separación clara entre backend público y backend administrativo
El sistema farmacia no se está modelando como una sola superficie confusa.
Se decidió separar al menos estas dos caras:

- **admin farmacia**: auth, CRUD, publicación, media;
- **catálogo público**: listar, buscar, ver detalle, categorías públicas.

La próxima IA no debería mezclar esas superficies sin justificación fuerte.

### 2. El catálogo público no debe inferir reglas de negocio por su cuenta
El frontend futuro no debe deducir si algo está visible o no.
La semántica de publicación se decidió del lado backend.

En términos prácticos, la visibilidad pública depende de algo equivalente a:
- `estadoProducto = ACTIVO`
- `esPublicable = true`
- `estadoDisponibilidad in (DISPONIBLE, AGOTADO)`

### 3. `NO_PUBLICADO` no es una disponibilidad pública “normal”
Se empujó la idea de que:
- `NO_PUBLICADO` es estado interno de despublicación;
- no debe ser tratado como disponibilidad pública visible;
- no debe entrar por el flujo de `actualizar-disponibilidad`.

### 4. La imagen del producto debe venir resuelta desde backend público
Ya se empujó la decisión de que el frontend futuro no debe resolver imágenes con múltiples llamadas raras.
La intención es que:
- el backend público devuelva `imagenUrl` en catálogo y detalle;
- si no hay imagen, salga `null`;
- el frontend use placeholder visual si hace falta.

### 5. La autenticación admin debe apoyarse en BCrypt
La conclusión ya tomada es:
- `backend-consultorio` sí usa BCrypt de verdad;
- `backend-farmacia` debe alinearse a eso;
- se sembró entidad/seed/script para que el flujo quede orientado a BCrypt.

### 6. La BD de farmacia no debe “reventarse” por conveniencia del backend
Se tomó una decisión importante: conservar el rastro del modelo normalizado y sumar una extensión operativa.
Es decir:
- no reinterpretar el SQL base como si no existiera;
- no destruir el enfoque académico/3FN por rapidez;
- sí complementar con tablas operativas que el backend necesita.

---

## Qué se sembró con mayor profundidad y qué quedó más superficial
La próxima IA debe entender que no todos los módulos están igual de maduros.

### Más empujados conceptualmente
- `common`
- `productos`
- `disponibilidad-publicacion`
- `catalogo-publico`
- `media`
- `auth-admin`

### Más frágiles por probable necesidad de ajuste fino
- `catalogo-publico.service.ts`
- `media.service.ts`
- `auth-admin.service.ts`
- `jwt.strategy.ts`
- wiring de TypeORM con entidades y schema
- tests con mocks sembrados rápidamente

### Más “de soporte” que seguramente solo requieren coherencia
- `configuration.ts`
- `data-source.ts`
- `.env.example`
- config local example
- utilidades pequeñas como parseo/normalización

---

## Archivos nuevos y extendidos que se sembraron en esta etapa
Esta lista es importante porque la próxima IA debe revisar estas piezas como “últimas capas añadidas”, donde es más probable encontrar inconsistencias de integración.

## SQL / seeds / scripts
Se sembraron o propusieron:
- `database-farmacia/V2/schema/V2_1_backend_extension.sql`
- `database-farmacia/V2/seeds/03_seed_backend_extension.sql`
- `backend-farmacia/scripts/generar-hash-bcrypt.js`

### Intención de esos archivos
- **`V2_1_backend_extension.sql`**: no reemplaza el SQL base. Lo extiende con tablas operativas.
- **`03_seed_backend_extension.sql`**: deja un admin demo y prepara el terreno de auth.
- **`generar-hash-bcrypt.js`**: herramienta utilitaria simple para generar hashes nuevos sin improvisar.

### Qué puede estallar ahí
- diferencias entre nombres/longitudes de columnas del SQL y entidades TypeORM;
- conflictos con un SQL base que use nombres distintos o schema distinto;
- seed que conceptualmente sirve pero requiera ajuste fino según nombres reales del modelo.

---

## Área `catalogo-publico`
Se agregaron o empujaron:
- DTO de categoría pública;
- caso de uso `listar-categorias-publicas`;
- enriquecimiento con `imagenUrl`;
- servicio con consulta pública + lookup de imagen principal.

### Intención de diseño
La idea fue dejar un backend público suficientemente completo para que Angular pueda arrancar después con:
- home;
- listado catálogo;
- filtros por categoría;
- detalle de producto;
- placeholders visuales solo cuando no haya imagen.

### Riesgos específicos de diseño
1. **`CatalogoPublicoService` puede estar demasiado cargado**.
   Hoy concentra:
   - query pública;
   - paginación;
   - búsqueda;
   - categorías públicas;
   - lookup de imágenes principales.

2. **La estrategia de imagen principal es pragmática, no necesariamente definitiva**.
   Se decidió algo simple:
   - tomar la última media asociada por `mediaRecursoId` descendente.

   Eso sirve para V1, pero la próxima IA debe revisar si eso sigue siendo la política correcta o si necesita formalizarse mejor.

3. **El servicio está muy orientado al futuro frontend**, lo cual es bueno, pero puede aumentar el acoplamiento si no se limpia un poco.

---

## Área `media`
La intención fue resolver media de forma suficientemente profesional, pero sin llegar todavía a un sistema de assets complejo.

### Filosofía sembrada
- el archivo vive en storage;
- la BD guarda referencia y metadatos;
- la media se trata como un recurso operacional del backend;
- la URL pública se calcula desde backend.

### Flujo que se empujó
- subir archivo;
- asociar archivo a producto;
- reemplazar imagen actual.

### Lectura correcta
Ese flujo fue sembrado por claridad de dominio, no porque se haya demostrado aún que sea el flujo UX ideal para V1.

La próxima IA debe evaluar si:
- ese nivel de separación aporta claridad real,
- o si está encareciendo innecesariamente una farmacia pequeña.

### Riesgo importante
El módulo `media` puede haber quedado conceptualmente bien pero demasiado sofisticado respecto al tamaño del proyecto.
No simplificarlo por impulso, pero sí revisar si el valor real compensa la complejidad.

---

## Área `auth-admin`
Se empujó una auth simple pero formal:
- JWT;
- sesión actual `/me`;
- usuario administrativo persistido;
- intención de BCrypt.

### Intención de diseño
No hacer una auth extravagante.
Solo dejar una auth administrativa limpia y suficiente para el backend de farmacia.

### Lo más importante aquí
La próxima IA debe distinguir entre:
- lo que ya está bien como diseño,
- y lo que puede seguir débil por wiring o mocks.

La zona más crítica no es tanto el concepto, sino la integración fina entre:
- entidad;
- seed SQL;
- repositorio;
- `bcrypt.compare(...)`;
- `JwtStrategy`;
- DTOs;
- tests.

### Riesgo de esta zona
Puede haber coherencia conceptual pero pequeños errores tontos de:
- import;
- tipo;
- propiedad esperada;
- payload JWT;
- mock incompleto;
- dependencia mal inyectada.

---

## Área `categorias` y `productos`
Aquí se hizo algo importante: alinear más el backend con la BD formal y con restricciones más realistas.

### Qué se empujó
- longitudes de columnas más explícitas;
- DTOs más alineados con SQL;
- `DecimalToNumberTransformer` para `precio_visible`;
- idea de identidad natural más consistente para producto.

### Qué debe vigilar la próxima IA
- si los tamaños de `varchar` coinciden de verdad con el SQL base del usuario;
- si `descripcion_breve` tiene la misma longitud en todos los sitios;
- si el transformer de decimal es necesario o sobra según el driver y el uso;
- si los índices definidos en entidad reflejan la realidad de la BD o solo una intención parcial.

---

## Sobre la política de migraciones y herramientas
Se discutió explícitamente Flyway.
La conclusión operativa tomada para esta fase fue:

- **no meter Flyway ahora**;
- **no abrir ese frente** si va a retrasar todo;
- mantener disciplina de migraciones como idea, pero no cambiar de herramienta en medio del avance.

### Lo que sí debe asumir la próxima IA
- `DB_SYNC=false` como política preferida;
- la BD no debe quedar gobernada por sincronización automática;
- el proyecto debe tratar sus cambios de esquema con seriedad, aunque no se meta Flyway todavía.

---

## Sobre los tests ya sembrados
Se escribieron varias tandas de tests con un objetivo muy concreto:
- dejar una base suficiente para que luego se ajuste,
- no una suite final completamente consolidada.

### Cómo debe leerlos la próxima IA
Los tests actuales son:
- valiosos como mapa de intención;
- útiles como red inicial;
- pero potencialmente frágiles en nombres, imports, mocks y wiring.

### No leerlos como verdad absoluta
La próxima IA no debe asumir que:
- si el test existe, entonces el contrato ya es perfecto;
- si el mock compila, entonces el módulo está sano.

Debe tratarlos como:
> “una base de intención técnica que probablemente necesite ajuste fino cuando entre la compilación real”.

### Zonas de tests más relevantes
- `auth-admin`
- `catalogo-publico`
- `media`
- `disponibilidad-publicacion`
- utilidades compartidas e interceptor

### Zonas de tests menos prioritarias por ahora
- tests ornamentales o que solo repitan delegación trivial sin aportar nueva información.

---

## Lectura correcta de los malos olores sospechados por el usuario
El usuario expresó una intuición importante: que parte del trabajo reciente, aunque necesario, pudo haber introducido:
- torpeza arquitectónica;
- acoplamientos extraños;
- capas demasiado pesadas;
- o complejidad más alta de la necesaria.

La próxima IA debe tomar esa intuición en serio.

### Pero con una regla clara
No convertir esa intuición en excusa para reescribir todo.

### Enfoque correcto
Primero:
- compilar mentalmente o revisar coherencia real;
- detectar qué partes sí son ruido;
- distinguir complejidad necesaria de complejidad artificial.

Después:
- simplificar solo donde el beneficio sea claro.

### Zonas candidatas a sobreingeniería
- `CatalogoPublicoService`
- flujo completo de `media`
- cantidad de utilidades pequeñas o enums dispersos
- mappers demasiado triviales
- tests muy atados a implementación interna

---

## Qué debe quedar bien documentado por la próxima IA al final del backend
Cuando la próxima IA termine su pasada fuerte sobre backend-farmacia, debe dejar por escrito algo como esto:

1. **Qué partes del backend quedaron razonablemente estables**.
2. **Qué partes siguen frágiles pero aceptables para pasar al frontend**.
3. **Qué decisiones de diseño quedaron ratificadas**.
4. **Qué riesgos se posponen conscientemente**.
5. **Qué no debe reinterpretar la siguiente fase del frontend**.

Esto es importante porque el frontend de farmacia no debe arrancar sobre ambigüedades silenciosas.

---

## Resumen operativo de esta segunda tanda
La próxima IA debe asumir que el backend-farmacia ya tiene:
- una arquitectura sembrada;
- decisiones de dominio relevantes;
- contratos públicos bastante definidos;
- auth admin encaminada;
- media encaminada;
- BD extendida conceptualmente;
- tests iniciales suficientes.

Pero todavía necesita:
- consolidación técnica;
- limpieza prudente;
- alineación real entre SQL, código y tests;
- detección explícita de los puntos que pueden estallar.

La siguiente fase **todavía es backend**, no frontend.

---

# Segunda tanda ampliada: verificación quirúrgica archivo por archivo

## Propósito de esta ampliación
Esta ampliación existe para que la próxima IA tenga una lista más concreta de **qué mirar dentro del código** y **qué tipo de fallas esperar**.

No es una lista de cambios obligatorios. Es una guía de inspección altamente específica.

---

## 1. `src/main.ts`

### Qué corroborar
- Que `app.use('/media', expressStatic(resolve(process.cwd(), storageRoot)))` exista y no entre en conflicto con `apiPrefix`.
- Que el `GlobalPrefix` no afecte la ruta estática de `/media` si la intención es servirla fuera de `/api/v1`.
- Que `ValidationPipe` esté realmente con:
  - `whitelist: true`
  - `transform: true`
  - `forbidNonWhitelisted: true`
- Que el orden de `CorrelationIdInterceptor`, `ApiResponseInterceptor` y `HttpExceptionFilter` tenga sentido.

### Qué puede estallar
- respuestas duplicadamente envueltas;
- `correlationId` faltando en envelopes ya preenvueltos;
- `storageRoot` resolviendo a una ruta distinta a la esperada en Windows.

---

## 2. `src/app/common/interceptors/api-response.interceptor.ts`

### Qué corroborar
- Que cuando el body ya venga con `ok`, no se envuelva otra vez.
- Que si el body ya trae `timestamp` o `correlationId`, no se pierdan o sobrescriban incorrectamente.
- Que cuando el body trae `data`, `message` o `meta`, se normalice correctamente sin destruir `meta`.

### Qué puede estallar
- tests e2e por envelope distinto al esperado;
- endpoints que ya devuelven `PageResponseDto` y salen con forma distinta al resto.

---

## 3. `src/app/common/filters/http-exception.filter.ts`

### Qué corroborar
- Que `message`, `code`, `details`, `path`, `timestamp` y `correlationId` salgan consistentemente.
- Que excepciones personalizadas (`BusinessRuleException`, `ResourceNotFoundException`, etc.) se serialicen sin perder código de negocio.
- Que el filtro soporte correctamente payloads de excepción que vengan como string y como objeto.

### Qué puede estallar
- frontend futuro esperando `code` y backend devolviendo solo `error`;
- errores de validación de Nest con forma distinta a la de errores de dominio.

---

## 4. `src/app/security/strategies/jwt.strategy.ts`

### Qué corroborar
- Que `adminId` se resuelva correctamente desde `payload.adminId ?? payload.sub`.
- Que `roles` no se quede vacío por un filtro demasiado estricto.
- Que el tipo `AuthenticatedAdminType` coincida exactamente con lo que devuelve `validate()`.
- Que `username` fallback `admin-{id}` no rompa ningún flujo que espere username real.

### Qué puede estallar
- `/me` funcionando conceptualmente pero fallando por payload incompleto;
- tests de auth por desalineación entre `roles: string[]` y enums de rol.

---

## 5. `src/app/modules/auth-admin/entities/usuario-admin.entity.ts`

### Qué corroborar
- Que la tabla se llame realmente `usuario_admin` en SQL.
- Que `rol_codigo` exista con ese nombre exacto.
- Que `password_hash` exista con esa longitud.
- Que `isActivo()` y `getRoles()` no dependan de strings con casing extraño.

### Qué puede estallar
- seed alineado con SQL pero entidad desalineada en nombre de columna;
- `getRoles()` devolviendo un arreglo correcto conceptualmente pero incompatible con tests o guards.

---

## 6. `src/app/modules/auth-admin/repositories/usuario-admin.repository.ts`

### Qué corroborar
- Que exista `findByUsername(...)` y `findById(...)` con nombres de campo correctos.
- Que el repositorio no haga supuestos de schema erróneos.
- Que el wrapper no pierda relaciones o campos necesarios por `select` incompleto.

### Qué puede estallar
- login fallando porque `passwordHash` no llega desde ORM;
- `/me` fallando porque `findById` no encuentra por nombre de columna mal mapeado.

---

## 7. `src/app/modules/auth-admin/services/auth-admin.service.ts`

### Qué corroborar
- Que `compare(password, usuarioAdmin.passwordHash)` sea el flujo real.
- Que `jwtService.signAsync(payload)` reciba payload consistente con `JwtStrategy`.
- Que `resolveExpiresInSeconds()` no tenga discrepancia con `JWT_EXPIRES_IN` real.
- Que `obtenerSesionActual(...)` consulte persistencia y no regrese solo datos del token.

### Qué puede estallar
- imports de `compare` o mocks de bcrypt;
- `LoginAdminResponseDto` desalineado con `AuthAdminMapper`;
- token correcto pero sesión actual inconsistente.

---

## 8. `src/app/modules/media/entities/media-recurso.entity.ts`

### Qué corroborar
- Que las columnas coincidan con:
  - `producto_id`
  - `tipo_recurso`
  - `nombre_original`
  - `nombre_archivo`
  - `mime_type`
  - `extension`
  - `tamano_bytes`
  - `ruta_relativa`
  - `url_publica`
- Que `productoId` sea nullable de verdad.
- Que la relación a `ProductoEntity` use `productoId` y no otro nombre.

### Qué puede estallar
- SQL usando `BIGINT` y entidad usando `integer`;
- incompatibilidades de longitud o nombres exactos.

---

## 9. `src/app/modules/media/services/media.service.ts`

### Qué corroborar
- Que `findProductoOrFail()` use el repositorio correcto y la columna correcta.
- Que `findMediaOrFail()` no pierda el recurso por criterios de búsqueda demasiado estrechos.
- Que al asociar y reemplazar no queden dos medias activas apuntando al mismo producto sin querer.
- Que el flujo de desasociación en reemplazo sea coherente.

### Qué puede estallar
- `findByProductoId` devolviendo la media equivocada si hay más de una histórica;
- reemplazo correcto conceptualmente pero dejando registros inconsistentes.

---

## 10. `src/app/modules/media/storage/filesystem-storage.provider.ts`

### Qué corroborar
- Que se use `file.buffer`, por lo que el interceptor realmente debe entregar el archivo en memoria.
- Que `storage.root` se combine bien con `rutaRelativa`.
- Que el `publicBaseUrl` no duplique `/media` si también existe `MEDIA_BASE_URL`.
- Que la extensión se derive bien del archivo o del MIME type.

### Qué puede estallar
- subida funcionando pero `urlPublica` mal construida;
- diferencias entre Windows y Linux por `\` y `/`.

---

## 11. `src/app/modules/catalogo-publico/services/catalogo-publico.service.ts`

### Qué corroborar con mucho detalle
#### En `listarCategoriasPublicas()`
- Que `innerJoin('producto.categoria', 'categoria')` coincida con la relación real en `ProductoEntity`.
- Que los nombres usados en `select('categoria.categoria_id', ...)` y `addSelect('categoria.nombre_categoria', ...)` coincidan con la tabla real.
- Que `COUNT(producto.producto_id)` tenga sentido con el alias generado por PostgreSQL/TypeORM.

#### En `listar()` / `buscar()`
- Que `TextNormalizationUtil.normalizeOptional(query.q)` no genere `null` donde se esperaba `undefined`.
- Que la búsqueda `ILIKE` sea compatible con PostgreSQL y los campos reales.
- Que `categoria.nombre_categoria` exista en el join.
- Que `skip/take` con `getManyAndCount()` no se rompa por el join.

#### En `obtenerProductoPublico()`
- Que el `qb.andWhere('producto.producto_id = :productoId', ...)` use el nombre correcto.
- Que el lookup de imagen posterior no introduzca `null` inesperado por tipo o clave.

#### En `findImagenesPrincipalesByProductoIds()`
- Que `In(productoIds)` sea aceptado sin problemas por el tipo de `productoId`.
- Que la política “última media por `mediaRecursoId DESC`” sea válida para V1.
- Que `tipoRecurso: TipoRecursoMediaEnum.IMAGEN_PRODUCTO` coincida con el tipo real guardado en DB.

### Qué puede estallar
- nombres de columna incorrectos en query builder;
- `In(...)` bien escrito pero rompiendo por tipo o mapping;
- `imagenUrl` nunca llenándose aunque haya media en DB;
- conteo y paginación correctos conceptualmente, pero con bugs por joins.

---

## 12. `src/app/modules/categorias/services/categorias.service.ts`

### Qué corroborar
- Que la normalización de texto no contradiga la longitud real del SQL.
- Que `ensureCanCreate` y `ensureCanUpdate` sigan teniendo sentido con el DTO ya alineado.
- Que `toResponseDto()` no pierda `null` vs `undefined` en `descripcionBreve`.

### Qué puede estallar
- reglas de duplicado funcionando mal si el repositorio normaliza distinto que el servicio.

---

## 13. `src/app/modules/productos/services/productos.service.ts`

### Qué corroborar
- Que el producto nazca con:
  - `estadoProducto = ACTIVO`
  - `esPublicable = false`
  - `estadoDisponibilidad = NO_PUBLICADO`
- Que `normalizePrice()` no entre en conflicto con numeric/decimal del driver.
- Que `cambiarEstado(INACTIVO)` fuerce realmente la despublicación.
- Que `listar()` delegue filtros correctos al repositorio.

### Qué puede estallar
- `precioVisible` llegando como string desde DB y generando tests raros;
- reglas de publicación duplicadas entre `productos` y `disponibilidad-publicacion`.

---

## 14. `src/app/modules/productos/repositories/producto.repository.ts`

### Qué corroborar
- Que la identidad natural usada en búsquedas por duplicado coincida con la restricción pensada en entidad/SQL.
- Que `findPaginated(...)` soporte todos los filtros sembrados:
  - `q`
  - `categoriaId`
  - `estadoProducto`
  - `esPublicable`
  - `estadoDisponibilidad`
- Que ordene por columnas reales.

### Qué puede estallar
- filtros declarados en DTO que el repositorio no soporta realmente;
- ordenamiento por alias/columna inexistente.

---

## 15. `src/app/modules/disponibilidad-publicacion/validation/publicacion-rules.validator.ts`

### Qué corroborar
- Que `ensureCanPublish(...)` no deje publicar un INACTIVO.
- Que `ensureCanUnpublish(...)` detecte bien el caso “ya despublicado”.
- Que `ensureCanUpdateDisponibilidad(...)` rechace `NO_PUBLICADO` como flujo normal.
- Que los códigos/mensajes de negocio sean razonables y no contradictorios.

### Qué puede estallar
- doble fuente de verdad entre validator y `ProductoPublicacionUtil`;
- tests pasando con mocks pero reglas reales duplicadas o desalineadas.

---

## 16. `src/app/common/utils/producto-publicacion.util.ts`

### Qué corroborar
- Que siga siendo la fuente central de:
  - normalización de estado producto;
  - normalización de disponibilidad;
  - visibilidad pública;
  - estado de despublicación.
- Que `ESTADOS_VISIBLES_PUBLICAMENTE` sea realmente el conjunto usado por catálogo y publicación.

### Qué puede estallar
- que el sistema termine con tres sitios definiendo la misma semántica;
- que la utilidad exista pero no se use de forma consistente.

---

## 17. Tests más sensibles a desalineación

### `test/unit/catalogo-publico/catalogo-publico.service.spec.ts`
Mirar si:
- el mock del query builder sigue cubriendo lo que el servicio realmente hace;
- la expectativa de `imagenUrl` coincide con la política actual;
- el test no quedó demasiado amarrado a implementación interna.

### `test/unit/auth-admin/auth-admin.service.spec.ts`
Mirar si:
- los mocks de `bcrypt.compare` siguen correctos;
- el DTO esperado coincide con el mapper real;
- `expiresInSeconds` sigue alineado con `JWT_EXPIRES_IN`.

### `test/unit/media/media.service.spec.ts`
Mirar si:
- el flujo de `findById -> save -> findById` sigue igual;
- el test presupone más de lo que el servicio garantiza;
- la entidad mockeada coincide con la entidad real.

---

## Señal práctica de que esta segunda tanda ya sirvió
La próxima IA debería poder responder, después de revisar esto:
- qué archivos están bien solo conceptualmente;
- cuáles necesitan corrección concreta;
- cuáles deben simplificarse;
- cuáles están listos para sostener el frontend de farmacia.

Ese es el objetivo de esta tanda ampliada.



---

# Tercera tanda: mapa de decisiones tomadas, límites de reinterpretación y secuencia de trabajo

## Propósito de esta tercera tanda
Esta tercera tanda busca dejarle a la próxima IA algo que normalmente falta en muchos handoffs:
- **qué decisiones ya se consideran suficientemente tomadas**;
- **qué cosas no debe reinterpretar por capricho**;
- **qué refactors sí están permitidos**;
- **qué refactors no convienen todavía**;
- y **en qué orden preciso conviene seguir trabajando dentro del backend-farmacia**.

El objetivo no es congelar el proyecto artificialmente, sino evitar que la siguiente IA convierta una fase de consolidación en una fase de reinvención.

---

## 1. Decisiones que, por defecto, deben considerarse tomadas
La próxima IA puede revisarlas, pero no debería cambiarlas salvo que encuentre una razón técnica fuerte.

### 1.1. Superficie pública separada de superficie administrativa
Esto ya no debe discutirse de nuevo salvo hallazgo serio.

La farmacia tiene dos superficies principales:
- **administrativa**
- **pública**

La pública no debe arrastrar lógica administrativa y la administrativa no debe contaminar los contratos públicos.

### 1.2. Semántica de visibilidad pública ya prácticamente definida
La visibilidad pública está pensada alrededor de esta combinación:
- `estadoProducto = ACTIVO`
- `esPublicable = true`
- `estadoDisponibilidad in (DISPONIBLE, AGOTADO)`

La próxima IA no debe inventar otra semántica paralela en frontend o en otro módulo del backend.

### 1.3. `NO_PUBLICADO` es estado interno, no disponibilidad pública ordinaria
Esto ya debe tratarse como una decisión base.

La próxima IA no debería hacer que:
- el catálogo público lo trate como visible;
- el frontend lo muestre como estado comercial normal;
- `actualizar-disponibilidad` lo acepte como valor operativo estándar.

### 1.4. La imagen principal pública debe venir resuelta desde backend
La próxima IA no debería empujar al frontend a resolver imágenes mediante varias llamadas, búsquedas raras o reglas ocultas.

La idea base sigue siendo:
- backend devuelve `imagenUrl`;
- si no hay imagen, devuelve `null`;
- frontend pone placeholder visual.

### 1.5. El núcleo SQL normalizado se conserva como base formal
La próxima IA no debería borrar o reinterpretar alegremente el trabajo de normalización previo del usuario.

El criterio vigente es:
- mantener el SQL formal como base;
- sumar extensión operativa si hace falta;
- no deshacer el enfoque 3FN por conveniencia puntual del backend.

### 1.6. `DB_SYNC=false` debe seguir siendo la política normal
La próxima IA no debería “resolver rápido” activando sincronización automática del ORM como salida fácil.

Si algo no encaja entre entidades y SQL, se corrige esa desalineación. No se tapa con sincronización automática.

### 1.7. No meter Flyway ahora salvo decisión estratégica explícita
La discusión ya pasó por ese punto.
La postura operativa vigente es:
- no abrir ese frente ahora;
- mantener disciplina de migraciones como idea;
- seguir consolidando el backend actual sin introducir una nueva capa de complejidad estratégica.

---

## 2. Cosas que la próxima IA no debe reinterpretar sin una justificación fuerte

## 2.1. No debe reinterpretar el backend-farmacia como si todavía estuviera vacío
Ya hay módulos sembrados, contratos pensados y reglas empujadas.
No toca reinventar. Toca consolidar.

## 2.2. No debe reinterpretar `catalogo-publico` como simple CRUD público
Ese módulo no es un “read-only genérico”.
Es una superficie de producto pensada para sostener un storefront futuro. Por eso importa el contrato visual y la semántica pública.

## 2.3. No debe reinterpretar `media` como almacenamiento decorativo
Aunque pueda simplificarse luego, el módulo se sembró con intención funcional real:
- subir;
- registrar;
- asociar/reemplazar;
- exponer URL pública.

No tratarlo como un simple campo texto de imagen pegada a mano.

## 2.4. No debe reinterpretar tests sembrados como si fueran la verdad absoluta
Los tests actuales son guía útil, no prueba de perfección.
La próxima IA debe usarlos para detectar intención y luego corregir lo que haga falta.

## 2.5. No debe interpretar cada enum o utilidad pequeña como necesariamente “buena” o necesariamente “mala”
Algunas utilidades probablemente ayudan a centralizar reglas.
Otras podrían sobrar.
La próxima IA debe evaluar caso por caso, no barrerlas todas ni conservarlas todas por reflejo.

---

## 3. Refactors permitidos en la siguiente fase
Estos refactors sí están permitidos, incluso recomendados, si la próxima IA confirma que aportan valor real.

## 3.1. Consolidar imports y rutas relativas frágiles
Permitido y recomendado.
Especialmente si hay imports largos, frágiles o con alta probabilidad de romper compilación.

## 3.2. Corregir nombres de columnas, tipos y longitudes para alinear SQL ↔ entidades ↔ DTOs
Totalmente permitido.
Esto no es capricho; es consolidación de base.

## 3.3. Reducir duplicación de reglas entre módulos
Permitido si realmente reduce duplicación sin oscurecer el dominio.
Por ejemplo, en publicación y visibilidad pública.

## 3.4. Simplificar mappers triviales si solo agregan ruido
Permitido, pero con criterio.
No borrar mappers útiles solo por estética.

## 3.5. Extraer pequeñas funciones privadas o utilidades locales dentro de servicios pesados
Permitido si ayuda a legibilidad sin crear otra capa innecesaria.
Esto aplica sobre todo a `CatalogoPublicoService`.

## 3.6. Ajustar el flujo de `media` para V1 si se demuestra exceso de complejidad
Permitido, pero con una regla:
la simplificación debe ser **deliberada** y **justificada**, no improvisada.

---

## 4. Refactors prohibidos o desaconsejados por ahora

## 4.1. Reescritura completa del backend-farmacia
No hacerlo.
No es el momento.

## 4.2. Cambiar de stack de migraciones/herramienta estratégica
No meter Flyway ni otra herramienta de gobernanza del esquema ahora, salvo decisión muy explícita del usuario.

## 4.3. Reorganización masiva de carpetas por gusto
No conviene todavía.
Primero consolidar la coherencia técnica de lo que ya existe.

## 4.4. Introducir más módulos nuevos salvo necesidad real
No conviene abrir más superficies ahora.
El backend ya tiene suficientes zonas para consolidar.

## 4.5. Refactor de “elegancia” que rompa continuidad del trabajo
No toca optimizar por pureza arquitectónica si eso rompe continuidad y retrasa el frontend.

## 4.6. Pasar al frontend sin dejar contratos públicos razonablemente firmes
Tampoco conviene.
La próxima IA debe resistir la tentación de empezar Angular antes de que catálogo público, categorías públicas e imágenes públicas estén suficientemente firmes.

---

## 5. Secuencia de trabajo recomendada dentro del backend-farmacia
Este orden está pensado para maximizar estabilidad antes del frontend.

## Paso 1. Verificar infraestructura mínima del backend
Primero mirar:
- `src/main.ts`
- `configuration.ts`
- `data-source.ts`
- `.env.example`
- config local example

### Qué decidir aquí
- si `DB_SCHEMA=farmacia` está correctamente propagado;
- si `/media` se sirve fuera de `apiPrefix` como se pretende;
- si la configuración de storage y URLs es consistente.

## Paso 2. Verificar auth-admin de punta a punta a nivel conceptual
Antes de frontend, conviene saber si la auth admin tiene sentido real.

Orden sugerido:
1. `UsuarioAdminEntity`
2. seed SQL `usuario_admin`
3. repositorio
4. `AuthAdminService`
5. `JwtStrategy`
6. DTOs / mapper
7. tests asociados

### Qué debe salir de aquí
Una respuesta clara a esta pregunta:
> “¿La auth administrativa está solo sembrada, o ya está realmente coherente?”

## Paso 3. Verificar media como soporte real del catálogo público
Orden sugerido:
1. entidad `media_recurso`
2. SQL extension
3. repositorio de media
4. storage provider
5. service
6. controller
7. validator

### Qué debe salir de aquí
Una respuesta clara a esta pregunta:
> “¿El sistema de media actual sostiene V1 razonablemente, o conviene simplificarlo antes del frontend?”

## Paso 4. Verificar `catalogo-publico` como contrato de integración
Esto probablemente es la parte más importante antes del frontend.

Orden sugerido:
1. DTOs públicos
2. mapper
3. service
4. categories públicas
5. tests de catálogo

### Qué debe salir de aquí
Una respuesta clara a estas preguntas:
- ¿`imagenUrl` sale donde debe salir?
- ¿las categorías públicas están bien resueltas?
- ¿el contrato de listado y detalle ya está suficientemente estable para Angular?

## Paso 5. Revisar `productos` y `disponibilidad-publicacion` como soporte del contrato público
Aquí no se trata tanto de frontend directo, sino de garantizar que las reglas del catálogo se sostienen correctamente.

Orden sugerido:
1. entidad producto
2. DTOs
3. `ProductoRulesValidator`
4. `ProductosService`
5. `PublicacionRulesValidator`
6. `DisponibilidadPublicacionService`

### Qué debe salir de aquí
Una respuesta clara a esta pregunta:
> “¿La semántica pública y la semántica administrativa realmente coinciden?”

## Paso 6. Recién entonces revisar `categorias`
Categorías importan, pero no tienen prioridad más alta que catálogo público, media y auth.

---

## 6. Señales concretas para decidir si una pieza está lista o no

## 6.1. Señales de que `catalogo-publico` está listo para frontend
- `imagenUrl` sale consistentemente o `null` explícito;
- `categoriaId` y `nombreCategoria` salen bien;
- `page`/`limit` ya están firmes;
- la búsqueda pública no tiene ambigüedades básicas;
- categorías públicas tienen contrato claro.

## 6.2. Señales de que `media` está aceptable para V1
- la política de imagen principal está clara;
- la URL pública se construye sin ambigüedad;
- el flujo no genera estados incoherentes;
- no obliga al frontend a resolver nada raro.

## 6.3. Señales de que `auth-admin` está aceptable para seguir
- la entidad y el seed coinciden;
- `bcrypt.compare(...)` está realmente integrado;
- el JWT devuelve datos que `/me` sabe revalidar;
- roles y ids no tienen ambigüedad.

---

## 7. Qué debe quedar explicitado para el futuro frontend de farmacia
La próxima IA, cuando cierre backend, debería dejar una mini-sección o nota con estas decisiones ya cristalizadas:

### Contrato público que el frontend debe asumir
- endpoint de listado catálogo;
- endpoint de búsqueda;
- endpoint de detalle;
- endpoint de categorías públicas;
- significado exacto de `imagenUrl`, `disponible`, `estadoDisponibilidad`, `page`, `limit`.

### Estados que el frontend no debe reinterpretar
- `NO_PUBLICADO` no es estado público visual normal;
- `imagenUrl = null` implica placeholder del lado frontend;
- visibilidad pública no se decide en Angular.

### Qué no debe pedir el frontend al backend si todavía no existe
- galerías complejas;
- slugs si aún no fueron aprobados;
- contratos más ricos de media si no fueron consolidados.

---

## 8. Qué debe hacer la próxima IA cuando detecte un problema
Para evitar caos, la próxima IA debería clasificar cada problema en una de estas cuatro categorías:

### A. Desalineación simple
Ejemplo:
- import roto;
- tipo mal importado;
- longitud inconsistente.

**Acción**: corregir directamente.

### B. Desalineación estructural moderada
Ejemplo:
- SQL y entidad no coinciden del todo;
- DTO y mapper no coinciden;
- tests reflejan una forma antigua del contrato.

**Acción**: alinear y dejar nota de qué cambió.

### C. Complejidad innecesaria sospechosa
Ejemplo:
- un flujo o capa parece sobrecomplicado para V1.

**Acción**: evaluar primero, simplificar solo si la mejora es clara.

### D. Decisión de arquitectura que cambia producto o estrategia
Ejemplo:
- meter Flyway;
- cambiar semántica de publicación;
- rediseñar por completo media;
- cambiar el contrato público de catálogo.

**Acción**: no hacerlo sin justificación fuerte y clara.

---

## 9. Meta final real antes de salir del backend-farmacia
La meta final de esta fase no es perfección teórica.
La meta real es dejar el backend-farmacia así:

- suficientemente consistente para sostener frontend;
- suficientemente limpio para no arrastrar caos al storefront;
- suficientemente documentado para saber qué riesgos quedan;
- suficientemente estable para que la siguiente fase no vuelva a discutir todo desde cero.

---

## Cierre de esta tercera tanda
Si la próxima IA usa bien las tres tandas de este lienzo, debería tener:
1. una visión estratégica del objetivo;
2. una visión operativa de las áreas del backend;
3. una guía quirúrgica archivo por archivo;
4. un mapa de decisiones ya tomadas;
5. y límites claros de qué puede y qué no puede reinterpretar.

Ese es el propósito completo de este handoff.

