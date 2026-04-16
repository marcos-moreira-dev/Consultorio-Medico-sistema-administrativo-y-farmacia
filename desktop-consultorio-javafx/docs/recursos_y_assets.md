# Recursos y assets

## Propósito

Definir qué recursos visuales y técnicos necesita `desktop-consultorio-javafx`, cómo deben clasificarse y qué papel cumplen dentro de la experiencia general del producto.

## Principio general

El desktop del consultorio no debe depender de decenas de assets dispersos ni de imágenes decorativas innecesarias. Debe apoyarse en pocos recursos, bien elegidos y claramente clasificados.

## Tipos de assets esperados

## 1. Logo principal del producto

### Uso
- login;
- barra superior;
- diálogo “Acerca de”.

### Requisito
Debe existir en versión limpia y adaptable a superficies claras u oscuras si hace falta.

## 2. Logo reducido o icono

### Uso
- parte superior del shell;
- ícono de aplicación si luego se adapta.

### Requisito
Debe seguir siendo legible a tamaño pequeño.

## 3. Visual institucional del login

### Uso
Panel hero o fondo parcial del login.

### Función
Aportar identidad sin competir con el formulario.

## 4. Ilustraciones de estado vacío

### Uso
Pantallas sin resultados o módulos sin registros.

### Función
Suavizar el vacío visual sin volverlo infantil.

## 5. Íconos o pictogramas de apoyo

### Uso
- sidebar;
- dashboard;
- badges o resúmenes;
- ayudas contextuales si se justifican.

### Requisito
Deben ser sobrios y consistentes con el tono institucional.

## 6. Recursos de estilo

### Incluye
- hojas de estilo;
- tokens visuales;
- definiciones de color;
- tipografías si se usan de forma controlada.

## Clasificación recomendada de assets

### Branding
- logo principal;
- logo reducido;
- nombre del software.

### Institucional
- imagen de login;
- empty states;
- diálogo “Acerca de”.

### Operativo
- íconos de módulos;
- iconos de estado;
- indicadores visuales mínimos.

### Estilo
- CSS o archivos equivalentes;
- definiciones visuales compartidas.

## Reglas de uso

### Branding mínimo
El desktop no debe saturarse de branding. El logo debe existir, pero no invadir cada panel.

### Reutilización controlada
Los mismos assets deben poder reutilizarse sin romper coherencia.

### Neutralidad operativa
La mayoría de módulos operativos deben apoyarse más en layout, tipografía y color que en imágenes grandes.

## Formatos recomendados

### Logo e ilustración ligera
Preferiblemente en formatos limpios y adaptables, según el pipeline que luego uses.

### Íconos
Formatos consistentes y fáciles de integrar visualmente.

### Imágenes grandes
Solo cuando realmente aporten, como en login o empty states institucionales.

## Qué no debe pasar

- assets puestos por decorar;
- branding enorme en cada módulo;
- imágenes pesadas sin función clara;
- mezcla de estilos incompatibles entre logo, íconos e ilustraciones;
- recursos sin una carpeta o clasificación reconocible.

## Relación con prompts visuales

Los recursos deben apoyarse en los prompts ya fijados en `prompts_visuales_y_branding.md`, para reducir improvisación al generarlos con IA.

## Resultado esperado

El sistema de recursos y assets del desktop debe quedar claro, contenido y funcional: pocos elementos visuales, bien dirigidos y coherentes con una app de escritorio institucional, clásica y sobria.