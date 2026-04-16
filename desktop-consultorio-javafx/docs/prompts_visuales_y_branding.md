# Prompts visuales y branding

## Propósito

Dejar escritos prompts y lineamientos visuales listos para IA, de modo que logos, ilustraciones, hero visuals, estados vacíos y otros assets del desktop del consultorio se generen con coherencia respecto al producto.

## Principio general

El frontend no debe depender de prompts improvisados cada vez que falte un asset. Este documento deja definido:

- qué tipo de imagen se necesita;
- en qué pantalla se usa;
- qué función cumple;
- qué estilo visual debe seguir;
- qué colores sí y no deben aparecer;
- un prompt base;
- un prompt negativo.

## Identidad ficticia base

### Marca
**Consultorio Santa Emilia**

### Producto de escritorio
**Santa Emilia Desktop**

### Tono visual
- clásico;
- institucional;
- sobrio;
- cercano;
- de barrio de clase media;
- no lujoso;
- no hospitalario extremo;
- no propagandístico.

## Paleta obligatoria de referencia

- grises;
- blanco;
- concho de vino como color secundario;
- rojo solo puntual;
- casi nada de azul dominante.

## Asset 1. Logo principal del desktop

### Uso
- login;
- barra superior;
- icono principal de marca dentro de la app;
- pantallas institucionales.

### Función
Identificar el consultorio con una imagen sobria y confiable.

### Estilo recomendado
- vectorial o semivectorial;
- limpio;
- institucional;
- fondo transparente o fácil de limpiar;
- sin demasiados detalles finos.

### Prompt base
"Logo institucional sobrio para un consultorio médico de barrio de clase media llamado Consultorio Santa Emilia, estética clásica y profesional, predominio de grises y blanco con concho de vino como color secundario, rojo solo como acento muy puntual, símbolo médico elegante y no agresivo, diseño limpio, legible, serio, confiable, apto para software de escritorio, sin apariencia infantil, sin estilo futurista, sin exceso decorativo, fondo transparente o neutro."

### Prompt negativo
"No estilo caricaturesco, no azul dominante, no rojo intenso dominante, no hospital de lujo, no iconografía recargada, no diseño infantil, no neón, no 3D exagerado, no demasiados elementos médicos flotando, no tipografía decorativa extrema."

## Asset 2. Visual principal del login

### Uso
Panel izquierdo del login o fondo ilustrado suave.

### Función
Aportar identidad visual sin competir con el formulario.

### Estilo recomendado
- ilustración institucional suave o fotografía ilustrada sobria;
- composición limpia;
- sin texto incrustado.

### Prompt base
"Ilustración institucional sobria para el login de una aplicación de escritorio de un consultorio llamado Santa Emilia Desktop, ambiente cercano y profesional, consultorio pequeño de barrio de clase media, sensación de orden administrativo y confianza, predominio de grises claros, blanco y acentos en concho de vino, sin publicidad agresiva, sin composición recargada, sin texto dentro de la imagen, estilo limpio y elegante para software de escritorio clásico."

### Prompt negativo
"No estilo infantil, no azul predominante, no rojo dominante, no estética futurista, no hospital masivo, no saturación visual, no collage caótico, no personajes caricaturescos exagerados, no texto incrustado."

## Asset 3. Empty state institucional

### Uso
Pantallas vacías o módulos sin resultados.

### Función
Evitar vacío brusco manteniendo seriedad visual.

### Estilo recomendado
- ilustración simple;
- poco detalle;
- línea limpia;
- gris + concho de vino suave.

### Prompt base
"Ilustración minimalista e institucional para estado vacío en software de escritorio de consultorio médico, estilo sobrio, limpio, profesional, con paleta de grises, blanco y acentos secundarios en concho de vino, sensación de orden y calma, sin personajes infantiles, sin exceso decorativo, apta para acompañar tablas o módulos administrativos vacíos."

### Prompt negativo
"No infantil, no meme, no caricatura exagerada, no colores saturados, no azul dominante, no rojo agresivo, no fondo complejo, no texto incrustado."

## Asset 4. Íconos o pictogramas de módulos

### Uso
Sidebar, dashboard o tarjetas de resumen.

### Función
Aportar reconocimiento visual moderado.

### Estilo recomendado
- simple;
- legible;
- plano o semiplano;
- bajo detalle.

### Prompt base
"Conjunto de íconos institucionales sobrios para aplicación de escritorio de consultorio médico: pacientes, agenda, atención, cobros, reportes, auditoría; estilo limpio, clásico, profesional, paleta en grises con acentos en concho de vino, sin apariencia infantil, sin neón, sin sobredecoración."

### Prompt negativo
"No estilo cartoon, no colores chillones, no 3D exagerado, no sombras dramáticas, no azul dominante, no símbolos recargados."

## CTA visual en el desktop

El desktop no necesita grandes banners de CTA como una web comercial. Aquí el CTA visual es más bien:

- botón principal del login;
- botones de crear registro;
- acciones de reportes;
- confirmaciones claras en modales.

## Reglas para imágenes del desktop

- priorizar assets sin texto incrustado;
- mantener branding bajo control;
- no saturar cada pantalla con imágenes;
- usar imágenes grandes solo en login o empty states;
- preferir compatibilidad con composición limpia de JavaFX.

## Resultado esperado

Este documento debe permitir generar assets visuales consistentes con `Santa Emilia Desktop`, sin depender de prompts inventados cada vez y reduciendo la probabilidad de que la IA produzca imágenes visualmente incoherentes con la identidad del consultorio.