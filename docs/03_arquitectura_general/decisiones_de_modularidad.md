# Decisiones de modularidad

## Propósito

Explicar por qué el sistema se divide en ciertos módulos y qué tipo de modularidad se considera adecuada para esta V1 amplia.

## Problema a resolver

Sin modularidad, el proyecto tendería a convertirse en una sola masa de código donde el consultorio, la farmacia, la persistencia, la lógica y la presentación terminan mezclándose. Eso dificultaría el aprendizaje, la evolución del sistema y la claridad de portafolio.

La modularidad se adopta para controlar ese riesgo.

## Nivel de modularidad elegido

La modularidad oficial del proyecto es **pragmática y suficiente**, no maximalista.

Esto significa:

- sí separar contextos de negocio;
- sí separar componentes principales;
- sí separar capas internas;
- sí separar persistencia por subdominio;
- no fragmentar artificialmente en demasiados microservicios o módulos diminutos sin valor real para esta etapa.

## Decisión 1. Separar consultorio y farmacia como contextos distintos

Se fija que consultorio y farmacia no son solo carpetas diferentes, sino contextos funcionales distintos.

### Razones
- manejan datos de naturaleza diferente;
- tienen superficies de exposición diferentes;
- obedecen reglas de negocio diferentes;
- evolucionan a ritmos potencialmente distintos.

### Implicación
Las decisiones de datos, rutas, DTOs, permisos y UI deben respetar esa separación.

## Decisión 2. Separar también la persistencia

Se fija que la separación entre consultorio y farmacia también aplica a bases de datos.

### Razones
- reduce acoplamiento relacional entre subdominios;
- protege mejor la información sensible del consultorio;
- obliga a diseñar integraciones explícitas en lugar de atajos por consultas cruzadas;
- hace más clara la responsabilidad de cada backend.

### Implicación
Cada backend consume su propia base de datos y no debe asumir acceso directo a la persistencia del otro contexto.

## Decisión 3. Mantener componentes técnicos diferenciados

Se fija la existencia de componentes técnicos separados para:

- `database-consultorio`;
- `database-farmacia`;
- `backend-consultorio`;
- `backend-farmacia`;
- `desktop-consultorio-javafx`;
- `storefront-farmacia-angular`.

### Razones
- claridad de responsabilidad;
- mejor aprendizaje por componente;
- facilidad para documentar y defender el sistema;
- reducción de mezcla entre capa pública, capa interna y persistencia.

## Decisión 4. Usar capas internas dentro de los backends

Cada backend debe separarse internamente en capas del estilo:

- entrada o interfaz;
- aplicación;
- dominio;
- infraestructura.

### Razones
- evitar lógica de negocio dentro de controladores o adaptadores;
- facilitar pruebas y cambios;
- mantener contratos estables;
- reforzar criterio profesional de arquitectura.

## Decisión 5. No adoptar microservicios distribuidos reales en la V1

Aunque exista separación entre backends, contextos y bases, no se fuerza una topología de despliegue distribuido compleja.

### Razones
- la V1 busca aprendizaje sólido, no complejidad operativa innecesaria;
- el proyecto debe poder ejecutarse localmente con control razonable;
- se prioriza claridad del diseño sobre sofisticación de infraestructura.

### Implicación
Puede haber separación lógica fuerte incluso si el entorno de ejecución inicial es relativamente simple o local.

## Decisión 6. Permitir evolución controlada hacia V1.1

La modularidad debe facilitar que cambios como reservas de farmacia o trazabilidad más madura de agenda no obliguen a rediseñar todo el sistema.

### Implicación
Cada módulo debe exponer contratos claros y depender lo menos posible de detalles internos ajenos.

## Acoplamiento aceptable

Se considera aceptable:

- que los clientes dependan de contratos API de sus respectivos backends;
- que cada backend dependa exclusivamente de su propia base de datos;
- que exista una visión documental común del producto.

Se considera no deseable:

- que el storefront dependa de lógica privada del consultorio;
- que el desktop consulte directamente su base de datos;
- que un backend conozca detalles internos del otro backend;
- que consultorio y farmacia se relacionen mediante tablas compartidas o claves cruzadas;
- que modelos de persistencia se filtren indiscriminadamente entre capas.

## Qué no debe confundirse con modularidad

- crear muchas carpetas no equivale a buena modularidad;
- dividir por tecnología sin dividir por responsabilidad no basta;
- separar físicamente código sin definir fronteras claras no resuelve el problema.

## Resultado esperado

La modularidad del proyecto debe ayudarte a estudiar mejor, documentar mejor y evolucionar mejor el sistema, manteniendo suficiente realismo profesional sin convertir la V1 en una arquitectura sobrediseñada.