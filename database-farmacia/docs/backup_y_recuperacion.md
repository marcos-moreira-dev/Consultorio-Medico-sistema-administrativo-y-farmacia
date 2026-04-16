# Backup y recuperación

## Propósito

Definir criterios mínimos de respaldo y recuperación para `database-farmacia`, de forma que el componente pueda restaurarse, estudiarse y demostrarse sin depender de memoria informal ni de una única instancia frágil.

## Principio general

Aunque esta base se use en un entorno local y pedagógico, sigue siendo importante asumir que pueden ocurrir errores de modelado, cambios destructivos, migraciones fallidas o pérdida de datos demo. La estrategia debe ser proporcional, pero real.

## Qué conviene proteger

### Esquema
La estructura de tablas, constraints, índices y catálogos de farmacia.

### Migraciones
Son esenciales para reconstruir la evolución del componente.

### Seeds demo
Permiten recuperar un entorno útil para estudio y demostración.

### Datos de trabajo controlados
Si se usan datasets demo elaborados, conviene poder restaurarlos.

## Estrategia mínima recomendada

### 1. Respaldo lógico del esquema y datos
Debe existir una forma razonable de exportar y restaurar la base de farmacia.

### 2. Recuperación basada en migraciones y seeds
La reconstrucción del componente no debe depender solo de copiar una base opaca. También debe poder rehacerse desde scripts versionados.

### 3. Punto de restauración para demo
Conviene mantener un estado base de farmacia que permita volver rápidamente a un entorno presentable.

## Cuándo conviene respaldar

- antes de cambios destructivos en esquema;
- antes de probar migraciones riesgosas;
- antes de limpiar datos demo significativos;
- antes de una presentación o revisión importante.

## Escenarios de recuperación considerados

### Escenario 1. Esquema inconsistente
Se reconstruye a partir de migraciones válidas y, si hace falta, respaldo lógico previo.

### Escenario 2. Datos demo corrompidos o caóticos
Se reinstalan seeds controlados.

### Escenario 3. Entorno nuevo
Se reconstruye el componente con documentación, migraciones, seeds y configuración mínima.

## Qué debe evitarse

- depender de una única copia viva sin respaldo;
- mezclar backups de consultorio con los de farmacia;
- usar datos sensibles reales como respaldo de práctica;
- trabajar migraciones riesgosas sin posibilidad de volver atrás.

## Resultado esperado

La estrategia de backup y recuperación debe darte seguridad mínima para estudiar, iterar y demostrar `database-farmacia` sin miedo a que un error de esquema o de datos te obligue a empezar desde cero cada vez.