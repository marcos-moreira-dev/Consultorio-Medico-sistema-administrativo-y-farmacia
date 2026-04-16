# Visión frontend

## Propósito

Definir la identidad del componente `storefront-farmacia-angular` y dejar claro qué tipo de frontend es, para quién existe y qué papel cumple dentro del sistema general.

## Qué tipo de frontend es este

`storefront-farmacia-angular` es un frontend **público**, **comercial**, **orientado a catálogo** y **ligero en fricción**.

No es una app administrativa. No es una intranet. No es una landing estática desconectada del negocio. Debe comportarse como una vitrina funcional del catálogo de farmacia.

## Función principal del componente

Este frontend existe para:

- presentar la marca pública de farmacia;
- orientar al usuario rápidamente;
- permitir explorar productos visibles;
- consultar disponibilidad pública;
- facilitar el paso desde descubrimiento a revisión de detalle;
- sostener una experiencia simple y confiable en navegador.

## Usuario objetivo

### Usuario principal
Persona del barrio o cliente potencial que quiere revisar si un producto existe, si parece disponible y cómo ubicarlo dentro del catálogo.

### Rasgos probables del usuario
- poco tiempo;
- uso frecuente desde celular;
- baja tolerancia a interfaces confusas;
- interés práctico antes que técnico.

## Frontera del componente

### Lo que sí le corresponde
- home pública;
- catálogo público;
- detalle de producto;
- navegación, filtros y búsqueda de lectura;
- manejo visual de estados de carga, vacío y error.

### Lo que no le corresponde
- lógica administrativa de productos;
- escritura o mutaciones del catálogo;
- reglas de negocio críticas que deben vivir en backend;
- mezclar datos clínicos o del consultorio.

## Decisiones de visión ya fijadas

### Angular como base
Se usa Angular porque permite una aplicación web clara, escalable y suficientemente formal para una superficie pública que podría crecer.

### Catálogo como protagonista
La experiencia gira alrededor de productos, filtros, cards, detalle y disponibilidad visible.

### Identidad sobria
La interfaz debe sentirse profesional y comercial, sin parecer infantil ni caótica.

### Baja fricción
No se asume cuenta de usuario final en V1. El recorrido debe ser directo.

## Páginas núcleo que definen su identidad

- home;
- catálogo;
- detalle de producto.

Todo lo demás es apoyo a esas tres piezas.

## Tensión central que debe resolver

El frontend debe equilibrar tres cosas:

- claridad comercial;
- simplicidad de navegación;
- fidelidad al catálogo real servido por backend.

No debe ser tan pobre que parezca una maqueta, ni tan recargado que opaque el catálogo.

## Resultado esperado

La visión del frontend debe dejar claro que este componente es la cara pública digital de la farmacia: simple de entender, útil para consultar y suficientemente serio como para apoyar una demostración o una futura implementación real.
