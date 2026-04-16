# Backup y recuperación

## Propósito

Definir criterios mínimos de respaldo y recuperación para la V1, de forma que el sistema no dependa por completo de la suerte, la memoria o el estado accidental de una sola máquina.

## Principio general

Aunque este proyecto opere principalmente en local y con fines de estudio o demo, sigue siendo importante asumir que pueden ocurrir fallos, errores humanos, cambios destructivos o pérdida de datos. El objetivo no es montar una estrategia empresarial compleja, sino una práctica sana y proporcional.

## Qué conviene proteger

### Database consultorio
Es el activo operativo principal del subdominio privado.

### Database farmacia
Es el activo operativo principal del subdominio comercial.

### Scripts y configuración clave
Son necesarios para reconstruir el entorno de forma razonable.

### Seeds y migraciones
Permiten recrear estructura y datos controlados de forma reproducible.

### Datos demo preparados
Ayudan a recuperar rápidamente un entorno presentable.

## Estrategia mínima recomendada

### 1. Respaldo lógico de cada base de datos
Mantener la posibilidad de exportar y restaurar `database-consultorio` y `database-farmacia` de forma independiente.

### 2. Configuración versionada o documentada
Lo no sensible debe estar claramente documentado y lo sensible debe poder reponerse sin depender de improvisación.

### 3. Recuperación basada en migraciones y seeds
La reconstrucción del entorno no debe depender solo de copiar archivos opacos.

### 4. Punto de restauración para demo
Conviene contar con un estado de datos demo estable que pueda recuperarse antes de una presentación.

## Cuándo conviene respaldar

- antes de cambios destructivos en cualquiera de las bases;
- antes de reinicializar o limpiar datos demo importantes;
- antes de introducir migraciones riesgosas;
- antes de una demo relevante.

## Escenarios de recuperación considerados

### Escenario 1. Database consultorio dañada o inconsistente
Se reconstruye usando respaldo, migraciones y seeds del consultorio.

### Escenario 2. Database farmacia dañada o inconsistente
Se reconstruye usando respaldo, migraciones y seeds de farmacia.

### Escenario 3. Entorno nuevo o reinstalado
Se recupera a partir de documentación, configuración, migraciones y scripts por subdominio.

### Escenario 4. Demo rota por pruebas previas
Se restaura un conjunto controlado de datos y se reinicia el flujo esperado.

## Reglas de diseño derivadas

### BAK-001. La recuperación debe ser reproducible
No debe depender de pasos secretos o irrepetibles.

### BAK-002. El respaldo no debe incluir exposición innecesaria de datos sensibles
Incluso en contexto local, debe cuidarse el contenido que se copia o comparte, especialmente el del consultorio.

### BAK-003. Migraciones y seeds son parte de la estrategia de recuperación
No solo de la estrategia de desarrollo.

### BAK-004. Debe existir al menos una forma razonable de volver a un estado demostrable
Especialmente importante para portafolio y práctica.

### BAK-005. La recuperación debe respetar separación de persistencia
No conviene fusionar backups de ambos subdominios como si se tratara de una sola base.

## Resultado esperado

La estrategia de backup y recuperación debe darte una red mínima de seguridad operativa: suficiente para trabajar con más confianza, experimentar con menos miedo y recuperar el sistema sin empezar desde cero, manteniendo además independencia entre la recuperación del consultorio y la de la farmacia.