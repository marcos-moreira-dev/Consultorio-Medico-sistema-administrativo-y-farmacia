# Onboarding backend

## Propósito

Explicar cómo debe incorporarse alguien a `backend-farmacia`, qué necesita entender primero, qué piezas debe reconocer rápido y cómo empezar a trabajar sin perderse en el proyecto.

## Principio general

Un buen backend no solo debe estar bien hecho. También debe poder leerse, levantarse y comprenderse sin depender de la memoria del autor. El onboarding existe para reducir fricción y acelerar entendimiento.

## Estado operativo mínimo que debe asumirse

Al recibir este componente hoy conviene asumir lo siguiente:

- el backend **compila**;
- el typecheck y los tests principales **pasan**;
- todavía es un borrador y no una versión final cerrada;
- la documentación sigue siendo importante, pero ya no reemplaza al código operativo;
- la BD formal de referencia sigue viviendo en `database-farmacia`.

## Qué debe entender primero una persona nueva

Antes de tocar código, quien entra al proyecto debería entender estas ideas:

### 1. Este backend tiene dos superficies
Una pública para catálogo y otra administrativa protegida.

### 2. Este backend no es el consultorio
No maneja pacientes, citas, atenciones ni cobros.

### 3. Este backend es modular
No debe leerse como una sola masa. Debe recorrerse por módulos funcionales.

### 4. Este backend usa contratos explícitos
DTOs, respuestas uniformes, validaciones y errores no deben improvisarse.

### 5. Este backend administra imágenes
La relación entre productos e imágenes forma parte formal del sistema.

### 6. Este backend separa V1.0 y V1.1
Reservas pertenecen a V1.1 y no deben contaminar la implementación base.

## Ruta sugerida de lectura

Alguien nuevo debería recorrer el backend en este orden mental:

### Paso 1. Leer la documentación del backend
- `README.md`
- `vision_backend.md`
- `arquitectura_backend.md`
- `modulos_y_responsabilidades.md`
- `estructura_de_paquetes.md`

### Paso 2. Entender seguridad
- `seguridad_y_autorizacion.md`
- `auth_y_sesiones.md`

### Paso 3. Entender contratos y lógica
- `contratos_api.md`
- `dto_y_modelos.md`
- `servicios_y_casos_de_uso.md`
- `reglas_de_validacion.md`
- `errores_y_validaciones.md`
- `paginacion_filtros_y_ordenamiento.md`

### Paso 4. Entender soporte transversal
- `media_y_archivos.md`
- `logging_y_trazabilidad.md`
- `testing_backend.md`
- `convenciones_backend.md`

## Arranque local mínimo

### Variables de entorno
Usar `.env.example` como base y completar secretos reales cuando corresponda.

### Comandos base
- `npm install`
- `npm run start:dev`
- `npm run build`
- `npm run typecheck`
- `npm run test:unit`
- `npm run test:e2e -- --runInBand`
- `npm run verify`

### Verificación recomendada antes de pasar contexto a otra IA
Ejecutar `npm run verify`.

## Dependencias conceptuales mínimas

Antes de implementar, conviene entender:

- Nest;
- TypeORM;
- REST clásico;
- JWT y guards;
- PostgreSQL;
- OpenAPI / Swagger;
- storage de archivos a nivel de backend;
- migraciones como evolución futura del proyecto.

No hace falta dominar todo al máximo para leer el proyecto, pero sí reconocer el papel de cada parte.

## Qué componentes del sistema debe ubicar rápido

### Base de datos asociada
`database-farmacia`

### Cliente consumidor principal
`storefront-farmacia-angular`

### Frontera externa
No hay integración directa con consultorio dentro del backend de farmacia.

## Checklist mental de comprensión rápida

Una persona nueva debería poder responder pronto:

- qué módulos tiene el backend;
- qué parte es pública y qué parte es admin;
- cómo se autentica un admin;
- cómo se gestionan categorías y productos;
- cómo se publica o despublica un producto;
- cómo se refleja la disponibilidad;
- cómo se gestionan las imágenes;
- cómo se estructuran los DTOs;
- cómo se maneja la paginación y los errores;
- qué se reserva para V1.1.

## Qué se recomienda revisar en el código primero

- configuración principal del proyecto;
- seguridad;
- módulo `auth-admin`;
- módulo `productos`;
- módulo `catalogo-publico`;
- módulo `disponibilidad-publicacion`;
- módulo `media`.

Ese recorrido da una idea rápida del núcleo del sistema.

## Reglas prácticas para entrar a trabajar

### 1. No romper convenciones
Antes de crear una nueva clase, revisar naming, paquetes, DTOs y política de errores.

### 2. No saltarse capas
No meter lógica de negocio compleja en controller por apuro.

### 3. No exponer entidades directamente
Si algo sale por HTTP, debe revisarse su DTO y contrato.

### 4. No improvisar permisos
Seguridad y autorización deben seguir la política ya fijada de frontera pública/admin.

### 5. No inventar filtros o respuestas incompatibles
Todo debe alinearse con la convención general del backend.

### 6. No mezclar V1.1 dentro de V1.0
Si aparece lógica de reservas, debe marcarse explícitamente como evolución futura.

## Resultado esperado

El onboarding del backend debe permitir que una persona nueva, o una IA que reciba el contexto, pueda orientarse rápido, ubicar responsabilidades y empezar a trabajar sin convertir el proyecto en una caja negra dependiente del autor original.


## Docker local de farmacia

Desde la raíz del repo se puede levantar la stack local con:

```bat
run_all.bat up
```

Esto arranca PostgreSQL, `backend-farmacia` y `storefront-farmacia-angular` con logs adjuntos para diagnóstico rápido.
