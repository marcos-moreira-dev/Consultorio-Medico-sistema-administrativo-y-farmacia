# Auditoría y trazabilidad BD

## Propósito

Definir qué nivel de rastro y control conviene sostener desde la base de datos de farmacia, distinguiendo entre trazabilidad útil y auditoría más deliberada.

## Principio general

La base de farmacia no debe convertirse en una bitácora infinita, pero sí debe permitir reconstruir ciertos hechos importantes del subdominio cuando sea necesario para estudio, diagnóstico o control operativo.

## Qué conviene poder rastrear desde la BD

### Producto
- creación del registro;
- actualización relevante de datos;
- cambio de publicación;
- inactivación;
- cambio relevante de disponibilidad.

### Reserva
- creación;
- cancelación;
- concreción o cierre, si V1.1 la incorpora.

## Trazabilidad mínima esperada

Como mínimo, el componente debería poder sostener:

- timestamps de creación y actualización donde aplique;
- relaciones claras entre entidades;
- rastro suficiente para entender secuencia operativa básica.

## Auditoría útil en esta V1

No se busca una plataforma de compliance pesada, pero sí tiene sentido considerar más control sobre:

- publicación y despublicación;
- inactivación de producto;
- cambios importantes de disponibilidad;
- reservas si la evolución futura las incorpora.

## Decisiones a cuidar

### 1. No duplicar innecesariamente lo que ya vive mejor en aplicación
No todo debe auditarse a nivel relacional si el backend ya lo resuelve mejor.

### 2. No exponer contenido interno por querer trazar demasiado
La auditoría no debe transformarse en fuga de información operativa innecesaria.

### 3. Mantener proporcionalidad
El rastro debe ser útil para una farmacia pequeña, no una estructura empresarial desbordada.

## Resultado esperado

La base de farmacia debe poder sostener un nivel razonable de trazabilidad y auditoría útil, suficiente para estudiar el comportamiento del sistema y apoyar diagnóstico, sin perder simplicidad ni claridad del subdominio.

