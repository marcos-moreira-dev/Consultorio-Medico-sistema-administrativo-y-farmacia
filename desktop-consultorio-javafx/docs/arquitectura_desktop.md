# Arquitectura desktop

## Propósito

Definir la arquitectura interna oficial de `desktop-consultorio-javafx`, de modo que el cliente de escritorio se implemente con una estructura clara, coherente con JavaFX y suficientemente robusta para crecer sin degradarse en desorden visual y técnico.

## Estilo arquitectónico adoptado

El cliente de escritorio del consultorio se implementará como una aplicación **modular**, con separación clara entre:

- shell principal;
- navegación;
- pantallas;
- componentes reutilizables;
- integración con backend;
- estado de sesión;
- manejo de errores y feedback visual.

## Principio rector

La arquitectura debe evitar, o al menos dificultar mucho, estos errores:

- lógica de negocio metida en controladores de pantalla;
- mezcla confusa entre layout, networking y estado;
- repetición caótica de componentes visuales;
- pantallas construidas como escenas aisladas sin shell estable;
- acoplamiento fuerte entre UI y respuestas crudas del backend.

## Capas o zonas de responsabilidad recomendadas

## 1. Capa de aplicación UI

Coordina lo que la app muestra y cómo navega.

### Responsabilidades
- cambio de pantallas o vistas;
- apertura y cierre de modales;
- composición del shell;
- manejo del estado visible de la sesión;
- orquestación de acciones del usuario.

## 2. Capa de presentación

Representa visualmente el sistema mediante vistas, contenedores y componentes JavaFX.

### Responsabilidades
- composición de layout;
- renderizado de tablas, formularios y cards;
- aplicación del sistema visual;
- interacción inmediata del usuario.

## 3. Capa de integración con backend

Se encarga de consumir `backend-consultorio`.

### Responsabilidades
- llamadas HTTP;
- serialización y deserialización de DTOs;
- manejo técnico de errores remotos;
- adaptación de responses para la UI.

## 4. Capa de estado y sesión

Se encarga de sostener información compartida de la aplicación.

### Responsabilidades
- token y contexto de sesión;
- usuario activo;
- rol activo;
- módulo actual;
- datos de estado transversal si realmente se justifican.

## 5. Capa de componentes reutilizables

Provee piezas visuales y patrones UI que pueden repetirse en varias pantallas.

### Responsabilidades
- botones estilizados;
- cards;
- paneles de filtro;
- headers de módulo;
- diálogos comunes;
- mensajes de error o empty states.

## Shell estable como eje

La arquitectura debe asumir que el sistema vive sobre un shell principal estable.

Ese shell debe contener:
- `MenuBar`;
- barra superior contextual;
- sidebar persistente;
- zona central dinámica.

Las pantallas operativas viven dentro de ese shell y no deben reconstruir toda la app desde cero.

## Módulos funcionales esperados desde UI

El cliente debe poder representar, al menos, estos módulos del dominio del consultorio:

- dashboard
- pacientes
- profesionales
- citas
- atenciones
- cobros
- reportes
- auditoría o equivalentes si aplica en la evolución del sistema

## Patrones UI clave

### Shell + módulo activo
El usuario entra al sistema y queda dentro de una estructura estable.

### Tabla + filtros + acciones
Patrón dominante para módulos operativos.

### Modal para crear/editar
Los flujos de alta o edición pueden resolverse con modales bien diseñados.

### Dashboard de resumen
Debe existir una pantalla inicial con KPIs y visualización controlada.

## Relación con JavaFX

La arquitectura debe sentirse natural dentro de JavaFX:

- `Stage` y `Scene` como base de la app;
- `BorderPane` como shell principal;
- `VBox`, `HBox`, `GridPane`, `StackPane` y `ScrollPane` como contenedores principales;
- `TableView` como núcleo de módulos tabulares;
- modales sobre `StackPane` o diálogo equivalente;
- control estricto del layout y la jerarquía visual.

## Qué no se busca en esta V1

- frontend visualmente experimental;
- arquitectura rebuscada solo por “patrones”; 
- pantallas aisladas sin lenguaje común;
- dependencia excesiva de branding o imágenes;
- una UI cargada de animaciones innecesarias.

## Resultado esperado

La arquitectura desktop del consultorio debe dejar una base clásica, modular y muy explícita, donde la app se entienda como una estación de trabajo de escritorio seria, con shell estable, integración clara con backend y componentes visuales reutilizables.