# Patrones de tablas, modales y dashboards

## Propósito

Definir los tres grandes patrones visuales y operativos del desktop del consultorio: tablas, modales y dashboard. Estos patrones son el corazón de la aplicación y deben quedar muy bien amarrados para que la implementación JavaFX conserve coherencia.

## Principio general

La app de escritorio del consultorio no vive de pantallas decorativas. Vive de:
- tablas para operar datos;
- modales para crear, editar o confirmar;
- dashboard para resumir y orientar.

Si estos tres patrones están bien diseñados, el resto del frontend se vuelve mucho más consistente.

## Reglas obligatorias por patrón

### Tablas
- toolbar arriba;
- tabla como zona dominante;
- acciones por fila compactas;
- badge de estado visible;
- paginación y resumen al pie si aplica.

No meter botones grandes repetidos dentro de cada fila.

### Modales
- tamaño contenido;
- una tarea dominante;
- header claro;
- cuerpo con padding suficiente;
- footer con jerarquía clara entre acción principal, secundaria y cancelación.

No usar modales gigantes para tareas pequeñas.

### Dashboards
- una métrica dominante;
- dos a cuatro KPIs secundarios;
- gráfico solo si realmente aporta lectura operativa;
- evitar dashboards que parezcan tablero decorativo.

## Patrón 1. Tablas

## Función
La tabla es el núcleo operativo principal del sistema.

### Debe servir para
- leer información densa;
- buscar;
- filtrar;
- ver estados;
- ejecutar acciones por fila;
- paginar resultados.

## Componente JavaFX principal
- `TableView`
- `TableColumn`

## Estructura espacial recomendada

### Encima de la tabla
- título del módulo;
- subtítulo contextual;
- barra de filtros;
- botón principal de crear o acción relevante;
- resumen breve de cantidad de resultados.

### Contenedor de tabla
- panel claro;
- borde suave;
- padding `12px` a `16px`;
- header de tabla más oscuro;
- cuerpo claro y legible.

### Debajo de la tabla
- paginación;
- acciones secundarias si aplican;
- feedback de página actual.

## Columnas recomendadas

Las tablas deben priorizar:
- columnas con nombres claros;
- estados visibles;
- acciones agrupadas al extremo derecho;
- jerarquía razonable entre datos importantes y datos secundarios.

## Acciones por fila

Las acciones por fila deben mantenerse compactas y predecibles.

### Ejemplos
- Ver
- Editar
- Activar
- Inactivar
- Descargar
- Consultar estado

### Regla visual
No hacer botones gigantes dentro de cada fila. Deben ser pequeños, claros y consistentes.

## Estados dentro de tabla

Usar `StatusBadge` o equivalente para:
- ACTIVO;
- INACTIVO;
- PENDIENTE;
- COMPLETADA;
- ERROR según el módulo.

## Qué evitar en tablas

- demasiados colores por fila;
- celdas visualmente ruidosas;
- acciones desordenadas;
- tablas sin filtros en módulos que claramente lo necesitan;
- headers sin contraste suficiente.

## Patrón 2. Modales

## Función
Los modales son el patrón preferido para:
- crear;
- editar;
- confirmar;
- mostrar detalle controlado.

## Contenedor JavaFX recomendado
- `StackPane` para overlay y centrado
- `VBox` o `BorderPane` para el contenido del modal

## Estructura del modal

### Header
- título claro;
- botón cerrar;
- fondo distintivo pero sobrio.

### Body
- formulario o contenido principal;
- organizado con `VBox` o `GridPane`;
- campos con espaciado uniforme.

### Footer
- acciones;
- botón primario y secundario;
- separación clara entre guardar y cerrar o cancelar.

## Tamaño recomendado

### Pequeño
Para confirmaciones simples.

### Mediano
Para formularios comunes.

### Grande
Solo si el flujo lo exige realmente.

## Overlay
Debe oscurecer o suavizar el fondo sin ocultarlo completamente.

## Qué evitar en modales

- modales demasiado pequeños para formularios largos;
- modales gigantes para tareas simples;
- cerrar sin confirmación cuando se perderán cambios importantes;
- mezclar demasiadas responsabilidades en un solo modal.

## Patrón 3. Dashboard

## Función
El dashboard es la pantalla de entrada del usuario autenticado. Debe resumir y orientar, no reemplazar la operación detallada de los módulos.

## Estructura recomendada

### Parte superior
- título de bienvenida;
- subtítulo contextual;
- indicador breve de carga o pulso si aplica.

### Zona protagonista
- panel hero o tarjeta principal con métrica dominante;
- breve lectura contextual del estado general.

### KPIs secundarios
- tarjetas pequeñas o medianas con ratios o conteos útiles.

### Panel lateral
- gráfico o distribución resumida;
- leyenda legible;
- resumen operativo complementario.

## Qué debe mostrar el dashboard

Solo información que realmente ayude a orientarse.

### Ejemplos
- cantidad de registros activos;
- distribución general;
- volumen de módulos clave;
- resumen operativo del backend.

## Qué evitar en dashboard

- demasiados widgets;
- información inútil para trabajo real;
- visualizaciones chillones;
- gráficos puestos solo por “verse modernos”.

## Relación entre los tres patrones

### Dashboard
Resume.

### Tabla
Opera.

### Modal
Actúa sobre el dato.

Esa triada debe ser clarísima en todo el sistema.

## Resultado esperado

Estos tres patrones deben convertirse en la gramática visual principal de `desktop-consultorio-javafx`. Si se respetan bien, la aplicación se verá coherente, madura y claramente operativa, justo como un software de escritorio serio.
