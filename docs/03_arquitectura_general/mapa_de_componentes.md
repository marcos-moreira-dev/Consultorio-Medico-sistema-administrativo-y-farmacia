# Mapa de componentes

## Propósito

Identificar los grandes componentes del sistema, su rol dentro del producto y su relación general con el resto de piezas.

## Vista general

El proyecto se compone de un conjunto de componentes principales que colaboran para sostener el negocio híbrido de consultorio y farmacia. Estos componentes no deben entenderse como archivos sueltos, sino como unidades de responsabilidad dentro de una arquitectura modular.

## Componentes principales

### 1. Database consultorio

**Rol principal:**
Persistir la información privada del consultorio con integridad y permitir evolución controlada del esquema.

**Responsabilidades:**
- almacenar pacientes, citas, atenciones, indicaciones breves y cobros;
- sostener restricciones y consistencia básica del consultorio;
- soportar migraciones y seeds del subdominio;
- servir como fuente de verdad persistente del consultorio.

**No debe hacer:**
- almacenar catálogo o stock de farmacia;
- asumir lógica de presentación;
- convertirse en punto de integración pública.

## 2. Backend consultorio

**Rol principal:**
Exponer la lógica privada del subdominio consultorio.

**Responsabilidades:**
- gestionar pacientes;
- gestionar citas;
- registrar atenciones;
- registrar cobros;
- aplicar validaciones y reglas del consultorio;
- proteger acceso a datos sensibles;
- consumir exclusivamente `database-consultorio`.

**Consumidores principales:**
- cliente desktop del consultorio.

## 3. Cliente desktop consultorio

**Rol principal:**
Proveer la interfaz interna y operativa del consultorio.

**Responsabilidades:**
- facilitar búsqueda rápida y flujos del día a día;
- presentar agenda, pacientes, atención y cobro;
- consumir contratos privados del backend consultorio;
- sostener una UX sobria y clara.

**No debe hacer:**
- asumir reglas de negocio críticas fuera del backend;
- acceder directamente a `database-consultorio`.

## 4. Database farmacia

**Rol principal:**
Persistir la información operativa y comercial de la farmacia con integridad y evolución controlada del esquema.

**Responsabilidades:**
- almacenar productos, disponibilidad, estados y estructuras futuras de reserva;
- sostener restricciones y consistencia básica del catálogo;
- soportar migraciones y seeds del subdominio;
- servir como fuente de verdad persistente de farmacia.

**No debe hacer:**
- almacenar pacientes, citas o atenciones;
- servir como interfaz pública directa.

## 5. Backend farmacia

**Rol principal:**
Exponer operaciones administrativas y de publicación del subdominio farmacia.

**Responsabilidades:**
- crear y actualizar productos;
- controlar estado, visibilidad y disponibilidad;
- soportar catálogo público;
- preparar evolución hacia reservas en V1.1;
- consumir exclusivamente `database-farmacia`.

**Consumidores principales:**
- storefront web de farmacia;
- posibles interfaces administrativas futuras.

## 6. Storefront web farmacia

**Rol principal:**
Ofrecer una capa pública o semipública para consulta de productos.

**Responsabilidades:**
- mostrar catálogo visible;
- permitir búsqueda simple;
- mostrar detalle controlado del producto;
- mantener una experiencia clara y ligera.

**No debe hacer:**
- exponer datos internos no publicables;
- acceder a `database-farmacia` directamente;
- acceder a información privada del consultorio.

## Componentes transversales o de soporte

### Documentación del proyecto
Sostiene coherencia conceptual, técnica y evolutiva.

### Seguridad y autorización
Define acceso, separación de superficies y protección de datos sensibles.

### Trazabilidad y logs
Permite seguimiento de acciones relevantes y diagnóstico operativo.

### Despliegue y operación
Hace posible ejecutar el sistema localmente y mantenerlo de forma ordenada.

## Relaciones principales entre componentes

- el cliente desktop consultorio consume el backend consultorio;
- el backend consultorio consume `database-consultorio`;
- el storefront web farmacia consume el backend farmacia;
- el backend farmacia consume `database-farmacia`;
- documentación, seguridad y trazabilidad influyen sobre todo el conjunto.

## Relación entre consultorio y farmacia

La relación entre ambos subdominios es principalmente contextual y de producto, no relacional a nivel de base de datos. Si en el futuro deben intercambiar información, la integración deberá pasar por contratos de aplicación, referencias externas controladas o coordinación explícita entre backends.

## Relación con la V1.1

La evolución V1.1 no cambia el mapa general, pero sí puede ampliar responsabilidades de ciertos componentes, especialmente:

- `database-consultorio` y `backend-consultorio`, para reprogramaciones y mayor trazabilidad de agenda;
- `database-farmacia` y `backend-farmacia`, para reservas;
- clientes, para reflejar nuevos estados y comportamientos.

## Resultado esperado

Este mapa debe servir como referencia rápida para entender de qué piezas está hecho el sistema, qué rol cumple cada una y cómo se conectan sin perder claridad arquitectónica ni separación real de persistencia.