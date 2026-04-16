# Testing y calidad

## Propósito

Fijar una postura práctica sobre pruebas y calidad para que el proyecto sea confiable, demostrable y evolutivo sin exigir una maquinaria de testing desproporcionada para esta V1.

## Principio general

La calidad no depende solo de tener muchas pruebas. Depende de probar lo importante, revisar con criterio y mantener coherencia entre dominio, contratos y comportamiento real.

## Objetivos de calidad en la V1

- validar flujos principales del consultorio;
- validar catálogo y disponibilidad de farmacia;
- proteger reglas críticas del dominio;
- evitar regresiones evidentes al evolucionar a V1.1;
- mantener confianza suficiente para demo y estudio.

## Niveles de prueba recomendados

### 1. Pruebas de dominio o lógica crítica
Para reglas como:
- no solapar citas;
- no cobrar sin atención;
- no publicar productos inválidos;
- no mostrar productos agotados como disponibles.

### 2. Pruebas de aplicación o casos de uso
Para verificar que los flujos importantes se coordinan correctamente.

### 3. Pruebas de contratos o endpoints
Para confirmar respuestas, validaciones y errores uniformes.

### 4. Pruebas manuales guiadas
Siguen siendo útiles para desktop, storefront y demo, siempre que estén estructuradas y no sean puro azar.

## Reglas de calidad

### QLT-001. Probar lo que rompe negocio o demo
No hace falta cubrir todo con el mismo nivel de profundidad.

### QLT-002. Cada cambio importante debe revisarse con impacto en dominio, contrato y operación

### QLT-003. La V1.1 debe acompañarse de pruebas sobre cambios de estado nuevos
Especialmente agenda más madura y reservas de farmacia.

### QLT-004. Las pruebas deben ser entendibles
Una prueba que nadie entiende pierde valor pedagógico y de mantenimiento.

## Señales de mala calidad

- el sistema solo funciona en el “camino feliz”;
- cambiar un flujo rompe otro sin aviso;
- una regla crítica vive solo en UI y no en backend;
- los contratos cambian sin revisión;
- la demo depende de suerte y no de comportamiento confiable.

## Resultado esperado

La estrategia de testing y calidad debe ser proporcional al proyecto: suficiente para sostener confianza real, aprendizaje sólido y evolución ordenada, sin volverse una carga ceremonial.