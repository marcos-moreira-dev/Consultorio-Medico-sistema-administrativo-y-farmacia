# Sistema visual y tokens

## Propósito

Definir los lineamientos del sistema visual del desktop y la idea de tokens que deberían sostenerlo cuando se implemente CSS JavaFX o el sistema visual definitivo.

## Principio general

La aplicación no debe depender de valores puestos a mano en cada vista. Conviene centralizar decisiones visuales repetibles para mantener consistencia entre shell, tablas, formularios, modales y dashboard.

## Tokens obligatorios para V1

### Espaciado
`4, 8, 12, 16, 24, 32, 40, 48`

### Radios
- `8px`
- `10px`

### Bordes
- borde sutil estándar para inputs y paneles;
- no usar bordes duros y oscuros por defecto.

### Sombras
- una sombra ligera para panel/card;
- una sombra de overlay para diálogos o modales.

### Tipografía
- título principal;
- subtítulo;
- texto base;
- texto secundario;
- texto de tabla.

Evitar multiplicar tamaños sin necesidad.

### Alturas de control
Conviene definir una altura uniforme para:
- `TextField`
- `ComboBox`
- `DatePicker`
- botones principales y secundarios

## Familias de tokens recomendadas

### Color
- gris de fondo principal;
- gris de superficie secundaria;
- blanco o gris muy claro para paneles;
- concho de vino como color institucional de acento;
- rojo controlado para acciones delicadas o error.

### Espaciado
- escala de márgenes y paddings;
- separación entre bloques;
- separación entre controles;
- respiración de tablas y formularios.

### Bordes y radios
- radios pequeños y consistentes;
- bordes suaves para inputs y paneles;
- nada de bordes pesados sin motivo.

### Sombra
- sombra ligera en tarjetas o paneles que la ameriten;
- overlay sobrio para modales.

## Aplicación esperada

Los tokens deben alimentar componentes como:
- sidebar;
- barra superior contextual;
- tablas;
- formularios;
- modales;
- dashboard;
- badges de estado.

## Regla importante

Los tokens deben simplificar la consistencia, no complicarla. Conviene mantener un sistema pequeño, útil y fácil de aplicar en CSS JavaFX.

## Qué no debe pasar

- valores hardcodeados por cada pantalla;
- radios, sombras y paddings distintos sin razón;
- demasiados tonos grises casi iguales compitiendo;
- jerarquías tipográficas arbitrarias.

## Resultado esperado

El sistema visual y tokens del desktop deben dejar una base suficientemente estable para que `desktop-consultorio-javafx` se vea unificado, controlado y mantenible desde la V1.
