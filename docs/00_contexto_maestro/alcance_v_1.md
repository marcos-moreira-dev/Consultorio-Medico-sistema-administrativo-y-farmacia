# Alcance V1

## Propósito de este documento

Este documento delimita la primera versión amplia del proyecto para evitar dos riesgos clásicos: construir algo demasiado pequeño para aprender de verdad, o construir algo tan ambicioso que se vuelva difuso, inmanejable o irreal para una práctica seria.

La convención oficial del proyecto es que existe una sola V1 amplia. Sin embargo, para efectos de aprendizaje, evolución y documentación técnica, esta V1 se divide internamente en dos subetapas:

- V1.0
- V1.1

No son productos distintos. Son capas de maduración dentro del mismo sistema.

## Criterio general de alcance

La V1 debe cubrir el núcleo operativo y administrativo suficiente para que el producto sea:

- creíble como software de negocio pequeño;
- útil para estudiar arquitectura y dominio;
- demostrable ante terceros;
- extensible sin rehacer toda la base.

## V1.0

### Objetivo
Construir un núcleo sólido, coherente y funcional del sistema, con separación clara entre consultorio y farmacia.

### Incluye: consultorio

- registro básico de pacientes;
- búsqueda y consulta de pacientes;
- agenda de citas;
- control básico de estados de cita;
- atención simple;
- indicaciones o receta breve no compleja;
- cobro de consulta;
- historial operativo básico de atenciones.

### Incluye: farmacia pública y administrativa

- catálogo de productos visible al público;
- búsqueda por nombre, categoría o coincidencia simple;
- detalle de producto;
- visibilidad de disponibilidad;
- mantenimiento administrativo básico de productos;
- control básico de stock o disponibilidad publicada.

### Incluye: arquitectura y calidad

- separación por capas;
- DTOs explícitos;
- contratos API consistentes;
- manejo uniforme de errores;
- persistencia relacional en PostgreSQL;
- seeds reproducibles para demo;
- documentación suficiente para entender y defender el sistema.

### No incluye en V1.0

- historia clínica extensa o especializada;
- internación, laboratorio o módulos clínicos avanzados;
- pagos en línea;
- delivery completo;
- facturación electrónica real;
- integración con sistemas externos complejos;
- múltiples sucursales;
- analítica avanzada;
- módulos pesados de reportería empresarial.

## V1.1

### Objetivo
Introducir una evolución funcional razonable que obligue a modificar base de datos, reglas y contratos de forma controlada, con el fin de practicar migraciones y mantenimiento evolutivo del sistema.

### Cambios oficiales previstos para V1.1

#### Línea 1: agenda más madura

- cancelación y reprogramación de citas con mayor formalidad;
- mejor trazabilidad de cambios de estado;
- preparación del dominio para diagnósticos operativos posteriores.

#### Línea 2: farmacia con reservas

- incorporación de reservas de productos cuando no haya disponibilidad inmediata o cuando se requiera apartar unidades;
- reglas más claras de transición entre stock, reserva y disponibilidad publicada;
- impacto real en contratos y modelo relacional.

### Qué se busca aprender con V1.1

- diseño de migraciones con Flyway;
- evolución de esquema sin romper el sistema;
- compatibilidad razonable entre versiones del dominio;
- refactorización de DTOs y contratos;
- adaptación de reglas de negocio sin improvisación.

## Principios de recorte

Cuando aparezca una duda sobre si algo entra o no entra en la V1, se deben usar estos criterios:

### Sí entra si:

- fortalece el núcleo operativo real del negocio;
- mejora significativamente la fidelidad administrativa;
- obliga a aprender una práctica técnica valiosa;
- no dispara complejidad hospitalaria o empresarial excesiva.

### No entra si:

- responde a un caso demasiado raro para esta escala;
- requiere infraestructura o regulación fuera del propósito del proyecto;
- agrega complejidad clínica ajena al foco administrativo;
- puede dejarse para una evolución futura sin dañar la coherencia del producto.

## Resultado esperado

Al terminar la V1.0, el sistema ya debe poder demostrarse como un producto serio. Al terminar la V1.1, el proyecto además debe enseñar cómo un sistema administrativo evoluciona de forma profesional sin empezar desde cero.