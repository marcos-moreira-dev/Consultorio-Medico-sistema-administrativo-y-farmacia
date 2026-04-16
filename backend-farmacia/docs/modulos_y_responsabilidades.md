# Módulos y responsabilidades

## Propósito

Definir los módulos funcionales oficiales de `backend-farmacia` y dejar explícito qué responsabilidad tiene cada uno, qué no debe hacer y con qué otros módulos se relaciona.

## Principio general

Cada módulo debe existir porque resuelve un área concreta del backend. No conviene crear módulos por capricho, pero tampoco conviene mezclar demasiadas responsabilidades en uno solo.

En farmacia esto es especialmente importante porque hay dos superficies distintas:

- una pública sin login;
- una administrativa privada con login.

## Módulo `auth-admin`

### Responsabilidad
Gestionar autenticación del usuario administrativo de farmacia.

### Incluye
- login admin;
- emisión de JWT;
- validación técnica del token;
- integración con guards o seguridad de Nest;
- flujo de sesión técnica del backend administrativo.

### No debe hacer
- absorber gestión completa de productos o categorías;
- mezclarse con la lógica pública del catálogo.

## Módulo `categorias`

### Responsabilidad
Gestionar la clasificación simple del catálogo.

### Incluye
- alta de categoría;
- consulta;
- edición básica;
- activación e inactivación si el diseño lo contempla.

### No debe hacer
- reemplazar al módulo de productos;
- cargar lógica de media o disponibilidad que no le corresponde.

## Módulo `productos`

### Responsabilidad
Gestionar el núcleo comercial de la farmacia.

### Incluye
- crear producto;
- editar producto;
- consultar producto;
- listar productos;
- cambiar estado del producto;
- relacionar producto con categoría;
- mantener su información comercial básica.

### No debe hacer
- exponerse automáticamente como catálogo público sin pasar por reglas de publicación;
- absorber toda la lógica de imágenes como si fueran parte accidental del producto.

## Módulo `media`

### Responsabilidad
Gestionar imágenes y archivos asociados al catálogo.

### Incluye
- recibir imagen;
- validar archivo;
- almacenar archivo;
- exponer URL o ruta servible;
- asociar imagen a producto.

### No debe hacer
- mezclarse con la lógica pública del catálogo como si fuera el catálogo mismo;
- meter binarios dentro de DTOs JSON;
- volverse un sistema genérico de archivos sin control.

## Módulo `catalogo-publico`

### Responsabilidad
Exponer la superficie pública de farmacia para consulta sin login.

### Incluye
- listado público de productos visibles;
- consulta pública de producto;
- búsqueda simple;
- exposición de datos públicos del catálogo.

### No debe hacer
- exponer información administrativa innecesaria;
- permitir cambios;
- ignorar reglas de publicación y disponibilidad.

## Módulo `disponibilidad-publicacion`

### Responsabilidad
Coordinar la lógica que separa:
- existencia interna del producto;
- visibilidad pública;
- disponibilidad operativa.

### Incluye
- publicar o despublicar producto;
- reflejar disponibilidad;
- mantener coherencia entre estado del producto y su presencia pública.

### No debe hacer
- reemplazar al módulo de productos;
- mezclar reserva o pedidos de V1.1 dentro de V1.0.

## Evolución prevista a V1.1

### Módulo `reservas`
Se considera una evolución formal de V1.1.

### Responsabilidad futura
- crear reserva;
- cancelar reserva;
- concretar reserva;
- integrar reserva con disponibilidad de producto.

### Importante
Este módulo no debe contaminar V1.0. Puede mencionarse como evolución, pero no asumirse implementado desde el inicio.

## Relaciones principales entre módulos

- `auth-admin` protege la superficie administrativa;
- `categorias` apoya estructuralmente a `productos`;
- `productos` depende de `categorias` y puede relacionarse con `media`;
- `catalogo-publico` consume información controlada de `productos` y `disponibilidad-publicacion`;
- `disponibilidad-publicacion` consume información de `productos` y gobierna qué llega al catálogo público;
- `media` complementa a `productos`, pero no debe dominar el modelo de negocio.

## Qué debe evitarse

- que `auth-admin` termine absorbiendo toda la parte admin sin frontera clara;
- que `productos` absorba también publicación, media y catálogo público sin separación conceptual;
- que `catalogo-publico` duplique lógica administrativa;
- que `media` sea un rincón improvisado sin reglas técnicas ni de negocio.

## Resultado esperado

Los módulos y sus responsabilidades deben permitir que el backend-farmacia se implemente con claridad mental y estructural, distinguiendo nítidamente el catálogo público, la administración interna y la gestión de archivos asociados al producto.

