# Criterios de revisión

## Propósito

Definir qué debe revisarse antes de considerar correcto un cambio dentro del proyecto, de modo que la evolución del sistema no dependa solo de que “compila” o “parece funcionar”.

## Principio general

Revisar bien significa comprobar coherencia, no solo detectar errores sintácticos. Un cambio aceptable debe respetar dominio, contratos, estructura, seguridad y claridad del sistema.

## Criterios mínimos de revisión

### 1. Coherencia con el dominio
¿El cambio respeta reglas del consultorio o farmacia y no inventa comportamientos contradictorios?

### 2. Coherencia con la arquitectura
¿El cambio mantiene fronteras y responsabilidades o está metiendo lógica donde no corresponde?

### 3. Coherencia con contratos
¿Afecta DTOs, endpoints o respuestas? Si los afecta, ¿se actualizó de forma consistente?

### 4. Impacto en seguridad y privacidad
¿Expone algo que no debería? ¿mezcla superficies pública y privada?

### 5. Impacto en logs, errores y diagnóstico
¿Los fallos siguen siendo interpretables y coherentes?

### 6. Impacto en despliegue o configuración
¿El cambio requiere actualizar scripts, variables, puertos o documentación operativa?

## Revisión de calidad del cambio

### REV-001. El nombre de lo nuevo debe ser claro y consistente

### REV-002. El cambio debe ser legible sin depender de intuiciones privadas del autor

### REV-003. El código no debe introducir atajos que rompan capas o contratos

### REV-004. La documentación debe actualizarse cuando el cambio lo exija

### REV-005. Si el cambio toca reglas críticas, debe acompañarse de verificación o pruebas razonables

## Señales de alerta en revisión

- lógica de negocio metida en controlador o UI;
- contrato público reutilizando respuesta interna sensible;
- validación importante ausente en backend;
- nombres ambiguos o inconsistentes;
- cambio funcional sin ajuste documental;
- nueva complejidad sin justificación clara.

## Resultado esperado

Los criterios de revisión deben ayudarte a mantener una disciplina práctica: cada cambio debe revisarse por su efecto real en el sistema, no solo por su apariencia local o por el hecho de que no da error al arrancar.