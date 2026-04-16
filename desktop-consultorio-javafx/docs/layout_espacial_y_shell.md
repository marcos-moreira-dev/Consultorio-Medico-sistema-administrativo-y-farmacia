# Layout espacial y shell

## Propósito

Describir de forma espacial y técnica cómo debe componerse la ventana principal de `desktop-consultorio-javafx`, qué contenedores JavaFX se usarán y cómo se distribuyen paneles, márgenes, espaciados y zonas funcionales.

## Principio general

Este documento debe funcionar casi como una guía espacial de implementación. No basta con decir “habrá un sidebar y un contenido”. Hay que describir:

- contenedores JavaFX;
- jerarquía de zonas;
- anchos y alturas aproximadas;
- márgenes y espaciados;
- prioridad visual de cada bloque;
- comportamiento del shell.

## Shell principal recomendado

La estructura base de la ventana principal debe construirse sobre un `BorderPane` raíz.

### Distribución general del `BorderPane`
- **top**: `VBox` conteniendo `MenuBar` y barra superior contextual.
- **left**: `VBox` del sidebar de navegación.
- **center**: `StackPane` o `BorderPane` de contenido dinámico del módulo activo.

## Reglas espaciales obligatorias

### Shell
El shell se considera una estructura estable de tres zonas:

1. `MenuBar` superior;
2. barra contextual superior del módulo;
3. cuerpo con sidebar izquierda y contenido principal.

### Proporción del contenido
- el contenido principal debe dominar visualmente;
- la sidebar acompaña, no compite;
- la barra contextual no debe crecer hasta parecer una segunda pantalla.

### Padding del contenido
- padding principal del área central: `24px`;
- separación entre toolbar y contenido: `16px`;
- separación entre bloques grandes dentro de una vista: `24px` o `32px`.

### Regla de composición
La estructura del módulo debe tender a:
- título;
- subtítulo o contexto breve;
- toolbar o filtros;
- contenido principal;
- pie operativo si aplica.

No abrir con varios paneles rivales sin jerarquía clara.

## Ventana principal

### `Stage`
Debe representar una app de escritorio clásica, redimensionable y con soporte para pantalla completa.

### `Scene`
Debe contener un `BorderPane` como raíz general del shell.

### Tamaño base recomendado
No fijar un tamaño rígido minúsculo. El layout debe pensarse para un tamaño de trabajo cómodo.

### Estado base
- ventana normal de escritorio;
- redimensionable;
- soporte de pantalla completa con `F11`.

## Zona superior

## `MenuBar`
Debe ocupar la parte más alta del shell.

### Altura aproximada
Entre `30px` y `36px`.

### Rol
- exponer acciones clásicas de escritorio;
- reforzar sensación de app real;
- aportar navegación secundaria y accesos rápidos.

## Barra superior de contexto
Debajo del `MenuBar` debe existir una barra superior visualmente integrada al shell.

### Contenedor sugerido
`HBox`

### Altura aproximada
Entre `56px` y `72px`.

### Contenido típico
- logo pequeño del consultorio o del software;
- nombre del sistema;
- nombre del usuario o estado de sesión;
- tal vez contexto del módulo actual o breadcrumbs suaves si se justifica.

### Padding interno sugerido
- vertical: `10px` a `14px`
- horizontal: `18px` a `24px`

### Espaciado interno sugerido
`12px` a `16px`

## Sidebar izquierdo

### Contenedor sugerido
`VBox`

### Ancho fijo recomendado
Entre `220px` y `260px`.

### Función
- navegación primaria persistente;
- acceso a módulos;
- acciones como cerrar sesión;
- branding mínimo en parte baja o alta si se desea.

### Estructura interna del sidebar
- bloque superior de navegación;
- bloque medio de botones de módulos;
- bloque inferior con branding menor, acceso de soporte o ayudas si aplica.

### Padding interno sugerido
- `16px` en vertical
- `12px` a `16px` en horizontal

### Espaciado entre bloques
`10px` a `14px`

### Botones de navegación
Cada opción de módulo debería vivir en un `Button` de ancho casi completo dentro del `VBox`.

#### Altura sugerida de cada botón
Entre `42px` y `52px`.

#### Separación vertical sugerida
`8px` a `10px`

## Zona central de contenido

### Contenedor sugerido
`StackPane` como host general, con un `ScrollPane` interno cuando el módulo lo requiera.

Otra opción válida es usar un `BorderPane` interno por módulo, pero el host del shell debe ser estable.

### Fondo
Gris claro o blanco grisáceo, nunca saturado.

### Padding general del contenido
- `20px` a `24px` en horizontal
- `18px` a `24px` en vertical

### Espaciado entre bloques de contenido
`12px` a `18px`

## Estructura típica de un módulo de pantalla

Cada pantalla grande del sistema debería organizarse preferentemente con un `VBox` principal dentro del contenido central.

### Orden recomendado dentro de ese `VBox`
1. título del módulo;
2. subtítulo o texto de contexto;
3. barra de acciones o filtros;
4. contenido principal;
5. paginación o acciones secundarias al pie si aplica.

## Barra de acciones o filtros

### Contenedor sugerido
`HBox`

### Uso típico
- buscador;
- `ComboBox`;
- `DatePicker`;
- botones buscar / limpiar / crear;
- filtros adicionales.

### Espaciado entre elementos
`10px` a `14px`

### Altura de controles
Entre `34px` y `40px`.

## Formularios

### Formularios simples
`VBox`

### Formularios con dos columnas o más alineación semántica
`GridPane`

### Padding interno sugerido del formulario
`16px` a `20px`

### Espaciado vertical entre campos
`10px` a `14px`

### Espaciado horizontal entre columnas
`14px` a `20px`

## Resultado esperado

El layout espacial y shell del desktop deben permitir una implementación JavaFX ordenada, operativa y visualmente estable, con reglas espaciales suficientes para que el crecimiento futuro del frontend no se vuelva caótico.
