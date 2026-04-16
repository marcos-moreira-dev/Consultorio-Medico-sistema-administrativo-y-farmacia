# Criterios UX

## Propósito

Definir los principios de experiencia de usuario que deben guiar la construcción de las interfaces del proyecto, tanto en la superficie privada del consultorio como en la superficie pública o administrativa de farmacia.

## Principio general

La UX del sistema debe hacer que operar sea sencillo, rápido y comprensible. No se diseña para entretener ni para exhibir adornos, sino para reducir fricción, evitar errores y sostener una sensación de control.

## Criterios globales

### UX-001. Claridad antes que ornamentación
La interfaz debe priorizar lectura, jerarquía visual y entendimiento inmediato por encima de decoraciones innecesarias.

### UX-002. Cada pantalla debe tener una tarea dominante
No conviene llenar una vista con demasiadas intenciones mezcladas. Buscar, registrar, atender, cobrar, consultar o publicar deben sentirse como acciones reconocibles.

### UX-003. El lenguaje debe ser comprensible
Los textos visibles deben evitar tecnicismo innecesario, especialmente para usuarios operativos no técnicos.

### UX-004. La interfaz debe reflejar el dominio real
No diseñar pantallas genéricas que ignoren los flujos concretos del consultorio o de la farmacia.

### UX-005. La fricción debe estar donde aporta control, no donde estorba
Confirmaciones, validaciones y bloqueos deben existir cuando evitan errores reales, no como burocracia visual.

## Criterios para consultorio

### UX-C-001. Operación rápida y privada
La interfaz del consultorio debe favorecer búsqueda rápida, continuidad de flujo y discreción visual.

### UX-C-002. Jerarquía centrada en paciente, agenda, atención y cobro
Las tareas centrales deben estar visibles y encadenadas sin menús laberínticos.

### UX-C-003. Formularios contenidos
No forzar formularios clínicos largos en la V1.

### UX-C-004. Estados claros
Programada, cancelada, atendida, pagado, pendiente y otros estados deben distinguirse sin ambigüedad.

## Criterios para farmacia

### UX-F-001. Catálogo público simple
La capa pública debe facilitar búsqueda, escaneo visual y comprensión rápida del producto.

### UX-F-002. Administración operativa sobria
La parte administrativa de farmacia debe permitir editar, publicar, inactivar o ajustar disponibilidad sin complejidad innecesaria.

### UX-F-003. Separación entre lo público y lo interno
La experiencia pública no debe heredar lenguaje, campos ni densidad visual propios de la administración interna.

## Criterios de navegación

### UX-N-001. Navegación predecible
La persona usuaria debe intuir dónde está y cómo volver o continuar.

### UX-N-002. Acciones principales visibles
Crear, guardar, cancelar, publicar, buscar o cobrar no deben quedar escondidas sin motivo.

### UX-N-003. Menos profundidad, más foco
Para esta V1 conviene evitar árboles de navegación excesivamente profundos.

## Criterios de validación y feedback

### UX-V-001. Errores comprensibles
El sistema debe explicar qué está mal de manera concreta.

### UX-V-002. Confirmaciones proporcionales
No toda acción necesita modal de confirmación, pero los cambios de estado importantes sí pueden requerirlo.

### UX-V-003. Éxitos visibles pero sobrios
La interfaz debe confirmar acciones completadas sin interrumpir el flujo más de lo necesario.

## Criterios UX ejecutables para V1

### UX-X-001. Cada vista debe tener una zona dominante
Toda pantalla debe dejar claro qué bloque manda:
- tabla;
- formulario;
- catálogo;
- detalle;
- dashboard.

No mezclar dos zonas dominantes compitiendo por peso visual.

### UX-X-002. Prioridad de acciones
En cada vista debe existir:
- una acción principal;
- cero a dos acciones secundarias visibles;
- acciones delicadas separadas visualmente.

Evitar tres o más CTA con el mismo peso.

### UX-X-003. Confirmaciones proporcionales
Usar confirmación modal solo en:
- cambios de estado relevantes;
- eliminaciones;
- acciones irreversibles;
- cierres con riesgo de pérdida de datos.

### UX-X-004. Estados visibles y sobrios
Loading, vacío, error y éxito deben existir como estados explícitos, pero sin dramatismo visual.

### UX-X-005. Navegación con profundidad controlada
Para V1, evitar profundidad excesiva. La persona usuaria debe poder:
- llegar;
- operar;
- volver;
sin perder contexto.

### UX-X-006. Formularios con agrupación semántica
No apilar campos sin estructura. Los formularios deben agruparse por bloques de negocio y cerrar con acciones claras al pie.

### UX-X-007. Tablas con lectura operativa
Las tablas no son decoración. Deben priorizar:
- comprensión rápida;
- acciones claras por fila;
- estados distinguibles;
- filtros sobrios.

## Resultado esperado

Estos criterios deben servir como base para wireframes, decisiones visuales, mensajes y comportamiento de pantallas, manteniendo una UX seria, clara y compatible con la realidad operativa del proyecto.
