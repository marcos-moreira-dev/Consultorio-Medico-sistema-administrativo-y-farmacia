# Comentarios y documentación en código

## Propósito

Definir cuándo comentar, cómo documentar y qué nivel de explicación conviene dejar dentro del código para que el proyecto siga siendo legible sin llenarse de ruido.

## Principio general

El código debe intentar ser claro por sí mismo. Los comentarios no existen para repetir lo obvio, sino para explicar intención, decisiones, restricciones y razones que no se ven de inmediato.

## Cuándo sí conviene comentar

### 1. Cuando una decisión no es evidente
Por ejemplo, una validación particular, un tradeoff o una restricción del dominio.

### 2. Cuando una parte del código implementa una regla importante del negocio
Especialmente si esa regla tiene impacto en agenda, cobro, publicación o privacidad.

### 3. Cuando un bloque responde a una limitación técnica o del entorno
Por ejemplo, una adaptación específica del stack, del arranque o de la integración.

### 4. Cuando una API, clase o componente necesita documentación de uso
Especialmente en puntos de integración o contratos internos.

## Cuándo no conviene comentar

- para describir línea por línea lo que ya se entiende leyendo el código;
- para dejar comentarios redundantes como “asignar variable” o “guardar en base”;
- para mantener comentarios viejos que ya no coinciden con el comportamiento real.

## Reglas recomendadas

### DOC-001. Comentar intención, no obviedad

### DOC-002. Mantener comentarios sincronizados con el código
Un comentario desactualizado puede ser peor que no tener comentario.

### DOC-003. Preferir documentación corta y útil
La explicación debe ayudar a comprender, no tapar el código.

### DOC-004. Documentar contratos y puntos de integración
Clases, endpoints, DTOs o servicios relevantes deben tener contexto suficiente cuando ayude al mantenimiento.

## Relación con la documentación del proyecto

La documentación estratégica vive fuera del código, en los markdown del proyecto. El código debe apoyarse en esa documentación, no intentar reemplazarla por completo.

## Resultado esperado

Los comentarios y la documentación en código deben ayudar a entender mejor el sistema sin convertir el repositorio en una pared de texto redundante o desactualizada.