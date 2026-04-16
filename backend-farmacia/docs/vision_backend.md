# Visión backend

## Propósito

Definir la identidad técnica del backend de farmacia y el papel que cumple dentro del sistema completo.

## Qué tipo de backend es este

`backend-farmacia` es un backend **modular**, **comercial-operativo**, **con doble superficie** y **orientado a casos de uso del negocio**.

No es un backend de consultorio. No es una API pública total sin administración. No es solo un “servidor de imágenes”. Debe ser el núcleo técnico del subdominio farmacia.

## Función principal del componente

Este backend existe para:

- exponer el catálogo público de la farmacia;
- proteger la parte administrativa con autenticación;
- coordinar la gestión de categorías y productos;
- aplicar reglas sobre publicación y disponibilidad;
- administrar imágenes o archivos visuales del catálogo;
- exponer contratos REST claros y versionados;
- registrar trazabilidad suficiente;
- dejar preparada una evolución formal a V1.1.

## Frontera del componente

### Lo que sí le corresponde
- exponer endpoints públicos de catálogo;
- exponer endpoints privados administrativos;
- consumir exclusivamente `database-farmacia`;
- aplicar reglas de negocio y validaciones fuertes;
- construir DTOs de request y response;
- manejar seguridad admin;
- sostener logging y trazabilidad técnica;
- servir imágenes del catálogo mediante URL controlada.

### Lo que no le corresponde
- gestionar pacientes, citas, atenciones o cobros del consultorio;
- consumir `database-consultorio`;
- delegar reglas críticas al frontend;
- devolver entidades TypeORM directamente;
- comportarse como backend de cliente autenticado en V1.0.

## Naturaleza del dominio que atiende

El backend opera una farmacia pequeña y realista. Eso significa que debe modelar con suficiente fidelidad:

- catálogo interno de productos;
- categorías simples;
- diferencia entre existencia del producto y publicación pública;
- disponibilidad operativa;
- administración de imágenes;
- una superficie pública simple y útil;
- una superficie admin clara y protegida.

## Decisiones de diseño ya fijadas

### Monolito modular
Se adopta un monolito modular porque es suficientemente clásico, estudiable y robusto para esta V1, sin introducir complejidad innecesaria de microservicios.

### REST puro
La API será REST clásica, con versionado, filtros, paginación y ordenamiento cuando el recurso lo requiera.

### Backend como fuente de verdad
Las reglas importantes sobre productos, categorías, publicación, disponibilidad e imágenes deben vivir aquí, no solo en el frontend.

### Seguridad explícita
La superficie pública no requiere login. La superficie administrativa sí.

### Evolución controlada
La reserva y otras extensiones se consideran V1.1 y deben quedar documentadas como tal, sin mezclarse dentro de V1.0.

## Módulos que definen su identidad

Este backend debe pensarse alrededor de estos módulos principales en V1.0:

- auth-admin
- categorias
- productos
- media
- catalogo-publico
- disponibilidad-publicacion

## Usuario objetivo del backend

Este backend tiene dos tipos de consumidores muy distintos:

### Público
Persona que solo necesita ver catálogo y disponibilidad pública.

### Administración interna
Usuario con rol `ADMIN_FARMACIA` que necesita operar productos, categorías, publicación, disponibilidad e imágenes.

## Tensión central que debe resolver

El backend debe equilibrar tres cosas al mismo tiempo:

- simplicidad del catálogo público;
- orden y control de la parte administrativa;
- suficiente claridad arquitectónica para que el sistema sea vendible y estudiable.

No debe hacerse ni tan pobre que parezca maqueta, ni tan complejo que parezca un ERP farmacéutico empresarial enorme.

## Resultado esperado

La visión del backend debe dejar claro que este componente es el núcleo técnico de farmacia, con una superficie pública controlada, una superficie administrativa privada, una arquitectura disciplinada y suficiente realismo como para servir de base profesional y de estudio.

