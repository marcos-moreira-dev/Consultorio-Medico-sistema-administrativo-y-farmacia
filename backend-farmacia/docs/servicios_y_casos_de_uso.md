# Servicios y casos de uso

## Propósito

Definir cómo debe organizarse la lógica de aplicación de `backend-farmacia` y qué casos de uso principales debe soportar el sistema, de modo que la implementación no se convierta en una mezcla confusa de controladores, repositorios y reglas de negocio dispersas.

## Principio general

El backend no debe pensar en términos de “endpoints que hacen cosas” solamente. Debe pensar en **casos de uso del negocio** coordinados por servicios de aplicación claros.

La idea central es esta:

- el controller recibe la solicitud HTTP;
- valida estructura básica del request;
- delega a un servicio o caso de uso;
- el servicio coordina lógica, validaciones de negocio y persistencia;
- el controller traduce el resultado a un response DTO uniforme.

## Qué se entiende aquí por servicio

En este contexto, un servicio es una clase de aplicación responsable de coordinar un flujo del negocio.

No debe ser:
- un “super service” gigante con cien métodos;
- un simple wrapper vacío sobre el repository;
- una utilidad procedural sin responsabilidad clara.

## Organización recomendada

Yo recomiendo una mezcla pragmática de:

- **Application Service** para coordinación por módulo;
- **Use Case** explícito para flujos importantes o sensibles.

Eso significa que no hace falta convertir cada acción en una ceremonia extrema, pero sí conviene que los flujos relevantes queden identificables y estudiables.

## Casos de uso principales por módulo

## Módulo `auth-admin`

### Casos de uso esperados
- autenticar usuario admin;
- construir contexto mínimo del usuario autenticado;
- devolver token y datos básicos de sesión.

### Servicio esperado
Un servicio de autenticación administrativa responsable de login y validación del contexto interno del usuario.

## Módulo `categorias`

### Casos de uso esperados
- crear categoría;
- consultar categoría;
- listar categorías;
- actualizar categoría;
- cambiar estado o vigencia si el diseño final lo contempla.

### Servicio esperado
Un servicio de gestión de categorías con reglas básicas de consistencia y uso en catálogo.

## Módulo `productos`

### Casos de uso esperados
- crear producto;
- actualizar producto;
- consultar producto;
- listar productos;
- cambiar estado del producto;
- asociar producto a categoría.

### Servicio esperado
Debe coordinar identidad comercial del producto, categoría asociada y coherencia del registro interno.

## Módulo `media`

### Casos de uso esperados
- subir imagen;
- validar archivo;
- almacenar archivo;
- asociar imagen principal a producto;
- reemplazar imagen;
- consultar referencia pública de imagen.

### Servicio esperado
Debe coordinar almacenamiento, validación técnica y asociación funcional con el producto.

## Módulo `catalogo-publico`

### Casos de uso esperados
- listar productos visibles al público;
- consultar detalle público del producto;
- buscar producto por nombre o categoría;
- devolver solo datos públicos del catálogo.

### Servicio esperado
Debe consumir producto, publicación y disponibilidad sin exponer información administrativa innecesaria.

## Módulo `disponibilidad-publicacion`

### Casos de uso esperados
- publicar producto;
- despublicar producto;
- actualizar disponibilidad;
- consultar estado de publicación y disponibilidad.

### Servicio esperado
Debe coordinar la frontera entre existencia interna, visibilidad pública y estado de disponibilidad.

## Evolución prevista a V1.1

## Módulo `reservas`

### Casos de uso esperados a futuro
- crear reserva;
- cancelar reserva;
- concretar reserva;
- consultar reservas por producto o estado.

### Importante
Este módulo queda expresamente fuera de V1.0 y se documenta como evolución natural para migraciones y aprendizaje posterior.

## Forma recomendada de los servicios

## Servicios de módulo
Conviene tener una clase principal por módulo que coordine operaciones frecuentes.

## Casos de uso específicos
Para flujos más delicados o con más pasos, conviene separarlos en clases explícitas.

Ejemplos razonables:
- `LoginAdminUseCase`
- `CrearProductoUseCase`
- `PublicarProductoUseCase`
- `SubirImagenProductoUseCase`
- `ListarCatalogoPublicoUseCase`

No hace falta aplicarlo a absolutamente todo si no aporta claridad real.

## Qué no debe pasar en esta capa

- lógica de negocio fuerte en controller;
- consultas complejas repartidas arbitrariamente;
- servicios gigantes sin frontera;
- casos de uso duplicados entre módulos;
- mezcla de lógica de catálogo público con autenticación admin o manejo de media sin separación clara.

## Relación con repositorios

Los servicios y casos de uso sí pueden depender de repositorios, pero no deben convertirse en simples pasamanos de `save()` y `find()`.

El valor de esta capa está en:
- coordinar pasos;
- decidir reglas;
- validar coherencia;
- componer resultados.

## Relación con DTOs

La capa de aplicación no debe quedar atada ciegamente al HTTP, pero en la práctica sí trabajará con request DTOs transformados o con comandos internos sencillos.

Lo importante es que:
- la entidad persistente no se exponga directamente;
- la respuesta final se prepare con intención clara;
- el mapper no quede olvidado ni mezclado con el controller.

## Resultado esperado

La organización por servicios y casos de uso debe hacer que el backend-farmacia sea entendible como sistema de negocio real: cada flujo importante debe poder rastrearse desde el endpoint hasta la persistencia sin sentir que todo sucede en una sola masa de código.

