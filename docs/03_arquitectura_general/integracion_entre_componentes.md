# Integración entre componentes

## Propósito

Describir cómo se relacionan los componentes principales del sistema sin perder claridad arquitectónica, separación de contextos ni control sobre la exposición de datos.

## Principio general de integración

Los componentes del sistema deben integrarse por contratos claros y dependencias razonables, no por atajos que mezclen capas o rompan fronteras.

La integración correcta en este proyecto significa:

- cada cliente habla con su backend correspondiente;
- cada backend habla con su propia base de datos;
- los contratos son explícitos;
- la información sensible no cruza a superficies no autorizadas;
- consultorio y farmacia no se integran por consultas relacionales directas.

## Integración 1. Desktop consultorio ↔ backend consultorio

### Objetivo
Sostener la operación privada del consultorio.

### Forma de integración
El cliente desktop consume endpoints privados del backend consultorio para:

- buscar y registrar pacientes;
- agendar y gestionar citas;
- registrar atención;
- registrar cobro.

### Reglas
- el desktop no accede directamente a la base de datos;
- la validación fuerte ocurre en backend;
- la UI solo refleja y guía el flujo.

## Integración 2. Backend consultorio ↔ database consultorio

### Objetivo
Persistir y recuperar información privada del consultorio de forma consistente.

### Alcance
Incluye acceso a:

- pacientes;
- citas;
- atenciones;
- indicaciones breves o receta breve;
- cobros.

### Reglas
- la persistencia debe respetar integridad y trazabilidad mínima;
- el backend no debe exponer directamente entidades de persistencia como contratos externos;
- el backend consultorio no debe depender de `database-farmacia`.

## Integración 3. Storefront farmacia ↔ backend farmacia

### Objetivo
Ofrecer una capa pública o semipública de consulta de productos.

### Forma de integración
El storefront consume endpoints públicos o controlados del backend farmacia para:

- listar catálogo visible;
- buscar productos;
- consultar detalle público;
- reflejar disponibilidad publicada.

### Reglas
- el storefront no debe conocer datos internos no publicables;
- el backend farmacia decide qué es visible y qué no.

## Integración 4. Backend farmacia ↔ database farmacia

### Objetivo
Persistir y recuperar información operativa y publicable del catálogo farmacéutico.

### Alcance
Incluye acceso a:

- productos;
- categorías simples;
- estado;
- visibilidad;
- disponibilidad;
- reservas en V1.1.

### Reglas
- el backend decide publicación y coherencia operativa;
- la base sostiene el estado persistente del catálogo;
- el backend farmacia no debe depender de `database-consultorio`.

## Integración 5. Consultorio ↔ farmacia

### Objetivo
Mantener coexistencia dentro de un mismo proyecto sin mezclar responsabilidades.

### Naturaleza de la integración
En la V1, la relación entre consultorio y farmacia es principalmente contextual y arquitectónica, no una dependencia funcional intensa ni una integración relacional.

Esto significa:

- comparten marco documental y visión de producto;
- pueden compartir despliegue local o infraestructura base;
- no comparten una sola base de datos;
- no se asume integración clínica-comercial compleja en V1.

### Regla clave
Si en el futuro se necesita relacionar ambos mundos, esa integración debe pasar por contratos de aplicación, identificadores externos controlados o flujos explícitos entre backends, no por acceso informal a tablas o modelos ajenos.

## Integración transversal con documentación, seguridad y trazabilidad

### Documentación
Actúa como memoria estructurada y marco de coherencia entre todos los componentes.

### Seguridad
Define quién puede consumir qué rutas y qué información puede circular entre capas.

### Trazabilidad
Permite seguir hechos operativos relevantes y entender el comportamiento del sistema.

## Riesgos de mala integración

- que un cliente dependa de detalles internos del backend;
- que se expongan entidades de persistencia directamente;
- que el catálogo público vea datos no autorizados;
- que consultorio y farmacia se mezclen por conveniencia técnica;
- que un backend consuma directamente la base del otro subdominio;
- que cambios en un componente rompan a otros por falta de contratos claros.

## Resultado esperado

La integración entre componentes debe sentirse limpia, razonable y defendible: cada pieza sabe con quién habla, por qué habla y hasta dónde llega su responsabilidad dentro del sistema, manteniendo además independencia real entre ambas persistencias.