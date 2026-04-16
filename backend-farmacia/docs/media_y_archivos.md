# Media y archivos

## Propósito

Definir cómo debe manejar `backend-farmacia` las imágenes y archivos asociados al catálogo, dejando claro dónde se almacenan, cómo se exponen y cómo se relacionan con los productos sin contaminar los DTOs ni la base de datos con decisiones poco sanas para la V1.

## Principio general

En este sistema, las imágenes del catálogo forman parte real de la experiencia del producto, pero eso no significa que deban meterse como binario dentro de cada respuesta JSON ni guardarse de cualquier forma improvisada.

La política oficial de este backend es:

- el backend administra los archivos;
- la base de datos guarda referencia y metadata útil;
- el frontend consume una URL servida por el backend;
- el DTO devuelve metadata e `imagenUrl`, no el archivo crudo;
- la lógica de archivos queda encapsulada en un módulo formal.

## Qué tipo de archivos se contemplan en V1.0

### Imágenes de productos
Son el caso principal de esta etapa.

### Otros archivos
No forman parte del núcleo de V1.0 salvo que más adelante se justifiquen. Este documento se centra sobre todo en imágenes de catálogo.

## Dónde se almacenan los archivos

La decisión actual para V1.0 es almacenar los archivos en el **filesystem controlado por el backend**, no como binario en PostgreSQL.

### Variables relevantes
- `STORAGE_ROOT`: raíz física del almacenamiento.
- `MEDIA_BASE_URL`: base pública desde la que se sirven los archivos.

## Qué se guarda en la base de datos

La base de datos no guarda el archivo binario principal en esta V1. Lo que conviene guardar es una **referencia**.

### Datos útiles
- nombre de archivo;
- ruta relativa;
- URL pública controlada;
- tipo MIME;
- extensión;
- tamaño en bytes;
- relación opcional con producto.

## Relación con `database-farmacia`

La implementación actual usa la tabla dedicada `media_recurso`, alineada con el soporte SQL sembrado para farmacia. Eso deja más limpio el crecimiento futuro que intentar meter la imagen como columna accidental dentro de `producto`.

## Cómo se expone al frontend

La regla oficial es:

- el frontend pide productos;
- el backend devuelve DTOs con datos del producto;
- dentro del DTO aparece `imagenUrl` cuando existe una imagen principal asociada;
- el frontend usa esa URL para cargar la imagen por separado.

## Decisiones operativas actuales

### Carga multipart en memoria
El módulo usa `memoryStorage`, de modo que `file.buffer` exista de forma explícita durante el flujo de subida.

### Límite de tamaño centralizado
El límite de subida se define desde configuración con `MAX_FILE_SIZE_MB`.

### URL pública tolerante
La construcción de URLs tolera dos escenarios:
- que `MEDIA_BASE_URL` ya termine en `/media`;
- que se reciba solo la base pública del backend y el resolutor añada `/media`.

## Qué no debe hacerse

### No incrustar imágenes en JSON
No conviene enviar imágenes en base64 dentro de los DTOs normales del catálogo.

### No exponer rutas internas del sistema
El frontend no debe conocer rutas crudas del filesystem ni detalles accidentales del almacenamiento.

### No mezclar media con lógica de producto sin frontera clara
El producto referencia o usa media, pero media conserva su responsabilidad técnica propia.

## Responsabilidades del módulo `media`

El módulo de media debe poder encargarse de:

- recibir el archivo;
- validar el tipo de archivo;
- validar el tamaño;
- almacenar el archivo;
- generar referencia servible;
- asociar o reemplazar imagen principal del producto;
- exponer metadata útil para contratos admin y públicos.

## Qué devuelve la API

### En contratos admin
Puede devolver metadata más rica, por ejemplo:
- identificador del recurso;
- producto asociado;
- nombre de archivo lógico;
- tipo de archivo;
- URL servida.

### En contratos públicos
Debe devolver solo lo necesario, por ejemplo:
- `imagenUrl`

## Consideraciones de validación

El backend valida al menos:

- archivo presente;
- buffer cargado correctamente;
- tipo permitido;
- tamaño aceptable;
- producto existente;
- asociación coherente con el producto.

## Consideraciones de seguridad

### Superficie admin
La carga, reemplazo y asociación de archivos es privada y protegida.

### Superficie pública
La lectura pública se limita a los archivos realmente expuestos por el catálogo visible.

## Relación con V1.1

La evolución a V1.1 podría incluir:

- más de una imagen por producto;
- metadata más rica;
- mejor gestión de archivos;
- limpieza o reemplazo más avanzada.

Pero V1.0 debe mantenerse simple y usable.

## Qué no debe pasar

- imágenes guardadas en DTOs JSON como base64 por costumbre;
- rutas internas del servidor expuestas como si fueran contratos estables;
- archivos huérfanos sin relación con producto;
- carga de archivos sin validación;
- media dispersa en utilidades sueltas sin módulo formal.

## Resultado esperado

El manejo de media y archivos del backend-farmacia debe dejar una solución clásica, limpia y profesional para la V1: el backend administra imágenes, la base guarda referencias útiles y el frontend consume URLs estables sin conocer detalles accidentales del almacenamiento.
