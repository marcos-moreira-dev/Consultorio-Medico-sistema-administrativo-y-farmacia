# Componentes y contratos

## Propósito

Describir los componentes más relevantes del sistema y los contratos lógicos mediante los cuales interactúan, para evitar dependencias implícitas o acoplamientos confusos.

## Principio rector

En este proyecto, la comunicación entre componentes debe pasar por contratos explícitos y estables. Ningún cliente debe depender de detalles internos de persistencia ni de estructuras accidentales del backend.

## Componentes relevantes

### Cliente desktop consultorio
Consume contratos privados del backend consultorio.

**Necesita contratos para:**
- búsqueda y alta de pacientes;
- agenda de citas;
- registro de atención;
- registro de cobro;
- consulta de estados operativos.

## Backend consultorio
Expone contratos privados para uso interno del consultorio.

**Tipos de contrato esperados:**
- request DTOs para creación o actualización;
- response DTOs para vistas de paciente, cita, atención y cobro;
- respuestas uniformes para éxito;
- errores uniformes para fallos de validación o negocio.

**Dependencia de persistencia:**
- consume exclusivamente `database-consultorio`.

## Database consultorio
No expone contratos de integración directa a los clientes. Su relación es interna con el backend consultorio mediante repositorios, adaptadores o acceso de infraestructura controlado.

## Storefront farmacia
Consume contratos públicos o controlados del backend farmacia.

**Necesita contratos para:**
- listado de productos visibles;
- búsqueda simple;
- detalle público del producto;
- visibilidad de disponibilidad publicada.

## Backend farmacia
Expone contratos administrativos y públicos según la superficie consumidora.

**Tipos de contrato esperados:**
- contratos privados para alta, edición, publicación, disponibilidad e inactivación;
- contratos públicos para catálogo visible;
- DTOs separados para administración y publicación;
- contratos evolutivos para reservas en V1.1.

**Dependencia de persistencia:**
- consume exclusivamente `database-farmacia`.

## Database farmacia
No expone contratos de integración directa a los clientes. Su relación es interna con el backend farmacia mediante repositorios, adaptadores o acceso de infraestructura controlado.

## Contratos por superficie

### Contratos privados del consultorio
Deben permitir trabajar con información sensible sin filtrarla a otros contextos.

**Ejemplos lógicos:**
- crear paciente;
- buscar paciente;
- agendar cita;
- registrar atención;
- registrar cobro.

### Contratos administrativos de farmacia
Deben permitir sostener el catálogo y la disponibilidad desde una superficie interna.

**Ejemplos lógicos:**
- crear producto;
- editar producto;
- publicar o despublicar producto;
- ajustar disponibilidad;
- inactivar producto.

### Contratos públicos de farmacia
Deben ser sobrios y limitados a lo publicable.

**Ejemplos lógicos:**
- listar catálogo visible;
- buscar productos visibles;
- consultar detalle público de producto.

## Principios de contrato

### 1. DTOs explícitos
No se exponen entidades JPA ni modelos de persistencia directamente.

### 2. Respuestas consistentes
Los contratos exitosos deben seguir una convención uniforme del proyecto, especialmente en superficies privadas y administrativas.

### 3. Errores uniformes
Los errores deben seguir un formato estable y documentable, evitando respuestas improvisadas.

### 4. Separación entre contratos públicos y privados
Un contrato diseñado para un cliente externo no debe arrastrar datos internos ni semántica administrativa innecesaria.

### 5. Evolución compatible
Los contratos deben permitir que la V1.1 amplíe capacidades sin romper sin necesidad a los consumidores.

### 6. Sin contratos de acceso cruzado a persistencia
Consultorio y farmacia no deben integrar sus bases mediante acceso técnico directo. Si surge una necesidad de comunicación futura, debe resolverse con contratos de aplicación entre backends o con identificadores externos controlados.

## Tensiones de diseño relevantes

- un mismo backend puede exponer contratos de distinta sensibilidad;
- no todo DTO interno debe ser reutilizado en público;
- mayor uniformidad contractual mejora claridad, pero también exige disciplina documental;
- separar persistencia obliga a que cualquier integración entre subdominios sea explícita y no accidental.

## Resultado esperado

Este documento debe servir como puente entre arquitectura, backend y clientes, dejando claro que la integración del sistema se apoya en contratos pensados y en persistencias separadas, no en acoplamientos accidentales.