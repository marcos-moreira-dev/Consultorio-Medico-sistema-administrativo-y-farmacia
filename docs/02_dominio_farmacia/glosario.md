# Glosario

## Propósito

Fijar un vocabulario común para el subdominio de farmacia y reducir ambigüedades entre documentación, base de datos, backend, UI pública y administración interna.

## Términos principales

### Administrador de farmacia
Usuario interno responsable de mantener catálogo, visibilidad, disponibilidad y coherencia operativa del módulo.

### Agotado
Estado operativo que indica que el producto no puede sostenerse como disponible en ese momento.

### Catálogo
Conjunto de productos visibles o administrables dentro del módulo de farmacia.

### Categoría
Agrupación funcional o comercial simple usada para clasificar productos.

### Cliente
Persona que consulta o busca productos en la superficie pública o semipública de farmacia.

### Disponibilidad
Representación operativa de si un producto puede ofrecerse o no. No necesariamente coincide con el stock exacto expuesto al público.

### Estado del producto
Condición interna del producto dentro del sistema, por ejemplo activo o inactivo.

### Farmacia
Contexto comercial y operativo del proyecto encargado del catálogo y la disponibilidad de productos, separado del consultorio.

### Personal operativo de farmacia
Usuario interno que participa en tareas diarias de revisión, ajuste y mantenimiento del catálogo.

### Presentación
Forma concreta en la que el producto se ofrece, por ejemplo tamaño, unidad o variante comercial breve.

### Producto
Elemento comercial administrado por la farmacia y potencialmente visible en el catálogo público.

### Publicación
Condición que indica si un producto puede mostrarse en la superficie pública.

### Reserva
Apartado formal de unidades de un producto. Se considera parte de la evolución V1.1 y no una obligación de V1.0.

### SKU
Código interno de inventario o referencia única del producto, si el proyecto decide incorporarlo.

### Stock
Cantidad o referencia interna de existencia de producto. En la V1 puede representarse de manera básica.

### Superficie pública
Parte del sistema accesible para consulta externa, donde solo deben mostrarse datos controlados y autorizados.

### Visible
Estado práctico de un producto que puede ser encontrado o mostrado al público según la política de publicación.

## Términos que conviene evitar o usar con cuidado

### Paciente
No debe usarse como término propio del dominio de farmacia salvo cuando se hable del proyecto global. En este subdominio se prefiere cliente o visitante para la capa pública.

### Historia clínica
No pertenece a este subdominio y no debe contaminar la documentación farmacéutica.

### ERP completo
No debe asumirse como objetivo implícito de la V1, aunque el sistema tenga buena estructura.

## Resultado esperado

Este glosario debe servir como referencia mínima para escribir documentos, nombrar entidades, diseñar DTOs y construir pantallas de farmacia con terminología consistente.

