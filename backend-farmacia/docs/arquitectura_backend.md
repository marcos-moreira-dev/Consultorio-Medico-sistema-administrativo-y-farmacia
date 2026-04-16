# Arquitectura backend

## Propósito

Definir la arquitectura interna oficial de `backend-farmacia`, de modo que el componente se implemente con una estructura clara, coherente con Nest y TypeORM, y suficientemente robusta para soportar evolución sin degradarse en desorden.

## Estilo arquitectónico adoptado

El backend de farmacia se implementará como un **monolito modular por capas**.

Eso significa dos cosas al mismo tiempo:

### 1. Monolito modular
Todo el backend vive dentro de una sola aplicación Nest, pero organizado en módulos funcionales bien separados.

### 2. Arquitectura por capas
Dentro de cada módulo, la lógica se distribuye en capas con responsabilidades distintas.

## Razones de esta decisión

### 1. Claridad pedagógica
Este estilo permite estudiar mejor qué hace cada parte del backend.

### 2. Realismo profesional
Es una arquitectura muy típica para sistemas administrativos o comerciales serios.

### 3. Menor complejidad operativa
Evita la sobrecarga de microservicios en una V1 donde no hacen falta.

### 4. Evolución razonable
Permite crecer hacia V1.1 con migraciones y ampliación de módulos sin rehacer todo el sistema.

## Principio rector

La arquitectura debe impedir, o al menos dificultar mucho, estos errores:

- lógica de negocio metida en controllers;
- entidades TypeORM expuestas como response HTTP;
- validación crítica escondida en frontend;
- mezcla de seguridad con lógica de catálogo de forma caótica;
- acoplamiento excesivo entre módulos públicos y admin.

## Capas recomendadas

La arquitectura interna del backend debe separarse, como mínimo, en estas capas:

### Capa de entrada o interfaz
Recibe solicitudes HTTP y devuelve respuestas HTTP.

**Elementos típicos:**
- controllers;
- request DTOs;
- response DTOs;
- configuración de rutas;
- pipes o validaciones de entrada.

**Responsabilidad:**
traducir el mundo HTTP al lenguaje interno del backend.

**No debe hacer:**
- lógica de negocio compleja;
- acceso directo a TypeORM para resolver casos enteros;
- decidir reglas del dominio por su cuenta.

### Capa de aplicación
Coordina casos de uso y orquesta el comportamiento del sistema.

**Elementos típicos:**
- services de aplicación;
- casos de uso cuando haga falta explicitarlos;
- criterios o comandos internos.

**Responsabilidad:**
encadenar pasos del negocio y coordinar módulos internos.

**No debe hacer:**
- lógica HTTP;
- detalles de serialización;
- mezclarse con detalles accidentales de infraestructura.

### Capa de dominio
Expresa el significado del negocio y sus reglas centrales.

**Elementos típicos:**
- entidades del dominio o entidades persistentes con criterio;
- enums de negocio;
- reglas invariantes;
- conceptos como producto, categoría, disponibilidad y publicación.

**Responsabilidad:**
representar el problema de farmacia con claridad.

**No debe hacer:**
- hablar HTTP;
- depender del frontend;
- absorber configuración técnica irrelevante.

### Capa de infraestructura
Materializa persistencia, seguridad técnica, manejo de media, OpenAPI y otros detalles de implementación.

**Elementos típicos:**
- repositories;
- adapters;
- configuración de JWT y guards;
- almacenamiento de archivos;
- exposición de media;
- configuración de Swagger;
- logging técnico.

**Responsabilidad:**
resolver el “cómo” técnico del backend.

## Modularidad funcional esperada

La arquitectura por capas debe convivir con módulos funcionales explícitos.

Módulos principales en V1.0:
- auth-admin
- categorias
- productos
- media
- catalogo-publico
- disponibilidad-publicacion

V1.1 incorporará al menos:
- reservas

## Relación entre módulos y capas

No conviene pensar “capas o módulos”, sino “capas dentro de módulos” o una mezcla equivalente suficientemente clara.

Eso significa que cada módulo puede tener:
- controller;
- service o use case;
- dto;
- mapper;
- entity;
- repository;
- validadores específicos;
- estrategias o adaptadores cuando realmente hagan falta.

## Decisiones arquitectónicas importantes

### 1. DTOs obligatorios en la frontera HTTP
No se exponen entidades TypeORM directamente.

### 2. Seguridad transversal, pero no invasiva
La autenticación administrativa debe integrarse bien sin contaminar innecesariamente toda la lógica pública del catálogo.

### 3. Módulos públicos y admin con fronteras claras
No conviene mezclar controladores públicos y admin como si fueran el mismo tipo de superficie.

### 4. Media como responsabilidad formal
Las imágenes no deben quedar regadas como archivos sueltos sin módulo ni contrato.

### 5. Trazabilidad desde diseño
Logs, correlation_id y contexto operativo deben contemplarse como parte real del backend.

## Lo que no se busca en esta V1

- microservicios;
- colas o workers sin necesidad real;
- cliente autenticado en V1.0;
- pedidos, pagos o delivery en V1.0;
- una estructura empresarial de cadena farmacéutica compleja.

## Relación con Nest

La arquitectura debe sentirse natural dentro de Nest:

- módulos claros;
- providers bien definidos;
- controllers delgados;
- guards para seguridad admin;
- pipes para validación;
- TypeORM para persistencia principal;
- OpenAPI integrado;
- migraciones de TypeORM como evolución natural de la base.

## Resultado esperado

La arquitectura backend de farmacia debe dejar una base clásica, sólida y muy explícita, lo bastante clara para que una IA la implemente con poca ambigüedad y lo bastante profesional como para que el proyecto sirva realmente como referencia de backend comercial bien hecho.

