# Accesibilidad y usabilidad

## Propósito

Definir criterios de accesibilidad y usabilidad para `desktop-consultorio-javafx`, de modo que la aplicación sea cómoda de usar, legible y razonable para jornadas prolongadas de trabajo administrativo.

## Principio general

El desktop del consultorio no debe buscar espectacularidad visual. Debe priorizar que el usuario pueda:

- leer rápido;
- entender dónde está;
- completar tareas sin fricción innecesaria;
- usar el sistema durante largo tiempo sin fatiga excesiva.

## Usabilidad general esperada

La interfaz debe ser:

- clara;
- consistente;
- de navegación predecible;
- con jerarquía visual evidente;
- con estados reconocibles;
- con acciones principales fáciles de localizar.

## Accesibilidad visual

### Contraste
Debe mantenerse contraste suficiente entre:
- fondo y texto;
- botones y su etiqueta;
- estados y superficies.

### Tipografía
Debe ser legible en:
- tablas;
- formularios;
- títulos;
- badges;
- modales.

### Tamaño visual
No conviene usar textos diminutos solo por densidad de información. La densidad debe resolverse con layout, no con sacrificar lectura.

### Color como apoyo, no como único canal
Un estado no debe depender únicamente del color para entenderse. Conviene combinar:
- color;
- texto;
- badge;
- posición o icono si hace falta.

## Accesibilidad operativa

### Foco visible
El usuario debe poder reconocer qué control está activo al navegar con teclado.

### Orden de tabulación
Debe ser lógico y coherente en:
- login;
- formularios;
- filtros;
- modales.

### Atajos razonables
Los atajos deben ayudar, no confundir.

### Escape y confirmación
`Esc` debe comportarse de forma consistente en modales o fullscreen cuando aplique.

## Usabilidad del shell

### Shell fijo
Ayuda a reducir carga cognitiva porque el usuario siempre sabe dónde están:
- navegación;
- sesión;
- menú;
- módulo activo.

### Sidebar
Debe tener opciones claras, sin saturarse de niveles raros.

### Menú superior
Debe complementar, no reemplazar ni duplicar absurdamente el sidebar.

## Usabilidad de listados

### Tablas
Deben permitir:
- escaneo rápido;
- acciones localizables;
- estados legibles;
- filtros cercanos.

### Filtros
Deben ser compactos y útiles.

### Paginación
Debe ser visible y entendible, sin esconder al usuario dónde está dentro de los resultados.

## Usabilidad de formularios

### Agrupación
Campos relacionados deben vivir juntos.

### Feedback
Los errores deben aparecer cerca del lugar correcto.

### Confirmación
Guardar, cancelar y cerrar deben ser fáciles de distinguir.

## Usabilidad de modales

### Contexto
El modal no debe hacer perder por completo el contexto del módulo activo.

### Tamaño
Debe ser proporcionado a la tarea.

### Acción principal
Debe quedar clarísima.

## Usabilidad del dashboard

### Función real
Debe orientar, no entretener.

### Claridad
Las métricas deben leerse rápido y no competir entre sí.

### Gráficos
Solo si ayudan a entender mejor, no por decoración.

## Estados vacíos y errores

### Estado vacío
Debe explicar que no hay resultados y sugerir siguiente paso si tiene sentido.

### Error
Debe ser claro y sobrio.

### Loading
Debe indicar espera sin bloquear visualmente más de lo necesario.

## Qué no debe pasar

- interfaz demasiado oscura o demasiado clara sin contraste equilibrado;
- textos pequeños por meter demasiada información;
- focus invisible al navegar con teclado;
- modales donde no se entiende cómo salir;
- tablas con acciones mínimas imposibles de localizar;
- error y vacío representados casi igual.

## Resultado esperado

La accesibilidad y usabilidad del desktop deben hacer que el producto se sienta cómodo, claro y controlable, incluso para sesiones largas y tareas repetitivas, que al final son el contexto real del software administrativo.