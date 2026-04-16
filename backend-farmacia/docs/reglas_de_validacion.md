# Reglas de validación

## Propósito

Definir las reglas de validación que debe aplicar `backend-farmacia`, distinguiendo entre validaciones de forma, validaciones de negocio y validaciones de contexto operativo.

## Principio general

Validar no es solo verificar que un campo no venga vacío. En este backend, validar significa proteger:

- la forma del request;
- la coherencia del dominio;
- la separación entre superficie pública y admin;
- la integridad del catálogo;
- la seguridad básica de las operaciones.

## Tipos de validación

## 1. Validación de forma

Se aplica sobre el request DTO.

### Ejemplos
- campos obligatorios;
- longitud mínima o máxima;
- formato de texto;
- enums válidos;
- precios o cantidades con estructura correcta.

### Dónde debe vivir
Preferentemente en DTOs, pipes y validadores simples cercanos a la frontera HTTP.

## 2. Validación de negocio

Se aplica cuando una operación puede ser formalmente correcta, pero inválida para el dominio.

### Ejemplos
- crear producto con categoría inexistente;
- publicar producto inactivo;
- exponer producto sin datos mínimos válidos;
- asociar imagen a producto inexistente;
- usar una imagen no válida para la política del catálogo.

### Dónde debe vivir
En la capa de aplicación o en lógica de negocio suficientemente cercana al caso de uso.

## 3. Validación de autorización contextual

Se aplica cuando una acción depende de la identidad admin autenticada.

### Ejemplos
- intentar usar endpoint admin sin token;
- intentar operar publicación o media sin contexto admin válido.

### Dónde debe vivir
En seguridad y autorización, complementada por chequeos de aplicación cuando haga falta.

## Reglas de validación por módulo

## `auth-admin`

### Reglas mínimas
- credencial obligatoria;
- password obligatoria;
- usuario admin debe existir;
- usuario admin debe estar activo;
- credenciales deben ser válidas.

## `categorias`

### Reglas mínimas
- nombre de categoría obligatorio y no vacío;
- evitar duplicados obvios según la política definida;
- estado válido si el módulo lo maneja.

## `productos`

### Reglas mínimas
- categoría obligatoria;
- nombre obligatorio;
- presentación obligatoria;
- precio visible no negativo si se envía;
- estado del producto válido;
- disponibilidad válida;
- producto no debe quedar en estado internamente incoherente.

## `media`

### Reglas mínimas
- archivo obligatorio;
- tipo de archivo permitido;
- producto destino válido;
- tamaño o política de archivo razonable;
- asociación coherente con el producto.

## `catalogo-publico`

### Reglas mínimas
- solo exponer productos publicables;
- solo exponer productos visibles según la lógica del sistema;
- filtros públicos con estructura correcta;
- búsqueda simple razonable.

## `disponibilidad-publicacion`

### Reglas mínimas
- producto válido;
- transición coherente entre publicación y disponibilidad;
- producto inactivo no debe comportarse como plenamente vigente;
- un producto no debe aparecer públicamente si su lógica interna lo impide.

## Reglas específicas importantes del sistema

### RV-001. Existencia interna no implica publicación
Un producto puede existir y no formar parte del catálogo público.

### RV-002. Publicación y disponibilidad no son lo mismo
Un producto puede estar publicado y agotado. La validación no debe colapsar esos conceptos.

### RV-003. La imagen pública debe referirse a un producto válido
No deben quedar asociaciones huérfanas o inconsistentes.

### RV-004. La superficie pública no debe exponer DTOs administrativos
Aunque los datos vengan del mismo producto, la respuesta pública debe ser una proyección controlada.

### RV-005. V1.1 debe quedar fuera de V1.0
Reservas, cliente autenticado y pedidos no deben “colarse” en validaciones de V1.0 por accidente.

## Qué no debe pasar

- poner toda la validación en el controller;
- confiar en que el frontend ya validó todo;
- repetir la misma validación en cinco capas sin sentido;
- permitir que la base sea el único lugar donde explota la incoherencia;
- lanzar errores genéricos cuando el backend sí puede identificar el problema con más precisión.

## Resultado esperado

Las reglas de validación del backend-farmacia deben dejar claro qué se valida, por qué se valida y en qué capa conviene hacerlo, evitando un sistema frágil o ambiguo tanto para la superficie pública como para la administrativa.

