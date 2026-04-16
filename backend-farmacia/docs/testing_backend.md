# Testing backend

## Propósito

Definir qué debe probarse en `backend-farmacia`, con qué criterio y con qué nivel de profundidad razonable para una V1 seria, con superficie pública y superficie administrativa, orientada a estudio y portafolio.

## Principio general

El testing del backend no debe ser decorativo ni una lista de pruebas sueltas que solo existen para “cumplir”. Debe ayudar a verificar que el sistema realmente sostiene:

- seguridad admin;
- separación entre superficie pública y privada;
- reglas del dominio de farmacia;
- consistencia de contratos;
- estabilidad razonable frente a cambios.

## Qué se busca con el testing en esta V1

### 1. Proteger reglas importantes
Especialmente aquellas que sostienen la operación real de la farmacia.

### 2. Evitar regresiones obvias
Que agregar o cambiar una funcionalidad no rompa otra ya establecida.

### 3. Dar confianza a la arquitectura
Que los módulos, servicios y contratos se comporten como fueron diseñados.

### 4. Servir como estudio
Que el proyecto también te enseñe qué se prueba y por qué se prueba en un backend clásico bien hecho.

## Niveles de prueba recomendados

## 1. Unit tests

### Objetivo
Probar lógica aislada con foco en reglas de negocio o transformaciones relevantes.

### Cuándo sí aporta
- validaciones internas;
- mappers no triviales;
- reglas de publicación;
- reglas de disponibilidad;
- validación de media;
- construcción de filtros o criterios.

### Cuándo no aporta tanto
No hace falta llenar el proyecto de unit tests vacíos para getters, setters o wrappers evidentes.

## 2. Integration tests

### Objetivo
Verificar que varias piezas colaboren bien entre sí.

### Ejemplos importantes
- controller + service + repository;
- seguridad + endpoint admin;
- persistencia + validación de negocio;
- carga o asociación de imagen;
- catálogo público filtrado correctamente.

### Valor en este proyecto
Muy alto. Este tipo de backend gana mucho más con buenas pruebas de integración que con miles de unit tests triviales.

## 3. API / endpoint tests

### Objetivo
Comprobar que los contratos HTTP realmente se cumplan.

### Ejemplos importantes
- login admin correcto e incorrecto;
- 401 cuando falta token;
- creación de categoría;
- creación de producto;
- publicación válida;
- producto no publicable;
- catálogo público filtrado;
- carga de imagen;
- error por archivo inválido.

## Áreas que conviene probar sí o sí

## `auth-admin` y seguridad
- login exitoso;
- login fallido;
- token inválido;
- token expirado si se contempla;
- acceso denegado a rutas admin.

## `categorias`
- creación válida;
- duplicado si la política lo contempla;
- consulta y listado.

## `productos`
- creación válida;
- categoría inexistente;
- validación de precio;
- actualización;
- cambio de estado.

## `disponibilidad-publicacion`
- publicación válida;
- despublicación válida;
- transición incoherente de estado;
- filtro correcto en catálogo público.

## `media`
- carga válida;
- archivo inválido;
- producto inexistente;
- asociación correcta;
- exposición de `imagenUrl` en DTOs correctos.

## `catalogo-publico`
- listar productos visibles;
- excluir productos no publicables;
- búsqueda por nombre;
- filtro por categoría;
- respuesta pública sin campos administrativos sensibles.

## Evolución a V1.1

Cuando exista `reservas`, convendrá probar:
- creación de reserva;
- cancelación;
- concreción;
- impacto sobre disponibilidad.

Pero eso no pertenece a V1.0.

## Criterio de cobertura

No se trata de perseguir un porcentaje mágico de cobertura. Se trata de cubrir bien:

- flujos críticos;
- separación público/admin;
- seguridad;
- validaciones sensibles;
- contratos más importantes.

## Datos de prueba

Conviene usar datos de prueba coherentes con el dominio ya modelado:

- varias categorías;
- productos activos e inactivos;
- productos publicables y no publicables;
- productos con disponibilidad distinta;
- imágenes asociadas de forma controlada.

## Qué no debe pasar

- pruebas que dependen del orden casual de ejecución;
- pruebas que solo verifican cosas triviales sin valor;
- cero pruebas sobre seguridad admin;
- cero pruebas sobre exposición pública del catálogo;
- endpoints importantes sin cobertura mínima de integración.

## Resultado esperado

La estrategia de testing del backend-farmacia debe dejar un sistema razonablemente confiable, con foco en flujos críticos, seguridad, catálogo público y reglas del negocio, sin caer en obsesión de cobertura vacía ni en abandono total de calidad.