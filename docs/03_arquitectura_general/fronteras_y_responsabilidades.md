# Fronteras y responsabilidades

## Propósito

Definir con claridad qué hace cada parte del sistema y qué cosas no le corresponden. La meta es evitar contaminación entre contextos, duplicación de lógica y acoplamientos innecesarios.

## Principio rector

Una buena frontera no solo separa carpetas. También separa lenguaje, responsabilidades, datos, permisos y expectativas.

## Frontera 1. Consultorio vs farmacia

### Consultorio
**Responsabilidad principal:**
Gestionar la superficie privada del negocio.

**Incluye:**
- pacientes;
- citas;
- atención simple;
- indicaciones breves;
- cobro asociado a atención.

**No le corresponde:**
- catálogo público de productos;
- búsqueda pública de farmacia;
- publicación comercial;
- lógica de visibilidad pública de productos.

### Farmacia
**Responsabilidad principal:**
Gestionar catálogo, disponibilidad y exposición pública controlada del negocio farmacéutico.

**Incluye:**
- productos;
- categorías;
- estado y visibilidad;
- disponibilidad;
- futura reserva de unidades en V1.1.

**No le corresponde:**
- pacientes del consultorio;
- citas del consultorio;
- notas de atención;
- cobros de consulta.

## Frontera 2. Persistencia consultorio vs persistencia farmacia

### Database consultorio
**Responsabilidad principal:**
Persistir exclusivamente información del consultorio.

**Incluye:**
- pacientes;
- citas;
- atenciones;
- indicaciones breves;
- cobros;
- trazabilidad propia del subdominio.

**No le corresponde:**
- productos;
- disponibilidad;
- reservas de farmacia;
- publicación de catálogo.

### Database farmacia
**Responsabilidad principal:**
Persistir exclusivamente información de farmacia.

**Incluye:**
- productos;
- estados;
- publicación;
- disponibilidad;
- reservas futuras;
- trazabilidad propia del subdominio.

**No le corresponde:**
- pacientes;
- citas;
- atenciones;
- cobros del consultorio.

**Regla clave:**
No se deben crear claves foráneas cruzadas ni dependencia relacional directa entre ambas bases.

## Frontera 3. Backends vs clientes

### Backends
**Responsabilidad principal:**
Aplicar reglas de negocio, validar entradas, coordinar casos de uso, proteger acceso y persistir datos.

**No les corresponde:**
- definir comportamiento visual específico de las pantallas;
- delegar reglas críticas a la UI;
- consumir directamente la base del otro subdominio.

### Clientes
**Responsabilidad principal:**
Presentar información, guiar flujos, capturar entradas y consumir contratos del backend correspondiente.

**No les corresponde:**
- acceder directamente a la base de datos;
- sostener la lógica de negocio crítica como fuente de verdad.

## Frontera 4. Dominio vs infraestructura

### Dominio
**Responsabilidad principal:**
Expresar conceptos del negocio, reglas y estados con significado operativo.

### Infraestructura
**Responsabilidad principal:**
Materializar persistencia, acceso a red, serialización, logging y otros detalles técnicos.

**Regla clave:**
El dominio no debe quedar rehén de detalles de infraestructura innecesarios.

## Frontera 5. Datos privados vs datos publicables

### Datos privados
- pacientes;
- citas;
- atenciones;
- indicaciones;
- cobros;
- información sensible o interna.

### Datos publicables
- nombre del producto;
- presentación;
- categoría;
- disponibilidad controlada;
- detalle público autorizado;
- precio visible, si el producto lo muestra.

**Regla clave:**
Nada del consultorio debe filtrarse a la superficie pública por comodidad de implementación.

## Responsabilidades por componente

### Database consultorio
- persistir datos del consultorio;
- sostener restricciones básicas del subdominio;
- servir como base evolutiva del consultorio.

### Backend consultorio
- aplicar reglas del consultorio;
- exponer contratos privados;
- proteger información sensible;
- integrar solo con `database-consultorio`.

### Desktop consultorio
- facilitar operación diaria interna;
- ofrecer flujos directos y sobrios.

### Database farmacia
- persistir datos de farmacia;
- sostener restricciones básicas del subdominio;
- servir como base evolutiva de farmacia.

### Backend farmacia
- aplicar reglas del catálogo y disponibilidad;
- exponer contratos administrativos y públicos controlados;
- integrar solo con `database-farmacia`.

### Storefront farmacia
- mostrar catálogo público;
- facilitar búsqueda y consulta externa.

## Señales de ruptura de frontera

Hay ruptura de frontera cuando:

- una UI decide reglas que deberían vivir en backend;
- un endpoint público revela información privada;
- un backend accede directamente a la base del otro subdominio;
- un módulo usa vocabulario del otro sin necesidad;
- la persistencia compartida reaparece por conveniencia técnica.

## Resultado esperado

Estas fronteras deben servir como criterio práctico al rellenar documentos, modelar tablas, diseñar DTOs, construir endpoints y decidir pantallas, de forma que el proyecto mantenga orden real y no solo orden aparente.