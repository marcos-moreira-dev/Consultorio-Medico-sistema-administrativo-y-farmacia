# Convenciones UI

## Propósito

Definir reglas de consistencia visual, espacial y de interacción para `storefront-farmacia-angular`, de forma que la interfaz se mantenga homogénea incluso cuando crezca en páginas, componentes y secciones del catálogo.

## Principio general

Las convenciones UI existen para reducir fricción, evitar arbitrariedad y hacer que el storefront se sienta como un único producto coherente. No deben verse como decoración documental, sino como reglas prácticas de diseño e implementación.

## Convenciones de color

### Regla base
- verdes farmacéuticos y blanco dominan;
- fondos muy claros aportan limpieza;
- turquesa de apoyo aparece con moderación;
- acentos cálidos solo en puntos puntuales.

### Regla de prioridad
No introducir colores nuevos por bloque o página si no tienen razón de sistema.

## Convenciones de tipografía

### Títulos
Deben ser claros, de alto peso visual y fáciles de escanear.

### Subtítulos
Deben contextualizar sin competir con el título.

### Nombre de producto
Debe tener jerarquía visible dentro de la card y del detalle.

### Texto secundario
Debe bajar contraste, no desaparecer.

## Convenciones de layout

### Layout público
Siempre estable:
- header;
- contenido principal;
- footer.

### Home
Debe priorizar:
- hero;
- búsqueda o acceso rápido;
- secciones bien espaciadas;
- productos destacados o visibles.

### Catálogo
Debe priorizar:
- barra de filtros;
- grid respirado;
- paginación clara.

### Detalle
Debe priorizar:
- imagen;
- nombre;
- disponibilidad;
- CTA;
- descripción útil.

### Márgenes y spacing
Se adopta la misma escala oficial del proyecto:

`4, 8, 12, 16, 24, 32, 40, 48`

Uso recomendado:
- `8px`: separación pequeña entre controles;
- `12px` o `16px`: gap interno de formularios, toolbar y bloques cortos;
- `16px`: padding interno de card estándar;
- `24px`: padding de secciones o bloques principales;
- `24px` o `32px`: separación entre secciones grandes.

### Niveles de contenedor
No usar más de dos niveles de contenedor fuerte.
Evitar card dentro de card dentro de card.

### Radios
- radios preferidos: `10px`, `12px`, `14px`;
- no usar radios inconsistentes en botones, cards y filtros.

## Convenciones de botones y CTA

### CTA principal
- verde farmacéutico principal;
- texto directo;
- usado para acción dominante del bloque.

### CTA secundario
- estilo más ligero;
- no debe competir visualmente con el CTA principal.

### Regla de prioridad
No colocar demasiados botones primarios en el mismo bloque.

### CTA
- una acción principal clara por bloque;
- no usar múltiples botones dominantes dentro de una misma card.

## Convenciones de cards

- bordes suaves;
- sombra ligera;
- suficiente aire interno;
- imagen protagonista arriba;
- contenido textual claro;
- CTA visible pero no agresivo.

## Convenciones de badges

- compactos;
- legibles;
- apoyados por texto, no solo color;
- usar disponibilidad, promoción o estado de forma muy controlada.

## Convenciones de hero

- headline claro;
- subtítulo breve;
- visual limpio;
- CTA principal visible;
- no usar demasiado texto;
- no convertirlo en un cartel recargado.

## Convenciones de formularios y búsqueda

- inputs claros;
- radios suaves;
- foco visible;
- labels o placeholders útiles;
- búsqueda fácil de encontrar;
- filtros compactos y comprensibles.

## Convenciones de estados visuales

### Loading
Usar skeletons o loaders suaves.

### Error
Mensaje claro y sobrio, no alarmista.

### Vacío
Debe explicar que no hay resultados y sugerir siguiente paso cuando tenga sentido.

### Disponible
Verde claro y legible.

### No disponible
Estado distinguible pero no exageradamente dramático.

## Convenciones de branding

- logo visible pero controlado;
- imágenes grandes solo en hero o banners bien justificados;
- no llenar el sitio de elementos de marca por todas partes;
- dejar que producto, composición y color hagan gran parte del trabajo visual.

## Qué no debe pasar

- hero con demasiados mensajes compitiendo;
- cards de producto todas diferentes entre sí;
- botones con tamaños y radios aleatorios;
- grid apretado como marketplace caótico;
- saturación de promociones, badges y stickers.

## Resultado esperado

Las convenciones UI del storefront deben hacer que la farmacia web se sienta limpia, comercialmente clara y coherente, reduciendo la improvisación de la IA y facilitando que todo el sitio mantenga una sola voz visual.
