# Componentes reutilizables

## Propósito

Definir las piezas visuales y funcionales que deben reutilizarse a lo largo de `desktop-consultorio-javafx`, para evitar duplicación caótica de UI y mantener una experiencia consistente en todos los módulos.

## Principio general

La aplicación no debe construirse como una colección de pantallas aisladas donde cada una reinventa:

- botones;
- headers;
- paneles de filtros;
- cards;
- modales;
- tablas;
- badges de estado.

Lo repetible debe convertirse en componente reutilizable.

## Qué se entiende aquí por componente reutilizable

Un componente reutilizable es una pieza de UI con responsabilidad clara, apariencia consistente y comportamiento repetible en varias pantallas.

No necesita ser un framework entero por sí mismo. Debe ser:

- visible;
- reconocible;
- útil;
- composable dentro de JavaFX.

## Familias de componentes recomendadas

## 1. Componentes estructurales

### `ShellTopBar`
Barra superior contextual con logo, nombre del sistema y estado de sesión.

### `SidebarNavigation`
Navegación lateral persistente con selección clara del módulo activo.

### `ModuleHeader`
Encabezado reutilizable de módulo con:
- título;
- subtítulo;
- acciones primarias opcionales.

## 2. Componentes de acción

### `PrimaryButton`
Botón principal con estilo institucional en concho de vino.

### `SecondaryButton`
Botón secundario sobrio para acciones auxiliares.

### `DangerButton`
Botón para acciones delicadas o destructivas.

### `GhostButton` o equivalente
Botón visualmente ligero para acciones menos prioritarias.

## 3. Componentes de formularios

### `LabeledTextField`
Campo con etiqueta clara y estilo uniforme.

### `LabeledPasswordField`
Versión para login u operaciones sensibles.

### `LabeledComboBox`
Selector estándar con espaciado y altura consistentes.

### `LabeledDatePicker`
Selector de fecha homogéneo para filtros y formularios.

### `FormSectionCard`
Contenedor reutilizable para grupos de campos relacionados.

## 4. Componentes de filtros y búsqueda

### `SearchBar`
Barra de búsqueda estándar para módulos de listado.

### `FilterPanel`
Panel horizontal o vertical con controles de filtro repetibles.

### `FilterActionBar`
Zona de botones como buscar, limpiar y crear.

## 5. Componentes tabulares

### `TableContainerCard`
Contenedor visual estándar de tablas.

### `PaginationBar`
Componente para paginación y navegación de resultados.

### `TableActionCell`
Celda reutilizable con botones como ver, editar, activar, inactivar o descargar.

### `StatusBadge`
Badge de estado reutilizable para ACTIVO, INACTIVO, PENDIENTE, COMPLETADA, etc.

## 6. Componentes de dashboard

### `KpiCard`
Tarjeta pequeña o mediana para KPI.

### `HeroMetricPanel`
Panel protagonista de resumen con métrica fuerte y texto contextual.

### `ChartCard`
Contenedor de gráfico sobrio y consistente.

### `SummaryLegend`
Bloque de apoyo para leer distribución o mezcla operativa.

## 7. Componentes modales y feedback

### `ModalShell`
Estructura base de modal con header, body y footer.

### `ConfirmDialog`
Diálogo de confirmación para acciones sensibles.

### `InfoDialog`
Diálogo simple de información.

### `ErrorPanel`
Panel reutilizable para mostrar error contextual.

### `EmptyStatePanel`
Panel de estado vacío con texto e imagen opcional.

### `LoadingOverlay`
Capa visual para procesos de carga o espera.

## Componentes recomendados por patrón de uso

## Login
- `PrimaryButton`
- `LabeledTextField`
- `LabeledPasswordField`
- panel hero del login

## Listados
- `ModuleHeader`
- `SearchBar`
- `FilterPanel`
- `FilterActionBar`
- `TableContainerCard`
- `PaginationBar`
- `TableActionCell`
- `StatusBadge`

## Formularios en modal
- `ModalShell`
- `FormSectionCard`
- `PrimaryButton`
- `SecondaryButton`
- `ErrorPanel`

## Dashboard
- `ModuleHeader`
- `HeroMetricPanel`
- `KpiCard`
- `ChartCard`
- `SummaryLegend`

## Criterios de diseño de componentes

### 1. Consistencia visual
Un botón principal debe parecer el mismo en todo el sistema.

### 2. Responsabilidad clara
Cada componente debe resolver una pieza concreta, no una pantalla entera escondida dentro de otra.

### 3. Composición natural en JavaFX
Los componentes deben ser fáciles de montar con `VBox`, `HBox`, `GridPane` y `StackPane`.

### 4. Adaptación a módulos densos
La UI del consultorio es operativa y rica en datos; los componentes deben favorecer esa densidad sin volverse pesados o caóticos.

## Qué no debe pasar

- cada pantalla con su propio estilo de botón o filtro;
- modales reinventados una y otra vez;
- tablas sin patrón común;
- dashboards armados como collages inconexos;
- componentes tan abstractos que nadie entienda para qué sirven.

## Resultado esperado

La biblioteca de componentes reutilizables del desktop debe permitir construir el sistema entero con lenguaje visual coherente, reduciendo duplicación y haciendo que cada módulo se sienta parte del mismo producto.