# Estructura de paquetes

## Propósito

Definir una organización de paquetes clara y suficientemente explícita para `desktop-consultorio-javafx`, de forma que la implementación JavaFX no se desordene a medida que crece en pantallas, componentes, módulos y llamadas al backend.

## Principio general

La estructura de paquetes debe ayudarte a encontrar rápido:

- el shell;
- la pantalla;
- el componente reutilizable;
- la integración con backend;
- el estado compartido;
- los recursos y estilos.

No debe ser tan plana que todo quede mezclado ni tan rara que solo se entienda después de mucho tiempo.

## Estrategia recomendada

Se recomienda una estructura **por áreas funcionales**, con una separación clara entre:

- aplicación;
- shell;
- módulos de pantalla;
- componentes comunes;
- integración remota;
- sesión y estado;
- estilos y assets.

## Paquete base sugerido

Ejemplo conceptual:

`com.tuorganizacion.santaemilia.desktop`

o equivalente según el nombre real del proyecto.

## Zonas principales sugeridas

### `app`
Punto de entrada general de la aplicación JavaFX.

### `shell`
Clases responsables del `BorderPane` principal, `MenuBar`, sidebar y barra superior.

### `modules`
Pantallas o módulos funcionales del sistema.

### `components`
Componentes UI reutilizables.

### `navigation`
Lógica de navegación, módulo activo y cambio de vistas.

### `session`
Estado de sesión, usuario autenticado, token y contexto visible.

### `api`
Clientes HTTP, DTOs remotos o adaptadores de integración con backend.

### `styles`
Recursos de estilo, temas, tokens o utilidades visuales.

### `assets`
Referencias a imágenes, logos, íconos y recursos visuales.

### `dialogs`
Modales o diálogos comunes, si decides separarlos de `components`.

## Estructura funcional sugerida

## `shell`
Podría incluir:
- `MainShellView`
- `TopBarView`
- `SidebarView`
- `MenuBarFactory` o equivalente si se justifica

## `modules.dashboard`
Podría incluir:
- vista principal del dashboard;
- componentes KPI;
- bloques analíticos;
- lógica de refresh del panel inicial.

## `modules.pacientes`
Podría incluir:
- vista de listado;
- modal de creación/edición;
- filtros;
- tabla;
- acciones de detalle.

## `modules.profesionales`
Podría incluir:
- listado;
- formulario modal;
- detalle resumido si aplica.

## `modules.citas`
Podría incluir:
- agenda;
- filtros por profesional y fecha;
- creación y cancelación;
- visualización tabular.

## `modules.atenciones`
Podría incluir:
- registro de atención;
- listados;
- formularios y detalle resumido.

## `modules.cobros`
Podría incluir:
- listado;
- registro;
- filtros por estado y fecha.

## `modules.reportes`
Podría incluir:
- solicitud de reporte;
- historial de reportes;
- descarga de archivos;
- estados de generación.

## `components`
Piezas reutilizables como:
- botones estilizados;
- cards;
- panel de filtros;
- encabezado de módulo;
- tabla encapsulada o helpers de tabla;
- badges de estado;
- empty states;
- loaders.

## `api`
Podría incluir:
- cliente base HTTP;
- servicios remotos por módulo;
- DTOs de request/response consumidos desde backend;
- manejo centralizado de errores remotos.

## `session`
Podría incluir:
- `SessionContext`
- `CurrentUserState`
- `AuthState`

o equivalentes con nombres claros.

## `navigation`
Podría incluir:
- rutas internas de módulo;
- gestor de vista activa;
- selección del sidebar;
- helpers de recarga o cambio de módulo.

## Criterios importantes

### 1. El shell no debe mezclarse con cada módulo
La estructura fija de la app debe vivir separada de las pantallas operativas.

### 2. Los módulos no deben compartir código por copiar y pegar
Lo repetible debe pasar a `components` o utilidades bien ubicadas.

### 3. La integración con backend debe ser visible
No conviene esconder llamadas HTTP por todo el proyecto sin capa reconocible.

### 4. La sesión debe tener lugar propio
No debe quedar distribuida de forma caótica entre controladores de pantalla.

## Qué evitar

- poner todas las pantallas en una misma carpeta plana;
- mezclar assets, estilos, lógica y vistas sin frontera;
- componentes reutilizables dispersos dentro de módulos concretos cuando ya son compartidos;
- acoplar el shell a un módulo específico.

## Resultado esperado

La estructura de paquetes del desktop debe hacer que la aplicación JavaFX se vea como un proyecto ordenado, mantenible y estudiable, donde el shell, los módulos, la integración con backend y los componentes reutilizables puedan ubicarse rápido y sin ambigüedad.