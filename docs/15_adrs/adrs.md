# ADRs

## Propósito

Definir cómo se usarán los *Architecture Decision Records* dentro del proyecto para capturar decisiones técnicas importantes de forma breve, clara y útil.

## Principio general

Las ADRs del proyecto deben ser concretas, legibles y orientadas a decisiones reales. No se escriben para inflar documentación, sino para dejar constancia de elecciones que afectan la estructura del sistema y su evolución.

## Cuándo crear una ADR

Debe considerarse una ADR cuando se tome una decisión que:

- cambie la arquitectura del sistema;
- afecte límites entre contextos o componentes;
- introduzca un tradeoff relevante;
- condicione la evolución futura del proyecto;
- altere despliegue, seguridad, persistencia o contratos de forma importante.

## Política de uso en este proyecto

### ADR-001. Registrar solo decisiones con impacto real
Las ADRs deben reservarse para decisiones que valga la pena volver a consultar meses después.

### ADR-002. Una ADR debe explicar contexto, decisión y consecuencias
No basta con registrar la conclusión. Debe entenderse qué problema resolvía y qué se aceptó a cambio.

### ADR-003. La ADR debe poder leerse sin depender de una conversación previa
Su contenido debe ser autosuficiente en lo esencial.

### ADR-004. Una ADR puede quedar reemplazada, no borrada conceptualmente
Si una decisión cambia, conviene registrar la nueva y marcar la anterior como superada, en lugar de fingir que nunca existió.

## Estados recomendados de una ADR

- propuesta;
- aceptada;
- reemplazada;
- descartada.

## Decisiones candidatas claras para este proyecto

### 1. Separación de consultorio y farmacia como contextos
Decisión estructural central del proyecto.

### 2. Arquitectura modular con componentes diferenciados
Incluyendo backends separados o equivalente modular fuerte.

### 3. Uso de DTOs explícitos y respuestas uniformes
Impacta contratos, clientes y backend.

### 4. Despliegue lógico simple pero modular para la V1
Importante para demo, estudio y evolución.

### 5. Evolución V1.0 → V1.1 con migraciones reales
Importante para persistencia y mantenimiento.

### 6. Separación entre superficie pública y privada
Importante para seguridad, UX y diseño de APIs.

## Forma recomendada de trabajo

1. identificar una decisión relevante;
2. redactar su contexto;
3. registrar alternativas si vale la pena;
4. fijar la decisión elegida;
5. anotar consecuencias y tradeoffs;
6. vincularla mentalmente con documentos del proyecto que dependan de ella.

## Qué no debe pasar con las ADRs

- que se vuelvan ensayos larguísimos;
- que documenten obviedades;
- que se dupliquen con documentos de contexto sin aportar nada nuevo;
- que queden tan vagas que no sirvan para revisar una decisión después.

## Resultado esperado

Las ADRs deben funcionar como memoria de arquitectura del proyecto: suficiente para recordar por qué se eligió algo importante y útil para sostener coherencia cuando el sistema crezca o cambie.

