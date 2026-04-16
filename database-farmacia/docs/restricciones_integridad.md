# Restricciones de integridad

## Propósito

Definir las restricciones de integridad que deben proteger la consistencia de la base de datos de farmacia y evitar estados contradictorios o relaciones inválidas.

## Principio general

La integridad no debe dejarse solo al frontend ni a la buena voluntad del backend. La base de datos también debe defender activamente ciertas reglas mínimas del subdominio.

## Integridad de entidad

### RI-001. Toda tabla principal debe tener clave primaria
Producto, categoría y reserva, si existe, deben tener una identidad única y estable.

### RI-002. Las claves primarias no deben depender de atributos de negocio cambiantes
No conviene usar nombre comercial o SKU como PK natural principal del modelo.

## Integridad referencial

### RI-010. Todo producto que use categoría debe referenciar una categoría válida
Si la categoría se modela como entidad separada, la FK debe ser consistente.

### RI-011. Toda reserva debe referenciar un producto válido
No puede existir una reserva huérfana.

## Integridad de dominio persistido

### RI-020. Los campos obligatorios mínimos no deben ser nulos
Por ejemplo, nombre del producto, estado del producto, publicación o indicador equivalente y disponibilidad operativa.

### RI-021. Los estados cerrados deben restringirse
Estado del producto, publicación y estado de reserva, si existe, no deben quedar abiertos a cualquier texto arbitrario.

### RI-022. Los valores numéricos deben ser coherentes
Si existe precio visible o cantidad reservada, no conviene permitir valores negativos o absurdos.

### RI-023. Un producto inactivo no debe seguir tratándose como vigente
El modelo debe permitir distinguir claramente existencia histórica y oferta actual.

## Integridad histórica y operativa

### RI-030. Despublicar no es borrar
Un producto puede dejar de mostrarse sin desaparecer del sistema.

### RI-031. Inactivar no es eliminar la historia
La base debe favorecer conservación del hecho administrativo de que ese producto existió.

### RI-032. La disponibilidad debe ser coherente con la operación
No conviene sostener estados simultáneamente contradictorios dentro del mismo registro.

## Restricciones que conviene evaluar con cuidado

### Integridad por unicidad del producto
No siempre conviene forzar unicidad absoluta por nombre comercial si luego pueden aparecer variantes, presentaciones o productos parecidos. La regla exacta dependerá del diseño del catálogo.

### Integridad de reservas
Si la reserva entra en V1.1, la relación entre disponibilidad, cantidad y visibilidad deberá reforzarse con más cuidado.

## Resultado esperado

Las restricciones de integridad deben ayudar a que la base de farmacia sea consistente incluso cuando la aplicación falle o cuando alguien intente persistir estados incompatibles con el dominio.

