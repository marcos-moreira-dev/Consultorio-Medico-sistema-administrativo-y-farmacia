# Contexto y contenedores

## Propósito

Describir el sistema desde una vista de alto nivel que muestre qué actores interactúan con él, qué contenedores principales existen y cómo se relacionan entre sí dentro de la V1.

## Vista de contexto

El sistema forma parte de un negocio híbrido pequeño en Guayaquil, compuesto por un consultorio y una farmacia asociada. Desde afuera, no todos los actores ven lo mismo ni interactúan con la misma superficie.

### Actores externos principales

#### Personal interno del consultorio
Usa la superficie privada del sistema para gestionar pacientes, citas, atenciones y cobros.

#### Personal interno de farmacia
Usa la superficie administrativa de farmacia para mantener productos, disponibilidad y visibilidad.

#### Visitante o cliente externo
Interactúa con la capa pública o semipública de farmacia para buscar productos y consultar disponibilidad visible.

## Contenedores principales

### 1. Cliente desktop consultorio
Es la interfaz interna del consultorio. Está diseñada para un flujo operativo privado, directo y sobrio.

**Responsabilidad:**
- búsqueda de pacientes;
- agenda;
- atención simple;
- cobro asociado.

**Tipo de acceso:**
privado.

## 2. Backend consultorio
Es el contenedor que expone la lógica y los contratos privados del subdominio consultorio.

**Responsabilidad:**
- validar y ejecutar casos de uso del consultorio;
- proteger datos sensibles;
- persistir pacientes, citas, atenciones y cobros.

**Tipo de acceso:**
privado.

## 3. Database consultorio
Es el contenedor persistente exclusivo del consultorio.

**Responsabilidad:**
- almacenar estado del consultorio;
- sostener integridad del subdominio privado;
- soportar migraciones y seeds propios.

**Tipo de acceso:**
solo a través del backend consultorio.

## 4. Storefront web farmacia
Es la interfaz pública o semipública orientada a consulta de catálogo.

**Responsabilidad:**
- listar productos visibles;
- permitir búsqueda simple;
- mostrar detalle público del producto.

**Tipo de acceso:**
público o semipúblico.

## 5. Backend farmacia
Es el contenedor que expone la lógica del subdominio farmacia tanto para la parte administrativa como para la parte pública controlada.

**Responsabilidad:**
- gestionar productos;
- controlar publicación;
- mantener disponibilidad;
- sostener catálogo visible.

**Tipo de acceso:**
mixto, con superficie pública controlada y superficie administrativa privada.

## 6. Database farmacia
Es el contenedor persistente exclusivo de la farmacia.

**Responsabilidad:**
- almacenar estado del catálogo y su operación interna;
- sostener integridad del subdominio comercial;
- soportar migraciones y seeds propios.

**Tipo de acceso:**
solo a través del backend farmacia.

## Relaciones entre contenedores

- el cliente desktop consultorio consume el backend consultorio;
- el backend consultorio consume `database-consultorio`;
- el storefront farmacia consume el backend farmacia;
- el backend farmacia consume `database-farmacia`;
- la interacción entre consultorio y farmacia es principalmente contextual y de aplicación, no una dependencia relacional directa en V1.

## Separación de superficies

### Superficie privada
- desktop consultorio;
- backend consultorio;
- endpoints internos administrativos del consultorio;
- `database-consultorio`.

### Superficie pública o semipública
- storefront farmacia;
- endpoints públicos o controlados de catálogo.

### Superficie administrativa interna
- endpoints administrativos de farmacia;
- `database-farmacia` accesible solo por backend.

## Criterios de diseño aplicados a esta vista

- el consultorio no se expone públicamente;
- la farmacia sí tiene una capa pública controlada;
- los clientes no acceden directamente a ninguna base de datos;
- cada contenedor tiene una responsabilidad dominante clara;
- la separación entre subdominios también existe a nivel de persistencia.

## Resultado esperado

Esta vista debe permitir entender el sistema de un solo vistazo: quién lo usa, qué grandes piezas lo componen y cómo se conectan dentro de una V1 sólida, modular y con dos persistencias claramente diferenciadas.