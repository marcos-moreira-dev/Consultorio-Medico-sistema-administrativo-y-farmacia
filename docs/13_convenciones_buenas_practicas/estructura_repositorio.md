# Estructura del repositorio

## Propósito

Definir cómo debe organizarse el repositorio para que el proyecto siga siendo legible, modular y fácil de recorrer.

## Principio general

La estructura del repositorio debe reflejar el sistema real: contexto maestro, dominios, arquitectura, despliegue y luego componentes técnicos con su propia documentación y código. El orden físico del proyecto debe ayudar a entender su orden mental.

## Capas de organización esperadas

### 1. Documentación transversal
La carpeta `docs/` contiene el marco global del proyecto: contexto, dominio, arquitectura, seguridad, trazabilidad, despliegue, buenas prácticas, UX y checklists.

### 2. Componentes técnicos
Cada gran componente técnico debe tener su propio espacio y su propia carpeta `docs` local.

**Ejemplos:**
- base de datos;
- backend consultorio;
- backend farmacia;
- desktop consultorio;
- storefront farmacia.

### 3. Historial de documentos llenados
La carpeta `llenados` conserva versiones concretas desarrolladas a partir de la estructura documental base.

## Reglas de organización

### ESTR-001. Un directorio debe tener una responsabilidad dominante
No conviene mezclar dominio, implementación y documentación local sin criterio.

### ESTR-002. La documentación global define; la local aterriza
Lo transversal vive en `docs/`. Lo específico de cada componente vive cerca del componente.

### ESTR-003. Los nombres de carpetas deben insinuar propósito
La organización debe ser comprensible incluso antes de abrir archivos.

### ESTR-004. La estructura debe facilitar navegación humana
No organizar solo para la máquina o para el build. También para el estudio, revisión y explicación.

## Qué evitar

- carpetas genéricas sin contexto;
- mezclar utilitarios, dominio e infraestructura sin orden;
- poner documentación estratégica dispersa dentro de código suelto;
- crecer por acumulación caótica de archivos sueltos en raíz.

## Resultado esperado

La estructura del repositorio debe transmitir orden profesional: qué parte define el proyecto, qué parte lo implementa y dónde vive cada responsabilidad sin forzar búsquedas innecesarias.