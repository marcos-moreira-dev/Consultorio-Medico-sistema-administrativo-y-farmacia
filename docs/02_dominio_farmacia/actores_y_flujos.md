# Actores y flujos

## Objetivo

Describir quiénes participan en la operación de la farmacia y cómo se conectan los flujos principales de consulta, mantenimiento y disponibilidad dentro de la V1 del sistema.

## Actores principales

### Administrador de farmacia
Es el actor que mantiene el catálogo, revisa la disponibilidad, activa o inactiva productos y sostiene la coherencia operativa del módulo.

Necesidades principales:

- crear y actualizar productos;
- controlar disponibilidad visible;
- revisar stock básico;
- evitar errores de publicación;
- reaccionar con rapidez ante agotados o cambios de catálogo.

### Personal operativo de farmacia
Puede coincidir con el administrador en negocios pequeños. Su foco está más en la operación del día a día que en el diseño del catálogo.

Necesidades principales:

- ubicar productos rápido;
- verificar disponibilidad;
- ajustar información mínima;
- registrar cambios operativos simples.

### Cliente o visitante
Es el actor de la superficie pública o semipública. No entra al sistema interno, pero consulta el catálogo, busca productos y revisa si están disponibles.

Necesidades principales:

- encontrar productos de forma simple;
- entender si un producto está disponible;
- ver información básica sin ruido.

## Actores secundarios o contextuales

### Consultorio asociado
No opera la farmacia directamente dentro de este subdominio, pero puede relacionarse conceptualmente con la existencia de ciertos productos o con el contexto comercial del negocio.

### Responsable de reservas futuras
En V1.1 puede ser el mismo personal operativo o administrativo. Se incorpora para gestionar apartados o reservas cuando la evolución del producto lo requiera.

## Flujo principal de farmacia

### Flujo típico del día

1. El personal revisa productos publicados o activos.
2. Consulta stock o disponibilidad básica.
3. Ajusta productos cuando cambia el catálogo.
4. El visitante navega, busca o consulta productos.
5. La superficie pública muestra únicamente información controlada.
6. Los productos agotados o inactivos dejan de ofrecerse como disponibles.

## Flujos operativos clave

### Flujo 1. Crear producto
Se usa cuando un nuevo producto entra al catálogo de farmacia.

### Flujo 2. Actualizar producto
Se usa cuando cambian nombre comercial, presentación, precio, categoría, disponibilidad o estado del producto.

### Flujo 3. Consultar catálogo público
Se usa cuando una persona externa busca productos, filtra por coincidencia simple o consulta el detalle básico.

### Flujo 4. Ajustar disponibilidad
Se usa cuando el personal necesita reflejar agotado, disponible o no publicado según la situación operativa.

### Flujo 5. Gestionar stock básico
Se usa para mantener una representación razonable de unidades o disponibilidad sin convertir la V1 en un inventario empresarial complejo.

## Flujos alternos frecuentes

### Producto sin stock suficiente
El sistema debe evitar mostrarlo como claramente disponible si operativamente ya no corresponde.

### Producto inactivo
El producto puede existir históricamente en el sistema, pero no formar parte del catálogo vigente.

### Producto visible pero con baja disponibilidad
El sistema debe poder reflejar una disponibilidad controlada sin requerir procesos complejos.

### Reserva futura
Se deja preparada como evolución natural de V1.1 cuando el negocio requiera apartar unidades.

## Límites importantes del flujo

- no se debe mezclar información del consultorio con la superficie pública de farmacia;
- no se debe asumir un proceso corporativo complejo de inventario;
- no se debe modelar la farmacia como simple escaparate sin operación interna;
- no se debe publicar información sin control administrativo.

## Resultado esperado

Los flujos de farmacia deben sentirse comerciales, claros y compatibles con una operación pequeña real, donde catálogo, visibilidad y disponibilidad importan tanto como la simplicidad del uso.

