# Sistema visual y tokens

## Propósito

Definir los lineamientos del sistema visual del storefront y la idea de tokens que deberían sostenerlo cuando se implemente CSS o el framework visual elegido.

## Principio general

El sitio no debe depender de valores puestos a mano en cada componente. Conviene centralizar decisiones visuales repetibles para mantener consistencia.

## Tokens obligatorios para V1

### Espaciado
`4, 8, 12, 16, 24, 32, 40, 48`

### Radios
- `10px`
- `12px`
- `14px`

### Sombras
- una sombra ligera para cards;
- una sombra de overlay para modales o capas flotantes;
- no usar múltiples sombras por componente.

### Color
Definir únicamente:
- fondo principal;
- fondo secundario;
- texto principal;
- texto secundario;
- acento principal;
- éxito;
- advertencia;
- error.

### Tipografía
Definir solo:
- título hero;
- título de sección;
- subtítulo;
- texto base;
- texto secundario;
- etiqueta pequeña o badge.

### Regla de disciplina
Todo valor visual repetido debe salir de token.
No hardcodear tamaños, radios, paddings o sombras en componentes aislados.

## Familias de tokens recomendadas

### Color
- fondo principal;
- fondo secundario;
- texto principal;
- texto secundario;
- color de acento;
- estados como éxito, advertencia y error.

### Espaciado
- escala de márgenes y paddings;
- separación entre secciones;
- separación interna de cards y formularios.

### Bordes y radios
- radios de cards;
- radios de botones;
- bordes suaves para inputs y contenedores.

### Sombra
- sombra ligera de cards;
- sombra de overlays o modales si luego existen.

## Aplicación esperada

Los tokens deben alimentar componentes como:
- cards de producto;
- botones;
- badges de disponibilidad;
- header y footer;
- buscador y filtros;
- estados vacíos o de error.

## Regla importante

Los tokens deben simplificar la consistencia, no complicarla. Conviene mantener un sistema pequeño y útil para V1.

## Qué no debe pasar

- valores hardcodeados por todas partes;
- cinco tonos casi iguales compitiendo sin necesidad;
- radios, sombras y espaciados incoherentes entre componentes.

## Resultado esperado

El sistema visual y los tokens deben dejar una base suficientemente estable para que el storefront se vea unificado, aunque todavía esté en una fase temprana de implementación.
