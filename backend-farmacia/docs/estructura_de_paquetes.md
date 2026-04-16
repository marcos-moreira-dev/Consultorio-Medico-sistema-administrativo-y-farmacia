# Estructura de paquetes

## Propósito

Definir una organización de paquetes clara, compatible con Nest y TypeORM, y suficientemente explícita para que la implementación del backend no se desordene a medida que crece.

## Principio general

La estructura de paquetes debe ayudarte a encontrar rápido:

- el módulo funcional;
- la capa correcta;
- los DTOs;
- los casos de uso o servicios;
- la entidad persistente;
- la configuración transversal;
- la lógica pública y la lógica admin.

No debe ser tan rara que solo se entienda después de leer diez archivos, ni tan plana que todo quede mezclado.

## Estrategia recomendada

Se recomienda una estructura **por módulos**, y dentro de cada módulo una separación suficientemente clara por tipo de responsabilidad.

## Directorio base sugerido

Ejemplo conceptual:

`src/`

con módulos y carpetas transversales claras.

## Zonas transversales sugeridas

### `config`
Para configuraciones globales del proyecto.

### `security`
Para JWT, guards, strategies, decorators de autorización y utilidades de autenticación admin.

### `common`
Para clases compartidas con mucho cuidado:
- respuestas comunes;
- errores comunes;
- paginación común;
- utilidades pequeñas;
- objetos de soporte transversal.

### `media-storage` o equivalente
Si decides centralizar detalles técnicos de almacenamiento de archivos fuera del módulo funcional `media`, debe quedar con nombre reconocible.

## Estructura por módulo

Cada módulo funcional puede organizarse con una forma parecida a esta:

- `controller`
- `dto`
- `service`
- `mapper` si aplica
- `entity`
- `repository` o acceso equivalente
- `validation` cuando haga falta

En Nest, según el estilo que adoptes, algunas piezas pueden vivir como providers del módulo sin necesidad de sobreformalizarlo, pero la frontera conceptual debe mantenerse.

## Módulos esperados y paquetes sugeridos

### `auth-admin`
Paquetes típicos:
- `auth-admin.controller`
- `auth-admin.dto`
- `auth-admin.service`

### `categorias`
Paquetes típicos:
- `categorias.controller`
- `categorias.dto`
- `categorias.service`
- `categorias.entity`
- `categorias.repository`

### `productos`
Paquetes típicos:
- `productos.controller`
- `productos.dto`
- `productos.service`
- `productos.entity`
- `productos.repository`
- `productos.mapper`
- `productos.validation`

### `media`
Paquetes típicos:
- `media.controller`
- `media.dto`
- `media.service`
- `media.storage`
- `media.validation`

### `catalogo-publico`
Paquetes típicos:
- `catalogo-publico.controller`
- `catalogo-publico.dto`
- `catalogo-publico.service`

### `disponibilidad-publicacion`
Paquetes típicos:
- `disponibilidad-publicacion.controller`
- `disponibilidad-publicacion.dto`
- `disponibilidad-publicacion.service`

### `reservas` para V1.1
Paquetes futuros:
- `reservas.controller`
- `reservas.dto`
- `reservas.service`
- `reservas.entity`
- `reservas.repository`

## Criterios para esta estructura

### 1. Lo funcional primero
Debe ser fácil ubicar el módulo al que pertenece una clase.

### 2. Lo transversal con moderación
No todo debe ir a `common` o `shared`, porque eso suele degradarse en cajón genérico.

### 3. DTOs separados por módulo
Evita una carpeta global de DTOs gigantesca.

### 4. Seguridad técnica fuera del dominio comercial
JWT, guards, estrategias y decorators deben vivir en una zona técnica clara.

### 5. Público y admin con separación visible
La estructura debe facilitar distinguir qué piezas pertenecen a la superficie pública y cuáles a la administrativa.

## Qué evitar

- paquetes demasiado planos;
- mezclar todos los controllers del sistema en una sola carpeta sin módulos claros;
- un `shared` gigante con medio proyecto adentro;
- entidades TypeORM mezcladas con DTOs o con objetos de seguridad;
- ubicar media como una serie de helpers sueltos sin módulo reconocible.

## Resultado esperado

La estructura de paquetes debe hacer que el backend-farmacia se sienta clásico, ordenado y legible, facilitando tanto el trabajo manual como la generación asistida por IA y el estudio posterior de Nest + TypeORM.

