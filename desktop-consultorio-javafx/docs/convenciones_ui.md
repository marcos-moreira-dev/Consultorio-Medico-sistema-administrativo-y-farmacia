# Convenciones UI

## Propósito

Definir reglas de consistencia visual, espacial y de interacción para `desktop-consultorio-javafx`, de forma que la interfaz se mantenga homogénea incluso cuando crezca en pantallas, módulos y componentes.

## Principio general

Las convenciones UI existen para reducir fricción, evitar arbitrariedad y hacer que la aplicación se sienta como un único producto coherente. No deben verse como decoración documental, sino como reglas prácticas de diseño e implementación.

## Convenciones de color

### Regla base
- grises y blancos dominan;
- concho de vino actúa como color secundario institucional;
- rojo solo se usa de forma puntual;
- naranja, si aparece, es mínimo.

### Regla de prioridad
No introducir colores nuevos por pantalla si no tienen razón de sistema.

## Convenciones de tipografía

### Títulos
Deben ser claros, de alto peso visual y fáciles de escanear.

### Subtítulos
Deben contextualizar sin competir con el título.

### Texto de tabla y formularios
Debe privilegiar legibilidad sobre estilo.

### Texto secundario
Debe bajar contraste, no desaparecer.

## Convenciones de layout

### Shell
Siempre estable:
- `MenuBar` arriba;
- barra superior contextual;
- sidebar izquierda;
- contenido al centro.

### Contenido de módulo
Debe organizarse como:
- título;
- subtítulo;
- filtros o acciones;
- contenido principal;
- pie con paginación o acciones secundarias si aplica.

### Márgenes y spacing
Se adopta la escala oficial del proyecto:

`4, 8, 12, 16, 24, 32, 40, 48`

Uso recomendado en desktop:

- `8px`: separación pequeña entre controles muy relacionados;
- `12px` o `16px`: separación normal entre campos, filtros y acciones;
- `16px`: padding interno de panel estándar;
- `24px`: padding de contenedor principal o bloque importante;
- `24px` o `32px`: separación entre bloques grandes de una vista.

No usar valores arbitrarios como `13px`, `19px`, `21px` o equivalentes salvo caso excepcional muy justificado.

### Contenedores y paneles
- no anidar más de dos niveles de panel visual fuerte;
- preferir separación por `VBox`, `HBox`, `GridPane`, `Insets` y títulos antes que multiplicar bordes;
- un panel debe agrupar semánticamente, no decorar sin razón;
- evitar interfaces donde todo parezca una tarjeta independiente.

## Convenciones de botones

### Botón primario
- concho de vino;
- usado para acción principal de la vista.

### Botón secundario
- neutro o claro;
- usado para acciones auxiliares.

### Botón delicado
- rojo puntual o variante muy controlada;
- usado en acciones sensibles.

### Regla de prioridad
No colocar tres botones primarios compitiendo en el mismo contexto.

## Convenciones de inputs

- altura uniforme;
- borde suave;
- radio moderado;
- label clara arriba o muy próxima;
- placeholder útil, no decorativo.

## Convenciones de formularios

- agrupar campos relacionados;
- no crear formularios visualmente infinitos;
- usar `GridPane` cuando la alineación semántica lo pida;
- usar `VBox` cuando la lectura vertical sea más clara.

## Convenciones de tablas

- cabecera oscura o claramente diferenciada;
- filas legibles;
- acciones agrupadas al final;
- estados visibles con `StatusBadge`;
- no recargar cada celda con colores o controles innecesarios.

## Convenciones de modales

- centrados;
- overlay sobrio;
- header claro;
- footer con acciones distinguibles;
- evitar modales gigantes para tareas pequeñas.

## Convenciones de dashboard

- hero principal con métrica dominante;
- KPIs secundarios bien agrupados;
- gráfico solo si aporta;
- nada de paneles decorativos sin sentido operativo.

## Convenciones de navegación

- módulo activo siempre visible en sidebar;
- no confundir al usuario respecto a dónde está;
- el cambio de módulo no rompe el shell;
- modales no reemplazan navegación, solo la complementan.

## Convenciones de estados visuales

### Loading
Debe ser visible, sobrio y no intrusivo.

### Error
Debe ser claro, sin dramatismo innecesario.

### Éxito
Debe confirmar sin interrumpir demasiado.

### Vacío
Debe orientar, no parecer fallo.

## Qué no debe pasar

- pantallas con radios aleatorios;
- cada módulo con spacing distinto;
- exceso de tarjetas o paneles por decorar;
- contrastes pobres;
- componentes “bonitos” pero poco operativos.

## Resultado esperado

Las convenciones UI del desktop deben ayudar a que toda la app se sienta madura, estable y claramente operativa, evitando que la implementación futura derive en un collage visual.
