# Arquitectura general

## Propósito

Definir la forma global del sistema y los principios arquitectónicos que permitirán construir una V1 sólida, coherente y evolutiva para el proyecto híbrido de consultorio y farmacia.

## Visión estructural del sistema

El sistema se concibe como una solución compuesta por varios componentes coordinados, no como una sola masa de código indiferenciada. La arquitectura debe permitir que cada pieza tenga una responsabilidad clara y que el conjunto siga sintiéndose como un único producto.

A alto nivel, el sistema se organiza en torno a seis bloques:

- `database-consultorio`;
- `database-farmacia`;
- `backend-consultorio`;
- `backend-farmacia`;
- `desktop-consultorio-javafx`;
- `storefront-farmacia-angular`.

Estos bloques no tienen el mismo nivel de exposición ni de sensibilidad. El consultorio opera como contexto privado. La farmacia combina una administración interna con una superficie pública controlada.

## Principios arquitectónicos

### 1. Separación de contextos
Consultorio y farmacia pertenecen al mismo proyecto, pero no deben colapsar en un único dominio confuso. La arquitectura debe reflejar que uno gestiona información sensible y el otro información comercial parcialmente publicable.

### 2. Separación de persistencia
La separación entre consultorio y farmacia no es solo lógica o documental. También es física a nivel de persistencia: cada subdominio tiene su propia base de datos.

### 3. Modularidad pragmática
La modularidad se adopta para reducir mezcla de responsabilidades, facilitar evolución y sostener claridad mental del sistema. No se busca microfragmentar sin necesidad.

### 4. Contratos explícitos
Las interacciones entre capas y entre componentes deben pasar por contratos definidos, con DTOs y respuestas consistentes, evitando exposición directa de modelos de persistencia.

### 5. Evolución sin ruptura
La arquitectura debe soportar crecimiento natural de la V1.0 a la V1.1 mediante migraciones, ajustes de reglas y ampliación de flujos sin rediseñar todo el producto.

### 6. Protección por diseño
La sensibilidad del consultorio no debe depender únicamente de “recordar tener cuidado”. Debe apoyarse en la propia estructura del sistema: rutas separadas, permisos, modelos separados, superficies bien diferenciadas y persistencia independiente.

## Estilo arquitectónico adoptado

La arquitectura base del proyecto será modular por componente y por capas.

Esto significa que cada backend y cada cliente tendrán una organización interna orientada a separar:

- entrada o interfaz;
- aplicación o casos de uso;
- dominio;
- infraestructura.

A nivel del sistema completo, habrá además separación por contexto funcional:

- contexto consultorio;
- contexto farmacia.

En otras palabras, el proyecto tiene dos ejes de orden simultáneos:

- eje de negocio: consultorio vs farmacia;
- eje técnico: capas y adaptadores internos.

## Estructura global esperada

### Database consultorio
Responsable de persistir exclusivamente el estado del consultorio y sostener integridad, consistencia y evolución del esquema del subdominio privado.

### Database farmacia
Responsable de persistir exclusivamente el estado de la farmacia y sostener integridad, consistencia y evolución del esquema del subdominio comercial.

### Backend consultorio
Responsable de exponer operaciones privadas para pacientes, citas, atenciones y cobros del consultorio, consumiendo solo `database-consultorio`.

### Backend farmacia
Responsable de exponer operaciones administrativas de farmacia y endpoints públicos de catálogo o soporte para la capa pública, consumiendo solo `database-farmacia`.

### Cliente desktop consultorio
Responsable de ofrecer una interfaz interna sobria y operativa para el subdominio del consultorio.

### Storefront web farmacia
Responsable de la consulta pública o semipública del catálogo farmacéutico y de la experiencia comercial simple de búsqueda y visibilidad.

## Decisión sobre integración entre subdominios

Consultorio y farmacia no se relacionan mediante claves foráneas cruzadas ni consultas relacionales directas entre sus bases. Si en el futuro deben intercambiar información, la integración deberá pasar por contratos de aplicación, identificadores externos controlados o flujos explícitos entre backends.

## Decisión sobre despliegue lógico

Aunque el sistema se piense modularmente, no se está forzando desde ya una arquitectura distribuida compleja. El proyecto puede desplegarse de forma local y controlada, manteniendo separación lógica y documental fuerte aunque la topología física inicial sea sencilla.

## Qué debe evitar la arquitectura

- mezclar pacientes y productos como si pertenecieran al mismo núcleo;
- dejar que la capa pública vea datos privados;
- exponer entidades de persistencia directamente;
- construir integraciones entre consultorio y farmacia mediante acceso directo a tablas ajenas;
- introducir complejidad hospitalaria o empresarial fuera del alcance.

## Resultado esperado

La arquitectura general debe permitir que el proyecto sea entendible, defendible y ampliable, con una base suficientemente seria para estudiar aplicaciones administrativas reales y mostrar criterio profesional, manteniendo además independencia real entre persistencia clínica y persistencia comercial.