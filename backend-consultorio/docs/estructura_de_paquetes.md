# Estructura de paquetes

## Propósito

Definir una organización de paquetes clara, compatible con Spring Boot 4 y suficientemente explícita para que la implementación del backend no se desordene a medida que crece.

## Principio general

La estructura de paquetes debe ayudarte a encontrar rápido:

- el módulo funcional;
- la capa correcta;
- los DTOs;
- los casos de uso;
- la entidad persistente;
- la configuración transversal.

No debe ser tan rara que solo se entienda después de leer diez archivos, ni tan plana que todo quede mezclado.

## Estrategia recomendada

Se recomienda una estructura **por módulos**, y dentro de cada módulo una separación suficientemente clara por tipo de responsabilidad.

## Paquete base sugerido

Ejemplo conceptual:

`com.tuorganizacion.consultorio.backend`

o equivalente según el nombre real del proyecto.

## Paquetes transversales sugeridos

### `config`
Para configuraciones globales del proyecto.

### `security`
Para clases técnicas de Spring Security, JWT, filtros y utilidades de autenticación.

### `common`
Para clases compartidas con mucho cuidado:
- respuestas comunes;
- errores comunes;
- utilidades pequeñas;
- paginación común;
- objetos de soporte transversal.

### `reporting`
Si quieres centralizar infraestructura de generación de documentos, puede convivir como módulo propio o como zona transversal ligada a `reportes`.

## Estructura por módulo

Cada módulo funcional puede organizarse con una forma parecida a esta:

- `controller`
- `dto`
- `service` o `application`
- `mapper`
- `entity`
- `repository`
- `validation` cuando haga falta

## Módulos esperados y paquetes sugeridos

### `auth`
Paquetes típicos:
- `auth.controller`
- `auth.dto`
- `auth.service`
- `auth.mapper` si se necesita

### `usuarios`
Paquetes típicos:
- `usuarios.controller`
- `usuarios.dto`
- `usuarios.service`
- `usuarios.entity`
- `usuarios.repository`
- `usuarios.mapper`

### `roles`
Paquetes típicos:
- `roles.controller` si expone endpoints
- `roles.dto`
- `roles.service`
- `roles.entity`
- `roles.repository`

### `profesionales`
Paquetes típicos:
- `profesionales.controller`
- `profesionales.dto`
- `profesionales.service`
- `profesionales.entity`
- `profesionales.repository`
- `profesionales.mapper`

### `pacientes`
Paquetes típicos:
- `pacientes.controller`
- `pacientes.dto`
- `pacientes.service`
- `pacientes.entity`
- `pacientes.repository`
- `pacientes.mapper`
- `pacientes.validation` si hace falta

### `citas`
Paquetes típicos:
- `citas.controller`
- `citas.dto`
- `citas.service`
- `citas.entity`
- `citas.repository`
- `citas.mapper`
- `citas.validation`

### `atenciones`
Paquetes típicos:
- `atenciones.controller`
- `atenciones.dto`
- `atenciones.service`
- `atenciones.entity`
- `atenciones.repository`
- `atenciones.mapper`

### `cobros`
Paquetes típicos:
- `cobros.controller`
- `cobros.dto`
- `cobros.service`
- `cobros.entity`
- `cobros.repository`
- `cobros.mapper`

### `reportes`
Paquetes típicos:
- `reportes.controller`
- `reportes.dto`
- `reportes.service`
- `reportes.generator`
- `reportes.template` si luego aplicara

## Criterios para esta estructura

### 1. Lo funcional primero
Debe ser fácil ubicar el módulo al que pertenece una clase.

### 2. Lo transversal con moderación
No todo debe ir a `common` o `shared`, porque eso suele degradarse en cajón genérico.

### 3. DTOs separados por módulo
Evita una carpeta global de DTOs gigantesca.

### 4. Mappers junto al módulo
Ayuda a que el flujo request → entidad → response sea más entendible.

### 5. Seguridad técnica fuera del dominio clínico
JWT, filtros, handlers y config de seguridad deben vivir en zona técnica clara.

## Qué evitar

- paquetes demasiado planos;
- mezclar todos los controllers del sistema en una sola carpeta sin módulos claros;
- un `shared` gigante con medio proyecto adentro;
- entidades JPA mezcladas con DTOs o con objetos de seguridad;
- ubicar reportes como utilidades dispersas sin módulo reconocible.

## Resultado esperado

La estructura de paquetes debe hacer que el backend-consultorio se sienta clásico, ordenado y legible, facilitando tanto el trabajo manual como la generación asistida por IA y el estudio posterior de Spring Boot 4.