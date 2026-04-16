# Testing desktop

## Propósito

Definir qué debe probarse en `desktop-consultorio-javafx`, con qué criterio y con qué nivel de profundidad razonable para una V1 seria, operativa y orientada a estudio.

## Principio general

El testing del desktop no debe ser decorativo ni limitarse a comprobar que “la ventana abre”. Debe ayudar a verificar que el cliente realmente sostiene:

- navegación consistente;
- formularios usables;
- integración razonable con backend;
- estado de sesión;
- manejo correcto de errores y loading;
- coherencia de componentes reutilizables.

## Qué se busca con el testing en esta V1

### 1. Proteger flujos esenciales
Especialmente login, navegación, listados, modales y solicitud de reportes.

### 2. Evitar regresiones visuales y funcionales obvias
Que un cambio en el shell no rompa módulos, filtros o modales.

### 3. Dar confianza a la arquitectura UI
Que shell, módulos y componentes reutilizables colaboren como fueron diseñados.

### 4. Servir como estudio
Que el proyecto también enseñe qué se prueba y por qué se prueba en un cliente JavaFX serio.

## Niveles de prueba recomendados

## 1. Unit tests

### Objetivo
Probar piezas aisladas que realmente contengan lógica útil.

### Cuándo sí aporta
- transformaciones de estado UI;
- utilidades de navegación;
- validaciones locales no triviales;
- adaptadores de respuesta del backend;
- construcción de filtros o criterios visuales.

### Cuándo no aporta tanto
No hace falta llenar el proyecto de tests triviales sobre setters, getters o nodos visuales obvios sin comportamiento.

## 2. Integration tests

### Objetivo
Verificar que varias piezas colaboren bien entre sí.

### Ejemplos importantes
- shell + navegación;
- login + estado de sesión;
- módulo + tabla + filtros;
- formulario modal + validación + feedback;
- solicitud de reporte + historial visible.

## 3. UI / interaction tests

### Objetivo
Comprobar que la interacción visible del usuario se comporte como se espera.

### Ejemplos importantes
- abrir login;
- autenticarse;
- cambiar de módulo;
- abrir modal;
- completar formulario;
- cerrar modal con `Esc` cuando aplique;
- usar paginación o búsqueda.

## Áreas que conviene probar sí o sí

## Shell y navegación
- render del shell autenticado;
- selección correcta del módulo activo;
- persistencia visual del sidebar y barra superior;
- recarga de vista actual si se implementa.

## Login
- login exitoso;
- login fallido;
- mensaje correcto de error;
- transición del login al shell.

## Listados
- filtros visibles;
- búsqueda funcional;
- carga de tabla;
- estado vacío;
- acciones por fila visibles.

## Modales
- apertura;
- cierre;
- validación de formulario;
- confirmación de guardado o cancelación.

## Reportes
- solicitud correcta;
- historial visible;
- estados legibles;
- descarga o acción disponible cuando el backend lo permita.

## Error y conectividad
- error de backend visible de forma clara;
- error de red tratado sin romper toda la app;
- token expirado o sesión inválida.

## Accesibilidad operativa
- foco visible;
- navegación básica por teclado;
- `F11` y `Esc` donde aplique;
- `Ctrl + F` o atajos relevantes si se implementan.

## Qué no debe pasar

- pruebas que dependen del orden casual de ejecución;
- solo pruebas sobre la apertura de la ventana y nada más;
- cero pruebas de navegación;
- cero pruebas de formularios;
- cero pruebas de sesión y errores.

## Resultado esperado

La estrategia de testing del desktop debe dejar una aplicación JavaFX razonablemente confiable, donde los flujos principales y la interacción visible estén cubiertos lo suficiente como para sostener evolución sin degradar la experiencia.