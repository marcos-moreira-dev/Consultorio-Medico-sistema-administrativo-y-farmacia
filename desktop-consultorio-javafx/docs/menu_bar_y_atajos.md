# Menu bar y atajos

## Propósito

Definir la barra de menú superior de `desktop-consultorio-javafx`, sus secciones típicas, acciones recomendadas y atajos de teclado razonables, para que el cliente se sienta como una aplicación de escritorio real y no como una simple vista sin controles globales.

## Principio general

El desktop del consultorio debe tener una `MenuBar` clásica en la parte superior. No es necesario cargarla de opciones innecesarias, pero sí debe aportar:

- identidad de aplicación de escritorio;
- acceso a acciones globales;
- descubribilidad de funciones útiles;
- atajos razonables para flujo operativo.

## Contenedor JavaFX

### Componente principal
`MenuBar`

### Ubicación
Parte superior del shell, dentro de la zona `top` del `BorderPane` raíz, por encima de la barra superior contextual.

## Menús recomendados

## Menú `Archivo`

### Objetivo
Agrupar acciones de sesión y salida de la aplicación.

### `MenuItem` sugeridos
- `Cerrar sesión`
- `Salir`

### Comportamiento esperado
- `Cerrar sesión` vuelve al login sin cerrar la aplicación.
- `Salir` cierra la aplicación de escritorio.

## Menú `Ver`

### Objetivo
Controlar presentación general de la ventana.

### `MenuItem` sugeridos
- `Pantalla completa`
- `Restaurar tamaño normal`
- `Recargar vista actual`

### Comportamiento esperado
- `Pantalla completa` activa fullscreen.
- `Restaurar tamaño normal` devuelve la ventana a modo no fullscreen.
- `Recargar vista actual` fuerza recarga de datos del módulo visible cuando tenga sentido.

## Menú `Herramientas`

### Objetivo
Agrupar acciones técnicas o de soporte de operación.

### `MenuItem` sugeridos
- `Ver logs`
- `Diagnóstico de conexión`
- `Preferencias` o `Configuración` si luego se justifica

### Comportamiento esperado
- `Ver logs` abre un visor o panel de logs si existe.
- `Diagnóstico de conexión` muestra estado básico de conectividad con backend.

## Menú `Ayuda`

### Objetivo
Agrupar ayuda contextual y datos del producto.

### `MenuItem` sugeridos
- `Atajos de teclado`
- `Acerca de Santa Emilia Desktop`

### Comportamiento esperado
- `Atajos de teclado` abre un pequeño diálogo de ayuda.
- `Acerca de...` abre un modal con nombre del producto, versión y referencia del consultorio.

## Menú adicional opcional `Ventana`

Solo si luego ves valor real, podría incluir:
- `Minimizar`
- `Traer al frente`
- otras acciones de ventana

No es obligatorio para V1.

## Atajos de teclado recomendados

## Atajos globales

### `F11`
Alternar pantalla completa.

### `Esc`
Cerrar modal activo o salir de pantalla completa cuando aplique.

### `Ctrl + R`
Recargar la vista actual o refrescar datos del módulo activo.

### `Ctrl + F`
Enfocar el buscador principal del módulo visible si existe.

### `Ctrl + L`
Abrir o enfocar visor de logs si esa utilidad está disponible.

### `Ctrl + Q`
Salir de la aplicación, si decides permitirlo como shortcut.

## Atajos contextuales opcionales

### `Ctrl + N`
Crear nuevo registro en módulos que lo soporten.

### `Ctrl + E`
Editar registro seleccionado, si el patrón lo justifica.

### `Enter`
Confirmar acción principal en formularios o login cuando tenga sentido.

## Criterios de uso de atajos

### 1. No saturar
No hace falta inventar veinte atajos si el beneficio real es bajo.

### 2. Coherencia
Un atajo global debe comportarse igual en toda la app cuando sea aplicable.

### 3. Descubribilidad
Los atajos deberían poder verse al menos en el menú o en el diálogo de ayuda.

### 4. Respeto al contexto
No disparar acciones destructivas con atajos ambiguos sin confirmación suficiente.

## Relación con la experiencia clásica de escritorio

La `MenuBar` y los atajos ayudan a que el producto se sienta como software de escritorio tradicional, reforzando:

- familiaridad;
- control;
- eficiencia;
- sensación de producto maduro.

## Qué no debe pasar

- una barra de menú vacía o decorativa sin valor real;
- opciones que duplican toda la navegación lateral sin criterio;
- atajos inconsistentes entre módulos;
- acciones delicadas sin confirmación cuando corresponda.

## Resultado esperado

La barra de menú y los atajos del desktop deben aportar estructura clásica, acceso rápido a funciones globales y una sensación real de aplicación de escritorio bien pensada, sin sobrecargar la experiencia con menús innecesarios.