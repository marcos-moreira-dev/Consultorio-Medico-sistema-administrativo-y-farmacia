# Arquitectura backend

## Propósito

Definir la arquitectura interna oficial de `backend-consultorio`, de modo que el componente se implemente con una estructura clara, coherente con Spring Boot 4 y suficientemente robusta para soportar evolución sin degradarse en desorden.

## Estilo arquitectónico adoptado

El backend del consultorio se implementará como un **monolito modular por capas**.

Eso significa dos cosas al mismo tiempo:

### 1. Monolito modular
Todo el backend vive dentro de una sola aplicación Spring Boot, pero organizado en módulos funcionales bien separados.

### 2. Arquitectura por capas
Dentro de cada módulo, la lógica se distribuye en capas con responsabilidades distintas.

## Razones de esta decisión

### 1. Claridad pedagógica
Este estilo permite estudiar mejor qué hace cada parte del backend.

### 2. Realismo profesional
Es una arquitectura muy típica para sistemas administrativos serios.

### 3. Menor complejidad operativa
Evita la sobrecarga de microservicios en una V1 donde no hacen falta.

### 4. Evolución razonable
Permite crecer hacia V1.1 con migraciones y ampliación de módulos sin rehacer todo el sistema.

## Principio rector

La arquitectura debe impedir, o al menos dificultar mucho, estos errores:

- lógica de negocio metida en controladores;
- entidades JPA expuestas como response HTTP;
- validación crítica escondida en frontend;
- mezcla de seguridad con lógica de dominio de forma caótica;
- acoplamiento excesivo entre módulos.

## Capas recomendadas

La arquitectura interna del backend debe separarse, como mínimo, en estas capas:

### Capa de entrada o interfaz
Recibe solicitudes HTTP y devuelve respuestas HTTP.

**Elementos típicos:**
- controllers;
- request DTOs;
- response DTOs;
- configuración de rutas;
- anotaciones de validación de entrada.

**Responsabilidad:**
traducir el mundo HTTP al lenguaje interno del backend.

**No debe hacer:**
- lógica de negocio compleja;
- acceso directo a JPA para resolver casos enteros;
- decidir reglas del dominio por su cuenta.

### Capa de aplicación
Coordina casos de uso y orquesta el comportamiento del sistema.

**Elementos típicos:**
- application services;
- use cases;
- comandos o criterios internos si se necesitan.

**Responsabilidad:**
encadenar pasos del negocio y coordinar módulos internos.

**No debe hacer:**
- detalles concretos de persistencia;
- lógica HTTP;
- mezclarse con detalles de serialización o seguridad baja.

### Capa de dominio
Expresa el significado del negocio y sus reglas centrales.

**Elementos típicos:**
- entidades de dominio o entidades persistentes con criterio;
- enums de negocio;
- reglas invariantes;
- conceptos del subdominio.

**Responsabilidad:**
representar el problema del consultorio con claridad.

**No debe hacer:**
- hablar HTTP;
- depender de detalles del frontend;
- contener lógica accidental de infraestructura.

### Capa de infraestructura
Materializa persistencia, seguridad técnica, generación de reportes, integración con archivos y otros detalles de implementación.

**Elementos típicos:**
- repositories;
- adapters;
- configuración de Spring Security;
- proveedores de JWT;
- generadores de PDF/DOCX/XLSX;
- configuración de OpenAPI;
- logging técnico.

**Responsabilidad:**
resolver el “cómo” técnico del backend.

## Modularidad funcional esperada

La arquitectura por capas debe convivir con módulos funcionales explícitos.

Módulos principales:
- auth
- usuarios
- roles
- profesionales
- pacientes
- citas
- atenciones
- cobros
- reportes

Cada módulo debe poder localizarse mentalmente sin necesidad de escanear todo el proyecto.

## Relación entre módulos y capas

No conviene pensar “capas o módulos”, sino “capas dentro de módulos” o una mezcla equivalente suficientemente clara.

Eso significa que cada módulo puede tener:
- controller;
- service o use case;
- dto;
- mapper;
- repository;
- entity;
- validadores o utilidades específicas.

Pero siempre respetando una frontera clara entre responsabilidades.

## Decisiones arquitectónicas importantes

### 1. DTOs obligatorios en la frontera HTTP
No se exponen entidades JPA directamente.

### 2. Seguridad transversal, pero no invasiva
La autenticación y autorización deben integrarse bien sin contaminar innecesariamente todo el código.

### 3. Casos de uso explícitos
La lógica importante debe poder rastrearse como flujo entendible.

### 4. Reportes como parte formal de infraestructura y aplicación
No deben quedar escondidos como utilidades dispersas.

### 5. Trazabilidad desde diseño
Logs, correlation_id y contexto operativo deben contemplarse como parte real del backend.

## Lo que no se busca en esta V1

- microservicios;
- mensajería o colas complejas;
- workers si no hay necesidad real;
- patrones ceremoniales que añadan peso sin mejorar claridad;
- arquitectura hexagonal purista si termina volviendo más opaco el proyecto que legible.

## Relación con Spring Boot 4

La arquitectura debe sentirse natural dentro de Spring Boot:

- configuración clara;
- uso razonable de anotaciones;
- inyección de dependencias limpia;
- Spring Security para auth;
- Spring Data JPA para persistencia principal;
- OpenAPI integrado;
- Flyway para evolución de esquema.

## Resultado esperado

La arquitectura backend del consultorio debe dejar una base clásica, sólida y muy explícita, lo bastante clara para que una IA la implemente con poca ambigüedad y lo bastante profesional como para que el proyecto sirva realmente como referencia de backend administrativo bien hecho.